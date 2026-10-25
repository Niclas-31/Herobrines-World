package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

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
}