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
public record SyncTimesPacket(int buttonID) implements CustomPacketPayload {

	public static final Type<SyncTimesPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_times"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncTimesPacket> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SyncTimesPacket message) -> buffer.writeInt(message.buttonID), (RegistryFriendlyByteBuf buffer) -> new SyncTimesPacket(buffer.readInt()));
	@Override
	public @NotNull Type<SyncTimesPacket> type() {
		return TYPE;
	}

	public static void handleData(final SyncTimesPacket message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() ->
					handleButtonAction(context.player(), message.buttonID)
			).exceptionally(e -> {
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
			} else {
				ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				vars.Hide = false;
				vars.markSyncDirty();
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, SyncTimesPacket::handleData);
	}
}