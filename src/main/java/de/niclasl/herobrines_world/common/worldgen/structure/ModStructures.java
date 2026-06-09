package de.niclasl.herobrines_world.common.worldgen.structure;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.ModTags;
import de.niclasl.herobrines_world.common.worldgen.structure.set.AshDesertPyramidStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModStructures {
    public static final ResourceKey<Structure> ASH_DESERT_PYRAMID = createKey("ash_desert_pyramid");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> holdergetter = context.lookup(Registries.BIOME);
        context.register(
                ASH_DESERT_PYRAMID,
                new AshDesertPyramidStructure(new Structure.StructureSettings(holdergetter.getOrThrow(ModTags.Biomes.HAS_ASH_DESERT_PYRAMID)))
        );
    }
}