package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.init.ModGameRules;
import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FrozenHeart extends Item {
	public FrozenHeart(Item.Properties properties) {
		super(properties.rarity(Rarity.RARE));
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        if (entity.getMainHandItem().getItem() == ModItems.FROZEN_HEART.get() && entity.getData(ModVariables.PLAYER_VARIABLES).Hearts < 3 && world instanceof ServerLevel _serverLevelGR2 && _serverLevelGR2.getGameRules().getBoolean(ModGameRules.THREE_HEARTS)) {
			if (entity instanceof Player _player) {
				ItemStack _storekeeper = new ItemStack(ModItems.FROZEN_HEART.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _storekeeper.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Hearts = entity.getData(ModVariables.PLAYER_VARIABLES).Hearts + 1;
				_vars.markSyncDirty();
			}
		} else {
            if (entity.getMainHandItem().getItem() == ModItems.FROZEN_HEART.get() && entity.getData(ModVariables.PLAYER_VARIABLES).Hearts == 3 && world instanceof ServerLevel _serverLevelGR6 && _serverLevelGR6.getGameRules().getBoolean(ModGameRules.THREE_HEARTS)) {
                if (entity instanceof Player _player && !_player.level().isClientSide())
                    _player.displayClientMessage(Component.literal((Component.translatable("message.not_more_hearts").getString())), true);
            } else {
                if (entity.getMainHandItem().getItem() == ModItems.FROZEN_HEART.get() && !(world instanceof ServerLevel _serverLevelGR11 && _serverLevelGR11.getGameRules().getBoolean(ModGameRules.THREE_HEARTS))) {
                    if (entity instanceof Player _player && !_player.level().isClientSide())
                        _player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
                }
            }
        }
		
		return InteractionResult.SUCCESS;
	}
}
