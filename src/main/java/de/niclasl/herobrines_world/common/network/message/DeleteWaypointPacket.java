package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SavedWaypoint;
import de.niclasl.herobrines_world.common.registries.item.custom.WaypointCompass;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record DeleteWaypointPacket(UUID waypointId) implements CustomPacketPayload {
    public static final Type<DeleteWaypointPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "delete_waypoint"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DeleteWaypointPacket> STREAM_CODEC =
            StreamCodec.of(
                    DeleteWaypointPacket::encode,
                    DeleteWaypointPacket::decode
            );

    private static void encode(RegistryFriendlyByteBuf buf, DeleteWaypointPacket packet) {
        buf.writeUUID(packet.waypointId);
    }

    private static DeleteWaypointPacket decode(RegistryFriendlyByteBuf buf) {
        return new DeleteWaypointPacket(buf.readUUID());
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DeleteWaypointPacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof WaypointCompass)) return;

            List<SavedWaypoint> waypoints = stack.get(ModDataComponents.WAYPOINTS);

            if (waypoints == null) return;

            List<SavedWaypoint> copy = new ArrayList<>(waypoints);

            copy.removeIf(
                    wp -> wp.id().equals(packet.waypointId()));

            stack.set(
                    ModDataComponents.WAYPOINTS,
                    List.copyOf(copy)
            );

            UUID selected =
                    stack.get(ModDataComponents.SELECTED_WAYPOINT.get());

            if (packet.waypointId().equals(selected)) {
                stack.remove(ModDataComponents.SELECTED_WAYPOINT);
                stack.remove(DataComponents.LODESTONE_TRACKER);
            }
        });
    }
}