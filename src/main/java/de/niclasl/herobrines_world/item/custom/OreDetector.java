package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.procedures.OreDetectorRightClicked;
import de.niclasl.herobrines_world.procedures.OreDetectorRightClickedOnBlock;
import org.jetbrains.annotations.NotNull;

public class OreDetector extends Item {

	public OreDetector(Item.Properties properties) {
		super(properties
				.durability(100)
				.fireResistant()
				.enchantable(1)
				.attributes(ItemAttributeModifiers.builder()
						.add(Attributes.ATTACK_DAMAGE,
								new AttributeModifier(BASE_ATTACK_DAMAGE_ID, -1,
										AttributeModifier.Operation.ADD_VALUE),
								EquipmentSlotGroup.MAINHAND)
						.add(Attributes.ATTACK_SPEED,
								new AttributeModifier(BASE_ATTACK_SPEED_ID, -4,
										AttributeModifier.Operation.ADD_VALUE),
								EquipmentSlotGroup.MAINHAND)
						.build()));
	}

	@Override
	public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
		return 1.0F;
	}

	@Override
	public boolean mineBlock(
			ItemStack stack,
			@NotNull Level level,
			@NotNull BlockState state,
			@NotNull BlockPos pos,
			@NotNull LivingEntity entity
	) {
		stack.hurtAndBreak(
				1,
				entity,
				entity.getUsedItemHand() == InteractionHand.MAIN_HAND
						? EquipmentSlot.MAINHAND
						: EquipmentSlot.OFFHAND
		);
		return true;
	}

	@Override
	public void hurtEnemy(
			ItemStack stack,
			@NotNull LivingEntity target,
			@NotNull LivingEntity attacker
	) {
		stack.hurtAndBreak(
				2,
				attacker,
				attacker.getUsedItemHand() == InteractionHand.MAIN_HAND
						? EquipmentSlot.MAINHAND
						: EquipmentSlot.OFFHAND
		);
	}

	@Override
	public @NotNull InteractionResult use(
			@NotNull Level level,
			@NotNull Player player,
			@NotNull InteractionHand hand
	) {
		InteractionResult result = super.use(level, player, hand);
		OreDetectorRightClicked.execute(player, player.getItemInHand(hand));
		return result;
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
		super.useOn(context);
		OreDetectorRightClickedOnBlock.execute(
				context.getLevel(),
				context.getClickedPos().getX(),
				context.getClickedPos().getY(),
				context.getClickedPos().getZ(),
				context.getPlayer(),
				context.getItemInHand()
		);
		return InteractionResult.SUCCESS;
	}
}