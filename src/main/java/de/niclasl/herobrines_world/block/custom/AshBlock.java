package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class AshBlock extends ColoredFallingBlock {
	public static final MapCodec<AshBlock> CODEC =
			simpleCodec(AshBlock::new);

	public @NotNull MapCodec<AshBlock> codec() {
		return CODEC;
	}

	public AshBlock(BlockBehaviour.Properties properties) {
		super(new ColorRGBA(14406560), properties.sound(SoundType.SAND).strength(1f, 10f));
	}

	@Override
	public int getLightBlock(@NotNull BlockState state) {
		return 15;
	}
}