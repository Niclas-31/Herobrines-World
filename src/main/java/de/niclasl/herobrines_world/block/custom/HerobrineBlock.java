package de.niclasl.herobrines_world.block.custom;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class HerobrineBlock extends Block {
	public HerobrineBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.METAL).strength(5f, 6f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.CHIME));
	}

	@Override
	public int getLightBlock(@NotNull BlockState state) {
		return 15;
	}
}