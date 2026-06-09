package de.niclasl.herobrines_world.common.worldgen.structure;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSets {
    public static final ResourceKey<StructureSet> ASH_DESERT_PYRAMIDS = register("ash_desert_pyramids");

    private static ResourceKey<StructureSet> register(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> holdergetter = context.lookup(Registries.STRUCTURE);
        HolderGetter<Biome> holdergetter1 = context.lookup(Registries.BIOME);
        context.register(ASH_DESERT_PYRAMIDS, new StructureSet(holdergetter.getOrThrow(ModStructures.ASH_DESERT_PYRAMID), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357617)));
    }
}