package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderboard.season.SeasonRewardStorage;
import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardAPI;
import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardAPIHolder;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardContext;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardEntry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

public record ClaimRewardsPacket() implements CustomPacketPayload {

    public static final Type<ClaimRewardsPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "claim_rewards"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClaimRewardsPacket> STREAM_CODEC = StreamCodec.of(
            ClaimRewardsPacket::encode,
            ClaimRewardsPacket::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, ClaimRewardsPacket msg) {
    }

    private static ClaimRewardsPacket decode(RegistryFriendlyByteBuf buf) {
        return new ClaimRewardsPacket();
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClaimRewardsPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {

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
        });
    }
}