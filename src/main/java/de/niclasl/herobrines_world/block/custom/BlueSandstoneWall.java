package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WallBlock;

public class BlueSandstoneWall extends WallBlock {
	public BlueSandstoneWall(BlockBehaviour.Properties properties) {
		super(properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false).forceSolidOn());
	}
}