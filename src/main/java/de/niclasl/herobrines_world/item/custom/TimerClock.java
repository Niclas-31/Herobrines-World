package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;

import de.niclasl.herobrines_world.world.inventory.custom.Timer;

import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

public class TimerClock extends Item {
	public TimerClock(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC).stacksTo(1));
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		if (entity instanceof ServerPlayer serverPlayer) {
			serverPlayer.openMenu(new MenuProvider() {
				@Override
				public @NotNull Component getDisplayName() {
					return Component.literal("Timer Clock");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
					FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
					packetBuffer.writeBlockPos(entity.blockPosition());
					packetBuffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
					return new Timer(id, inventory, packetBuffer);
				}
			}, buf -> {
				buf.writeBlockPos(entity.blockPosition());
				buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
			});
		}
		return ar;
	}
}