package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.Config;
import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FrozenHeart extends Item {
	public FrozenHeart(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

		if (entity.getMainHandItem().getItem() == ModItems.FROZEN_HEART.get() && entity.getData(ModVariables.PLAYER_VARIABLES).Hearts < 3 && Config.THREE_HEARTS.getAsBoolean()) {
			if (entity instanceof Player player) {
				ItemStack storekeeper = new ItemStack(ModItems.FROZEN_HEART.get());
				player.getInventory().clearOrCountMatchingItems(p -> storekeeper.getItem() == p.getItem(), 1, player.inventoryMenu.getCraftSlots());
			}
			ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			vars.Hearts = entity.getData(ModVariables.PLAYER_VARIABLES).Hearts + 1;
			vars.markSyncDirty();
		} else {
			if (entity.getMainHandItem().getItem() == ModItems.FROZEN_HEART.get() && entity.getData(ModVariables.PLAYER_VARIABLES).Hearts == 3 && Config.THREE_HEARTS.getAsBoolean()) {
				if (entity instanceof Player player && !player.level().isClientSide())
					player.displayClientMessage(Component.literal((Component.translatable("message.not_more_hearts").getString())), true);
			} else {
				if (entity.getMainHandItem().getItem() == ModItems.FROZEN_HEART.get() && !Config.THREE_HEARTS.getAsBoolean()) {
					if (entity instanceof Player player && !player.level().isClientSide())
						player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
				}
			}
		}

		return InteractionResult.SUCCESS;
	}
}