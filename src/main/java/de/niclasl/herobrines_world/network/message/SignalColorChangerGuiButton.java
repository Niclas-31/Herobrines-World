package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.procedures.*;
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

import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record SignalColorChangerGuiButton(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<SignalColorChangerGuiButton> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "signal_color_changer_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SignalColorChangerGuiButton> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SignalColorChangerGuiButton message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new SignalColorChangerGuiButton(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public @NotNull Type<SignalColorChangerGuiButton> type() {
		return TYPE;
	}

	public static void handleData(final SignalColorChangerGuiButton message, final IPayloadContext context) {
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

			OnGreenButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 1) {

			OnYellowButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 2) {

			OnRedButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 3) {

			OnBlueButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 4) {

			OnCyanButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 5) {

			OnGrayButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 6) {

			OnLightBlueButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 7) {

			OnLightGrayButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 8) {

			OnLimeButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 9) {

			OnMagentaButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 10) {

			OnOrangeButtonClicked.execute(world, x, y, z);
		}
		if (buttonID == 11) {

			OnPinkButtonClicked.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, SignalColorChangerGuiButton::handleData);
	}
}