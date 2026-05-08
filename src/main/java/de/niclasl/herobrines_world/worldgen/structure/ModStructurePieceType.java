package de.niclasl.herobrines_world.worldgen.structure;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.worldgen.structure.set.AshDesertPyramidPiece;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModStructurePieceType {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES =
            DeferredRegister.create(BuiltInRegistries.STRUCTURE_PIECE, HerobrinesWorld.MODID);

    public static final Supplier<StructurePieceType> ASH_DESERT_PYRAMID_PIECE = STRUCTURE_PIECES.register(
            "ash_desert_pyramid",
            () -> (StructurePieceType.ContextlessType) AshDesertPyramidPiece::new);

    public static void register(IEventBus bus) {
        STRUCTURE_PIECES.register(bus);
    }
}