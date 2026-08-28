package de.niclasl.herobrines_world.common.structure.set;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.common.structure.ModStructureType;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jspecify.annotations.NonNull;

public class AshDesertPyramid extends SinglePieceStructure {
    public static final MapCodec<AshDesertPyramid> CODEC = simpleCodec(AshDesertPyramid::new);

    public AshDesertPyramid(StructureSettings settings) {
        super(AshDesertPyramidPiece::new, 21, 21, settings);
    }

    @Override
    public @NonNull StructureType<?> type() {
        return ModStructureType.ASH_DESERT_PYRAMID.get();
    }
}