package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record TimeGuiButton(int buttonID) implements CustomPacketPayload {

	public static final Type<TimeGuiButton> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "uhr_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, TimeGuiButton> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, TimeGuiButton message) -> {
		buffer.writeInt(message.buttonID);
	}, (RegistryFriendlyByteBuf buffer) -> new TimeGuiButton(buffer.readInt()));
	@Override
	public @NotNull Type<TimeGuiButton> type() {
		return TYPE;
	}

	public static void handleData(final TimeGuiButton message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID) {
		if (buttonID == 0) {
			if (!entity.getData(ModVariables.PLAYER_VARIABLES).Hide) {
				ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				vars.Hide = true;
				vars.markSyncDirty();
			} else if (entity.getData(ModVariables.PLAYER_VARIABLES).Hide) {
				ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				vars.Hide = false;
				vars.markSyncDirty();
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(TimeGuiButton.TYPE, TimeGuiButton.STREAM_CODEC, TimeGuiButton::handleData);
	}
}