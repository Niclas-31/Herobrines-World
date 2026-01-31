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

import de.niclasl.herobrines_world.procedures.Back;
import de.niclasl.herobrines_world.procedures.EnchantmentLevel4;
import de.niclasl.herobrines_world.procedures.EnchantmentLevel3;
import de.niclasl.herobrines_world.procedures.EnchantmentLevel2;
import de.niclasl.herobrines_world.procedures.EnchantmentLevel1;
import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record RedEnchantmentTableGui2Button(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<RedEnchantmentTableGui2Button> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "red_enchantment_table_gui_2_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, RedEnchantmentTableGui2Button> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, RedEnchantmentTableGui2Button message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new RedEnchantmentTableGui2Button(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public @NotNull Type<RedEnchantmentTableGui2Button> type() {
		return TYPE;
	}

	public static void handleData(final RedEnchantmentTableGui2Button message, final IPayloadContext context) {
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

			Back.execute(x, y, z, entity);
		}
		if (buttonID == 1) {

			EnchantmentLevel1.execute(entity);
		}
		if (buttonID == 2) {

			EnchantmentLevel2.execute(entity);
		}
		if (buttonID == 3) {

			EnchantmentLevel3.execute(entity);
		}
		if (buttonID == 4) {

			EnchantmentLevel4.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(RedEnchantmentTableGui2Button.TYPE, RedEnchantmentTableGui2Button.STREAM_CODEC, RedEnchantmentTableGui2Button::handleData);
	}
}
