package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.Blocks;

public class BlueSmoothSandstoneStairs extends StairBlock {
	public BlueSmoothSandstoneStairs(BlockBehaviour.Properties properties) {
		super(Blocks.AIR.defaultBlockState(), properties.strength(2f, 6f).requiresCorrectToolForDrops());
	}

	@Override
	public float getExplosionResistance() {
		return 6f;
	}
}