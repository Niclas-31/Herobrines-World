package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@EventBusSubscriber
public record SyncSignalColorPacket(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	private static final String[] COLORS = {
			"green", "yellow", "red", "blue", "cyan",
			"gray", "light_blue", "light_gray",
			"lime", "magenta", "orange", "pink"
	};

	public static final Type<SyncSignalColorPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_signal_color"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncSignalColorPacket> STREAM_CODEC =
			StreamCodec.of(
					(buf, msg) -> {
						buf.writeInt(msg.buttonID);
						buf.writeInt(msg.x);
						buf.writeInt(msg.y);
						buf.writeInt(msg.z);
					},
					buf -> new SyncSignalColorPacket(
							buf.readInt(),
							buf.readInt(),
							buf.readInt(),
							buf.readInt()
					)
			);
	@Override
	public @NotNull Type<SyncSignalColorPacket> type() {
		return TYPE;
	}

	public static void handleData(final SyncSignalColorPacket message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() ->
					handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)
			).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player player, int buttonID, int x, int y, int z) {
		if (buttonID < 0 || buttonID >= COLORS.length) return;

		Level level = player.level();
		BlockPos pos = new BlockPos(x, y, z);

		if (!level.hasChunkAt(pos)) return;

		BlockState state = level.getBlockState(pos);

		Property<?> raw = state.getBlock().getStateDefinition().getProperty("color");

		if (!(raw instanceof EnumProperty<?> enumProp)) return;

		setEnumValue(level, pos, state, enumProp, COLORS[buttonID]);
	}

	private static <T extends Enum<T> & StringRepresentable> void setEnumValue(
			Level world,
			BlockPos pos,
			BlockState state,
			EnumProperty<@NonNull T> property,
			String value
	) {
		property.getValue(value).ifPresent(val ->
				world.setBlock(pos, state.setValue(property, val), 3));
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, SyncSignalColorPacket::handleData);
	}
}