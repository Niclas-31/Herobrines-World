package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderboard.season.SeasonManager;
import de.niclasl.herobrines_world.common.leaderboard.season.SeasonRewardStorage;
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

public record RequestRewardsScreenPacket() implements CustomPacketPayload {

    public static final Type<RequestRewardsScreenPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "request_rewards_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestRewardsScreenPacket> STREAM_CODEC = StreamCodec.of(
            RequestRewardsScreenPacket::encode,
            RequestRewardsScreenPacket::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, RequestRewardsScreenPacket msg) {
    }

    private static RequestRewardsScreenPacket decode(RegistryFriendlyByteBuf buf) {
        return new RequestRewardsScreenPacket();
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RequestRewardsScreenPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {

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
        });
    }
}