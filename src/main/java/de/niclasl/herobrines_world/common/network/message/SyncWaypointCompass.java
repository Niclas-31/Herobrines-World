package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SavedWaypoint;
import de.niclasl.herobrines_world.common.registries.item.custom.WaypointCompass;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.LodestoneTracker;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record SyncWaypointCompass(UUID id) implements CustomPacketPayload {
    public static final Type<SyncWaypointCompass> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_waypoint_compass"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncWaypointCompass> STREAM_CODEC =
            StreamCodec.of(
                    SyncWaypointCompass::encode,
                    SyncWaypointCompass::decode
            );

    private static void encode(RegistryFriendlyByteBuf buf, SyncWaypointCompass msg) {
        buf.writeUUID(msg.id);
    }

    private static SyncWaypointCompass decode(RegistryFriendlyByteBuf buf) {
        return new SyncWaypointCompass(buf.readUUID());
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncWaypointCompass msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof WaypointCompass)) return;

            List<SavedWaypoint> waypoints = stack.get(ModDataComponents.WAYPOINTS);

            if (waypoints == null) return;

            if (msg.id == null) return;

            SavedWaypoint wp = waypoints.stream()
                    .filter(w -> w.id().equals(msg.id))
                    .findFirst()
                    .orElse(null);

            if (wp == null) return;

            GlobalPos pos = new GlobalPos(wp.dimension(), wp.pos());

            stack.set(ModDataComponents.SELECTED_WAYPOINT, msg.id);

            stack.set(DataComponents.LODESTONE_TRACKER, new LodestoneTracker(Optional.of(pos), false));
        });
    }
}