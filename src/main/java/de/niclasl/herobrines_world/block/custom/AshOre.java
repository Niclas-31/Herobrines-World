package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class AshOre extends Block {
	public AshOre(BlockBehaviour.Properties properties) {
		super(properties.strength(3f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightBlock(@NotNull BlockState state) {
		return 15;
	}
}