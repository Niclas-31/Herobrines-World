package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record DeselectWaypointPacket() implements CustomPacketPayload {
    public static final Type<DeselectWaypointPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "deselect_waypoint_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DeselectWaypointPacket> STREAM_CODEC = StreamCodec.of(
            DeselectWaypointPacket::encode,
            DeselectWaypointPacket::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, DeselectWaypointPacket msg) {

    }

    private static DeselectWaypointPacket decode(RegistryFriendlyByteBuf buf) {
        return new DeselectWaypointPacket();
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}