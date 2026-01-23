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
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.procedures.Reset;
import de.niclasl.herobrines_world.procedures.Inactive;
import de.niclasl.herobrines_world.procedures.Activate;
import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record TimerGuiButton(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<TimerGuiButton> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "timer_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, TimerGuiButton> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, TimerGuiButton message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new TimerGuiButton(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public @NotNull Type<TimerGuiButton> type() {
		return TYPE;
	}

	public static void handleData(final TimerGuiButton message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			Activate.execute(entity);
		}
		if (buttonID == 1) {

			Inactive.execute(entity);
		}
		if (buttonID == 2) {

			Reset.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(TimerGuiButton.TYPE, TimerGuiButton.STREAM_CODEC, TimerGuiButton::handleData);
	}
}