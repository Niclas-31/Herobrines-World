package de.niclasl.herobrines_world.common.registries.item.custom;

import de.niclasl.herobrines_world.common.registries.item.ModToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AshPickaxe extends Item {

	public AshPickaxe(Item.Properties properties) {
		super(properties.pickaxe(ModToolTiers.TOOL_MATERIAL, 3f, -3f).fireResistant());
	}

	@Override
	public boolean mineBlock(@NotNull ItemStack itemstack,
	                         @NotNull Level world,
	                         @NotNull BlockState blockstate,
	                         @NotNull BlockPos pos,
	                         @NotNull LivingEntity entity) {
		ItemStack result = getSmeltedDrop(blockstate);

		if (!result.isEmpty() && world instanceof ServerLevel serverLevel) {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

			ItemEntity entityToSpawn =
					new ItemEntity(serverLevel,
							pos.getX(),
							pos.getY(),
							pos.getZ(),
							result);

			entityToSpawn.setPickUpDelay(10);
			serverLevel.addFreshEntity(entityToSpawn);
		}

        return false;
    }

	private static ItemStack getSmeltedDrop(BlockState state) {
		if (state.is(Blocks.IRON_ORE) || state.is(Blocks.DEEPSLATE_IRON_ORE))
			return new ItemStack(net.minecraft.world.item.Items.IRON_INGOT);

		if (state.is(Blocks.GOLD_ORE) || state.is(Blocks.DEEPSLATE_GOLD_ORE))
			return new ItemStack(net.minecraft.world.item.Items.GOLD_INGOT);

		if (state.is(Blocks.COPPER_ORE) || state.is(Blocks.DEEPSLATE_COPPER_ORE))
			return new ItemStack(net.minecraft.world.item.Items.COPPER_INGOT);

		if (state.is(Blocks.NETHER_GOLD_ORE))
			return new ItemStack(net.minecraft.world.item.Items.GOLD_NUGGET, 6);

		if (state.is(Blocks.STONE))
			return new ItemStack(Blocks.STONE);

		if (state.is(Blocks.GLOWSTONE))
			return new ItemStack(Blocks.GLOWSTONE);

		if (state.is(Blocks.SEA_LANTERN))
			return new ItemStack(Blocks.SEA_LANTERN);

		return ItemStack.EMPTY;
	}
}