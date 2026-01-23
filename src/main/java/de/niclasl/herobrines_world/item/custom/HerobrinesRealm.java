package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.block.custom.HerobrinesRealmPortal;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;

import org.jetbrains.annotations.NotNull;

public class HerobrinesRealm extends Item {

	public HerobrinesRealm(Item.Properties properties) {
		super(properties
				.rarity(Rarity.RARE)
				.durability(64));
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
		Player player = context.getPlayer();
		if (player == null) return InteractionResult.FAIL;

		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
		ItemStack stack = context.getItemInHand();

		if (!player.mayUseItemAt(pos, context.getClickedFace(), stack)) {
			return InteractionResult.FAIL;
		}

		if (level.isEmptyBlock(pos)) {
			HerobrinesRealmPortal.portalSpawn(level, pos);

			stack.hurtAndBreak(
					1,
					player,
					context.getHand() == InteractionHand.MAIN_HAND
							? EquipmentSlot.MAINHAND
							: EquipmentSlot.OFFHAND
			);

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.FAIL;
	}
}