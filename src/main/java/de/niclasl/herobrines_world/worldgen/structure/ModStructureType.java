package de.niclasl.herobrines_world.worldgen.structure;

import de.niclasl.herobrines_world.worldgen.structure.set.AshDesertPyramidStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructureType {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, "herobrines_world");

    public static DeferredHolder<StructureType<?>, StructureType<AshDesertPyramidStructure>> ASH_DESERT_PYRAMID = STRUCTURE_TYPES.register("desert_pyramid", () -> () -> AshDesertPyramidStructure.CODEC);

    public static void register(IEventBus bus) {
        STRUCTURE_TYPES.register(bus);
    }
}