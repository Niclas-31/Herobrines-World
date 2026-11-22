package de.niclasl.herobrines_world.common.structure.set;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.common.structure.ModStructureType;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jspecify.annotations.NonNull;

public class CursedWell extends SinglePieceStructure {
    public static final MapCodec<CursedWell> CODEC = simpleCodec(CursedWell::new);

    public CursedWell(StructureSettings settings) {
        super((_, west, north) -> new CursedWellPiece(west, north), 5, 5, settings);
    }

    @Override
    public @NonNull StructureType<?> type() {
        return ModStructureType.CURSED_WELL.get();
    }
}