package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public record SyncColorPacket(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	private static final String[] COLORS = {
			"green", "yellow", "red", "blue", "cyan",
			"gray", "light_blue", "light_gray",
			"lime", "magenta", "orange", "pink"
	};

	public static final Type<SyncColorPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_color"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncColorPacket> STREAM_CODEC =
			StreamCodec.of(
					(buf, msg) -> {
						buf.writeInt(msg.buttonID);
						buf.writeInt(msg.x);
						buf.writeInt(msg.y);
						buf.writeInt(msg.z);
					},
					buf -> new SyncColorPacket(
							buf.readInt(),
							buf.readInt(),
							buf.readInt(),
							buf.readInt()
					)
			);
	@Override
	public @NotNull Type<SyncColorPacket> type() {
		return TYPE;
	}

	public static void handle(final SyncColorPacket msg, final IPayloadContext context) {
		context.enqueueWork(() -> {
			if (msg.buttonID < 0 || msg.buttonID >= COLORS.length) return;

			Level level = context.player().level();
			BlockPos pos = new BlockPos(msg.x, msg.y, msg.z);

			if (!level.hasChunkAt(pos)) return;

			BlockState state = level.getBlockState(pos);

			Property<?> raw = state.getBlock().getStateDefinition().getProperty("color");

			if (!(raw instanceof EnumProperty<?> enumProp)) return;

            setEnumValue(level, pos, state, enumProp, COLORS[msg.buttonID]);
		});
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
}