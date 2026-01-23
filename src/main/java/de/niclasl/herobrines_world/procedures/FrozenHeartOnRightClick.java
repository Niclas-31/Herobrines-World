package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.init.ModGameRules;

@EventBusSubscriber
public class FrozenHeartOnRightClick {
	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event.getLevel(), event.getEntity());
	}

	private static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.FROZEN_HEART.get() && entity.getData(ModVariables.PLAYER_VARIABLES).Hearts < 3
				&& (world instanceof ServerLevel _serverLevelGR2 && _serverLevelGR2.getGameRules().getBoolean(ModGameRules.THREE_HEARTS))) {
			if (entity instanceof Player _player) {
				ItemStack _storekeeper = new ItemStack(ModItems.FROZEN_HEART.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _storekeeper.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Hearts = entity.getData(ModVariables.PLAYER_VARIABLES).Hearts + 1;
				_vars.markSyncDirty();
			}
		} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.FROZEN_HEART.get() && entity.getData(ModVariables.PLAYER_VARIABLES).Hearts == 3
				&& (world instanceof ServerLevel _serverLevelGR6 && _serverLevelGR6.getGameRules().getBoolean(ModGameRules.THREE_HEARTS))) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("message.not_more_hearts").getString())), true);
		} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.FROZEN_HEART.get()
				&& !(world instanceof ServerLevel _serverLevelGR11 && _serverLevelGR11.getGameRules().getBoolean(ModGameRules.THREE_HEARTS))) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
		}
	}
}