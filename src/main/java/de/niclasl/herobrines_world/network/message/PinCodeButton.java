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

import de.niclasl.herobrines_world.procedures.CreatePinButton;
import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record PinCodeButton(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<PinCodeButton> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "pin_code_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, PinCodeButton> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PinCodeButton message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new PinCodeButton(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public @NotNull Type<PinCodeButton> type() {
		return TYPE;
	}

	public static void handleData(final PinCodeButton message, final IPayloadContext context) {
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

			CreatePinButton.execute(x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(PinCodeButton.TYPE, PinCodeButton.STREAM_CODEC, PinCodeButton::handleData);
	}
}