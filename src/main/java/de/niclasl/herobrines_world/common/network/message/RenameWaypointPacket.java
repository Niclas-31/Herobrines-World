package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SavedWaypoint;
import de.niclasl.herobrines_world.common.registries.items.custom.WaypointCompass;
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

public record RenameWaypointPacket(UUID waypointId, String newName)
        implements CustomPacketPayload {

    public static final Type<RenameWaypointPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    HerobrinesWorld.MODID,
                    "rename_waypoint"
            ));

    public static final StreamCodec<RegistryFriendlyByteBuf, RenameWaypointPacket> STREAM_CODEC =
            StreamCodec.of(
                    RenameWaypointPacket::encode,
                    RenameWaypointPacket::decode
            );

    private static void encode(RegistryFriendlyByteBuf buf, RenameWaypointPacket msg) {
        buf.writeUUID(msg.waypointId());
        buf.writeUtf(msg.newName());
    }

    private static RenameWaypointPacket decode(RegistryFriendlyByteBuf buf) {
        return new RenameWaypointPacket(
                buf.readUUID(),
                buf.readUtf()
        );
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RenameWaypointPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof WaypointCompass)) return;

            List<SavedWaypoint> waypoints = stack.get(ModDataComponents.WAYPOINTS);

            if (waypoints == null) return;

            List<SavedWaypoint> copy = new ArrayList<>(waypoints);

            for (int i = 0; i < copy.size(); i++) {

                SavedWaypoint wp = copy.get(i);

                if (wp.id().equals(msg.waypointId())) {

                    copy.set(i, new SavedWaypoint(wp.id(), msg.newName(), wp.pos(), wp.dimension()));

                    stack.set(ModDataComponents.WAYPOINTS, List.copyOf(copy));

                    break;
                }
            }
        });
    }
}