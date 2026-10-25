package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

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
}