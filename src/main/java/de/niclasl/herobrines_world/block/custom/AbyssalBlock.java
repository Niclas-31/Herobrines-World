package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class AbyssalBlock extends Block {

	public AbyssalBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(5f, 12f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightBlock(@NotNull BlockState state) {
		return 15;
	}
}