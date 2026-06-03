package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.ClientHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

public record OpenWaypointScreenPacket(ItemStack stack) implements CustomPacketPayload {

    public static final Type<OpenWaypointScreenPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "open_waypoint_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenWaypointScreenPacket> STREAM_CODEC =
            StreamCodec.of(
                    OpenWaypointScreenPacket::encode,
                    OpenWaypointScreenPacket::decode
            );

    private static void encode(RegistryFriendlyByteBuf buf, OpenWaypointScreenPacket msg) {
        ItemStack.STREAM_CODEC.encode(buf, msg.stack);
    }

    private static OpenWaypointScreenPacket decode(RegistryFriendlyByteBuf buf) {
        ItemStack stack = ItemStack.STREAM_CODEC.decode(buf);
        return new OpenWaypointScreenPacket(stack);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenWaypointScreenPacket msg, IPayloadContext ctx) {
        if (ctx.flow().isClientbound()) {
            ctx.enqueueWork(() -> ClientHandler.handleOpenWaypointScreen(msg));
        }
    }
}