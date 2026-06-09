package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.items.custom.WaypointCompass;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

public record DeselectWaypointPacket() implements CustomPacketPayload {
    public static final Type<DeselectWaypointPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "deselect_waypoint_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DeselectWaypointPacket> STREAM_CODEC =
            StreamCodec.unit(new DeselectWaypointPacket());

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DeselectWaypointPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof WaypointCompass)) return;

            stack.remove(ModDataComponents.SELECTED_WAYPOINT.get());
            stack.remove(DataComponents.LODESTONE_TRACKER);
        });
    }
}