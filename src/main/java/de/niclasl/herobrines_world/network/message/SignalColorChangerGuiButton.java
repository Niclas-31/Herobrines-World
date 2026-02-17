package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.procedures.*;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
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
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {
			String value = "green";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 1) {
			String value = "yellow";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 2) {
			String value = "red";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 3) {
			String value = "blue";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 4) {
			String value = "cyan";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 5) {
			String value = "gray";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 6) {
			String value = "light_blue";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 7) {
			String value = "light_gray";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 8) {
			String value = "lime";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 9) {
			String value = "magenta";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 10) {
			String value = "orange";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
		if (buttonID == 11) {
			String value = "pink";
			BlockPos pos = BlockPos.containing(x, y, z);
			BlockState state = world.getBlockState(pos);

			Property<?> property = state.getBlock()
					.getStateDefinition()
					.getProperty("color");

			if (property instanceof EnumProperty<?> enumProp) {
				setEnumValue(world, pos, state, enumProp, value);
			}
		}
	}

	private static <T extends Enum<T> & StringRepresentable> void setEnumValue(
			Level world,
			BlockPos pos,
			BlockState state,
			EnumProperty<T> property,
			String value
	) {
		property.getValue(value).ifPresent(val ->
				world.setBlock(pos, state.setValue(property, val), 3));
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, SignalColorChangerGuiButton::handleData);
	}
}
