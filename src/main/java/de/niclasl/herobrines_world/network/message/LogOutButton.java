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
import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record LogOutButton(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<LogOutButton> TYPE = new Type<>(
			ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "log_out_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, LogOutButton> STREAM_CODEC = StreamCodec.of((
			RegistryFriendlyByteBuf buffer, LogOutButton message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		}, (RegistryFriendlyByteBuf buffer) ->
			new LogOutButton(
					buffer.readInt(),
					buffer.readInt(),
					buffer.readInt(),
					buffer.readInt()
			)
	);

	@Override
	public @NotNull Type<LogOutButton> type() {
		return TYPE;
	}

	public static void handleData(final LogOutButton message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() ->
							handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z))
					.exceptionally(e -> {
						context.connection().disconnect(Component.literal(e.getMessage()));
						return null;
					});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		if (!world.hasChunkAt(new BlockPos(x, y, z))) return;

		if (buttonID == 0) {
			de.niclasl.herobrines_world.procedures.LogOutButton.execute(x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(LogOutButton.TYPE, LogOutButton.STREAM_CODEC, LogOutButton::handleData);
	}
}