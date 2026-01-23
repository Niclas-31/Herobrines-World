package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ToxeniumOre extends Block {
	public ToxeniumOre(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.ANCIENT_DEBRIS).strength(30f, 1200f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightBlock(@NotNull BlockState state) {
		return 15;
	}
}