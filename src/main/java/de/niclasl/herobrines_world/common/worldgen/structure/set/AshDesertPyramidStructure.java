package de.niclasl.herobrines_world.common.worldgen.structure.set;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.common.worldgen.structure.ModStructureType;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jspecify.annotations.NonNull;

public class AshDesertPyramidStructure extends SinglePieceStructure {
    public static final MapCodec<AshDesertPyramidStructure> CODEC = simpleCodec(AshDesertPyramidStructure::new);

    public AshDesertPyramidStructure(StructureSettings settings) {
        super(AshDesertPyramidPiece::new, 21, 21, settings);
    }

    @Override
    public @NonNull StructureType<?> type() {
        return ModStructureType.ASH_DESERT_PYRAMID.get();
    }
}