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

import de.niclasl.herobrines_world.procedures.Enchantments;
import de.niclasl.herobrines_world.procedures.EnchantmentLevel;
import de.niclasl.herobrines_world.procedures.Enchanten;
import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record RedEnchantmentTableGuiButton(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<RedEnchantmentTableGuiButton> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "red_enchantment_table_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, RedEnchantmentTableGuiButton> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, RedEnchantmentTableGuiButton message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new RedEnchantmentTableGuiButton(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public @NotNull Type<RedEnchantmentTableGuiButton> type() {
		return TYPE;
	}

	public static void handleData(final RedEnchantmentTableGuiButton message, final IPayloadContext context) {
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

			EnchantmentLevel.execute(x, y, z, entity);
		}
		if (buttonID == 1) {

			Enchantments.execute(x, y, z, entity);
		}
		if (buttonID == 2) {

			Enchanten.execute(world, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(RedEnchantmentTableGuiButton.TYPE, RedEnchantmentTableGuiButton.STREAM_CODEC, RedEnchantmentTableGuiButton::handleData);
	}
}