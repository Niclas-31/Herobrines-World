package de.niclasl.herobrines_world.network.message;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import de.niclasl.herobrines_world.procedures.LogOutKeybindOnKeyPressed;
import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record LogOutKeybind(int eventType) implements CustomPacketPayload {
	public static final Type<LogOutKeybind> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "key_log_out_keybind"));
	public static final StreamCodec<RegistryFriendlyByteBuf, LogOutKeybind> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, LogOutKeybind message) -> buffer.writeInt(message.eventType), (RegistryFriendlyByteBuf buffer) -> new LogOutKeybind(buffer.readInt()));

	@Override
	public @NotNull Type<LogOutKeybind> type() {
		return TYPE;
	}

	public static void handleData(final LogOutKeybind message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> pressAction(context.player(), message.eventType)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void pressAction(Player entity, int type) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {

			LogOutKeybindOnKeyPressed.execute(x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(LogOutKeybind.TYPE, LogOutKeybind.STREAM_CODEC, LogOutKeybind::handleData);
	}
}