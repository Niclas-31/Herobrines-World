package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SlabBlock;

public class BlueSmoothSandstoneSlab extends SlabBlock {
	public BlueSmoothSandstoneSlab(BlockBehaviour.Properties properties) {
		super(properties.strength(2f, 6f).requiresCorrectToolForDrops());
	}
}