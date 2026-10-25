package de.niclasl.herobrines_world.common.network;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderboard.season.SeasonManager;
import de.niclasl.herobrines_world.common.leaderboard.season.SeasonRewardStorage;
import de.niclasl.herobrines_world.common.network.message.*;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.items.custom.WaypointCompass;
import de.niclasl.herobrines_world.common.util.variables.ModVariables;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardAPI;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardAPIHolder;
import de.niclasl.herobrines_world_api.leaderboard.RewardContext;
import de.niclasl.herobrines_world_api.leaderboard.RewardEntry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;
import java.util.UUID;

public class ModMessage {

    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(HerobrinesWorld.MOD_ID);

        registrar.playToServer(
                SyncHidePacket.TYPE,
                SyncHidePacket.STREAM_CODEC,
                (_, ctx) -> {
                    var player = ctx.player();

                    var vars = player.getData(ModVariables.PLAYER_VARIABLES);

                    vars.hide = !vars.hide;

                    if (player instanceof ServerPlayer serverPlayer) {
                        vars.markSyncDirty(serverPlayer);
                    }
                }
        );
        registrar.playToServer(
                SyncColorPacket.TYPE,
                SyncColorPacket.STREAM_CODEC,
                SyncColorPacket::handle
        );
        registrar.playToServer(
                SyncTimePacket.TYPE,
                SyncTimePacket.STREAM_CODEC,
                SyncTimePacket::handle
        );
        registrar.playToServer(
                SyncChipPacket.TYPE,
                SyncChipPacket.STREAM_CODEC,
                SyncChipPacket::handle
        );
        registrar.playToServer(
                RequestRewardsScreenPacket.TYPE,
                RequestRewardsScreenPacket.STREAM_CODEC,
                (_, ctx) -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    ServerLevel level = player.level();

                    SeasonRewardStorage storage =
                            SeasonRewardStorage.get(level);

                    List<RewardEntry> rewards;

                    if (SeasonManager.isSeasonActive(level)) {
                        rewards = SeasonManager.getPreviewRewards(player, level);
                    } else {
                        rewards = storage.getRewards(player.getUUID());
                    }

                    PacketDistributor.sendToPlayer(player, new OpenRewardScreenPacket(rewards));
                }
        );
        registrar.playToServer(
                ClaimRewardsPacket.TYPE,
                ClaimRewardsPacket.STREAM_CODEC,
                (_, ctx) -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    ServerLevel level = player.level();

                    SeasonRewardStorage storage = SeasonRewardStorage.get(level);

                    UUID uuid = player.getUUID();

                    if (!storage.canClaim(uuid)) return;

                    List<RewardEntry> rewards = storage.getRewards(uuid);

                    if (rewards == null || rewards.isEmpty()) return;

                    LeaderboardAPI api = LeaderboardAPIHolder.get();

                    int rank = api.getRank(uuid);

                    RewardContext rewardCtx = new RewardContext(player, rank);

                    for (RewardEntry r : rewards) {
                        r.type().apply(rewardCtx, r);
                    }

                    storage.markClaimed(uuid);

                    PacketDistributor.sendToPlayer(player, new SyncClaimStatePacket(storage.getClaimed()));
                }
        );
        registrar.playToServer(
                SyncWaypointCompass.TYPE,
                SyncWaypointCompass.STREAM_CODEC,
                SyncWaypointCompass::handle
        );
        registrar.playToServer(
                RenameWaypointPacket.TYPE,
                RenameWaypointPacket.STREAM_CODEC,
                RenameWaypointPacket::handle
        );
        registrar.playToServer(
                DeleteWaypointPacket.TYPE,
                DeleteWaypointPacket.STREAM_CODEC,
                DeleteWaypointPacket::handle
        );
        registrar.playToServer(
                DeselectWaypointPacket.TYPE,
                DeselectWaypointPacket.STREAM_CODEC,
                (_, ctx) -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();

                    ItemStack stack = player.getMainHandItem();

                    if (!(stack.getItem() instanceof WaypointCompass)) return;

                    stack.remove(ModDataComponents.SELECTED_WAYPOINT.get());
                    stack.remove(DataComponents.LODESTONE_TRACKER);
                }
        );

        registrar.playToClient(
                SyncLeaderboardPacket.TYPE,
                SyncLeaderboardPacket.STREAM_CODEC,
                SyncLeaderboardPacket::handle
        );
        registrar.playToClient(
                OpenRewardScreenPacket.TYPE,
                OpenRewardScreenPacket.STREAM_CODEC,
                OpenRewardScreenPacket::handle
        );
        registrar.playToClient(
                OpenWaypointScreenPacket.TYPE,
                OpenWaypointScreenPacket.STREAM_CODEC,
                OpenWaypointScreenPacket::handle
        );
        registrar.playToClient(
                PlayerVariablesSyncMessage.TYPE,
                PlayerVariablesSyncMessage.STREAM_CODEC,
                PlayerVariablesSyncMessage::handle
        );
        registrar.playToClient(
                SavedDataSyncMessage.TYPE,
                SavedDataSyncMessage.STREAM_CODEC,
                SavedDataSyncMessage::handle
        );
        registrar.playToClient(
                SyncClaimStatePacket.TYPE,
                SyncClaimStatePacket.STREAM_CODEC,
                SyncClaimStatePacket::handleSyncClaimState
        );
    }
}