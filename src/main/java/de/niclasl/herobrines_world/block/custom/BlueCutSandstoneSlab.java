package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SlabBlock;

public class BlueCutSandstoneSlab extends SlabBlock {
	public BlueCutSandstoneSlab(BlockBehaviour.Properties properties) {
		super(properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops());
	}
}