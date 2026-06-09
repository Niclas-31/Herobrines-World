package de.niclasl.herobrines_world.common.registries.items.custom;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class OreDetector extends Item {

	private static final List<String> MODES = List.of(
			"coal", "copper", "diamond",
			"emerald", "gold", "iron",
			"lapis", "quartz", "redstone",
			"ancient_debris", "platin", "ash",
			"herobrine", "frozen", "green"
	);

	public OreDetector(Properties properties) {
		super(properties
				.durability(100)
				.enchantable(1));
	}

	private String getMode(ItemStack stack) {
		return stack.getOrDefault(ModDataComponents.ORE_MODE, "coal");
	}

	private void setMode(ItemStack stack, String mode) {
		stack.set(ModDataComponents.ORE_MODE, mode);
	}

	@Override
	public @NotNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (player.isShiftKeyDown()) {
			String current = getMode(stack);
			int index = MODES.indexOf(current);

			String next = MODES.get((index + 1) % MODES.size());
			setMode(stack, next);

			if (!level.isClientSide()) {
				player.displayClientMessage(
						Component.translatable("item.herobrines_world.ore_detector.mode", next),
						true
				);
			}

			return InteractionResult.SUCCESS;
		}

		return super.use(level, player, hand);
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
		Level level = context.getLevel();
		Player player = context.getPlayer();
		BlockPos startPos = context.getClickedPos();
		ItemStack stack = context.getItemInHand();

		if (player == null) return InteractionResult.PASS;

		if (!player.isShiftKeyDown()) {
			scanForOre(level, player, startPos, stack);
		}

		return InteractionResult.SUCCESS;
	}

	private void scanForOre(Level level, Player player, BlockPos pos, ItemStack stack) {
		for (int i = 0; i < 256; i++) {
			BlockPos checkPos = pos.below(i);

			String mode = getMode(stack);

			Identifier tagId = Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "ores/" + mode);
			TagKey<Block> tag = BlockTags.create(tagId);

			if (level.getBlockState(checkPos).is(tag)) {
				if (player.level().isClientSide()) {
					player.displayClientMessage(
							Component.translatable("item.herobrines_world.ore_detector.found", checkPos.getX(), checkPos.getY(), checkPos.getZ()),
							true
					);
				}
				return;
			}
		}

		if (!level.isClientSide()) {
			player.displayClientMessage(
					Component.translatable("item.herobrines_world.ore_detector.no_found"),
					true
			);
		}
	}

	@Override
	public boolean mineBlock(ItemStack stack, @NonNull Level level, @NonNull BlockState state,
	                         @NonNull BlockPos pos, @NonNull LivingEntity entity) {
		stack.hurtAndBreak(1, entity,
				entity.getUsedItemHand() == InteractionHand.MAIN_HAND
						? EquipmentSlot.MAINHAND
						: EquipmentSlot.OFFHAND);
		return true;
	}

	@Override
	public void hurtEnemy(ItemStack stack, @NonNull LivingEntity target, @NonNull LivingEntity attacker) {
		stack.hurtAndBreak(2, attacker,
				attacker.getUsedItemHand() == InteractionHand.MAIN_HAND
						? EquipmentSlot.MAINHAND
						: EquipmentSlot.OFFHAND);
	}
}