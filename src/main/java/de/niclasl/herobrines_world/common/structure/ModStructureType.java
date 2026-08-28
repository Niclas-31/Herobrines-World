package de.niclasl.herobrines_world.common.structure;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.structure.set.AshDesertPyramid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructureType {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, HerobrinesWorld.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<AshDesertPyramid>> ASH_DESERT_PYRAMID = STRUCTURE_TYPES.register(
            "desert_pyramid",
            () -> () -> AshDesertPyramid.CODEC);

    public static void register(IEventBus bus) {
        STRUCTURE_TYPES.register(bus);
    }
}