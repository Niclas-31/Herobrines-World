package de.niclasl.herobrines_world.registries.item.custom;

import de.niclasl.herobrines_world.registries.block.ModBlocks;
import de.niclasl.herobrines_world.registries.item.ModItems;
import de.niclasl.herobrines_world.registries.item.ModToolTiers;
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
	public boolean mineBlock(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull BlockState blockstate, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
		boolean retval = super.mineBlock(itemstack, world, blockstate, pos, entity);

		if ((world.getBlockState(BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()))).getBlock() == ModBlocks.ASH_ORE.get() || (world.getBlockState(BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()))).getBlock() == ModBlocks.DEEPSLATE_ASH_ORE.get()) {
			world.setBlock(BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof ServerLevel level) {
				ItemEntity entityToSpawn = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.ASH_INGOT.get()));
				entityToSpawn.setPickUpDelay(10);
				level.addFreshEntity(entityToSpawn);
			}
		}
		return retval;
	}
}