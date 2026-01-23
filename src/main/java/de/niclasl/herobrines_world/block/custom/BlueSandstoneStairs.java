package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.Blocks;

public class BlueSandstoneStairs extends StairBlock {
	public BlueSandstoneStairs(BlockBehaviour.Properties properties) {
		super(Blocks.AIR.defaultBlockState(), properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops());
	}

	@Override
	public float getExplosionResistance() {
		return 0.8f;
	}
}