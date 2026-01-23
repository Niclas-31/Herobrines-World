package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.block.ModBlocks;

public class AshPickaxeBlockDestroyedWithTool {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.ASH_ORE.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.DEEPSLATE_ASH_ORE.get()) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(ModItems.ASH_INGOT.get()));
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
		}
	}
}