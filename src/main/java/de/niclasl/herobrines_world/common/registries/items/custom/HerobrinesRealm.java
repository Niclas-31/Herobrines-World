package de.niclasl.herobrines_world.common.registries.items.custom;

import de.niclasl.herobrines_world.common.registries.blocks.custom.HerobrinesRealmPortalBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jspecify.annotations.NonNull;

public class HerobrinesRealm extends Item {
	public HerobrinesRealm(Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		Player player = context.getPlayer();
		BlockPos pos = context.getClickedPos();
		BlockPos placePos = pos.relative(context.getClickedFace());

		ItemStack stack = context.getItemInHand();

		if (!level.isClientSide()) {
			if (HerobrinesRealmPortalBlock.portalSpawn(level, placePos)) {
				playSound(level, player, placePos);
				damageItem(player, stack, context);
				return InteractionResult.SUCCESS;
			}
		}

		if (BaseFireBlock.canBePlacedAt(level, placePos, context.getHorizontalDirection())) {
			playSound(level, player, placePos);

			BlockState fire = BaseFireBlock.getState(level, placePos);
			level.setBlock(placePos, fire, 11);

			level.gameEvent(player, GameEvent.BLOCK_PLACE, pos);

			if (player instanceof ServerPlayer serverPlayer) {
				CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, placePos, stack);
			}

			damageItem(player, stack, context);

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.FAIL;
	}

	private void playSound(Level level, Player player, BlockPos pos) {
		level.playSound(
				player,
				pos,
				SoundEvents.FLINTANDSTEEL_USE,
				SoundSource.BLOCKS,
				1.0F,
				level.getRandom().nextFloat() * 0.4F + 0.8F
		);
	}

	private void damageItem(Player player, ItemStack stack, UseOnContext context) {
		if (player != null) {
			stack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
		}
	}

	@Override
	public boolean canPerformAction(@NonNull ItemStack stack, @NonNull ItemAbility ability) {
		return ItemAbilities.DEFAULT_FLINT_ACTIONS.contains(ability);
	}
}