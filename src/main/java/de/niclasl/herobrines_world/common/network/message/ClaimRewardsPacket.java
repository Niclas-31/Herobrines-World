package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderbaord.season.SeasonRewardStorage;
import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

public record ClaimRewardsPacket() implements CustomPacketPayload {

    public static final Type<ClaimRewardsPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "claim_rewards"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClaimRewardsPacket> STREAM_CODEC =
            StreamCodec.unit(new ClaimRewardsPacket());

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

            for (RewardEntry r : rewards) {
                switch (r.name()) {
                    case "Souls" -> {
                        var vars = player.getData(ModVariables.PLAYER_VARIABLES);
                        vars.Souls += r.amount();
                        vars.markSyncDirty(player);
                    }
                    case "XP" -> player.giveExperiencePoints(r.amount());
                }
            }

            storage.markClaimed(uuid);
        });
    }
}