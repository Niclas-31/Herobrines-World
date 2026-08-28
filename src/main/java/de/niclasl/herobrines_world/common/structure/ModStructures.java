package de.niclasl.herobrines_world.common.structure;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.structure.set.AshDesertPyramid;
import de.niclasl.herobrines_world.common.util.ModTags;
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
                new AshDesertPyramid(new Structure.StructureSettings(holdergetter.getOrThrow(ModTags.Biomes.HAS_ASH_DESERT_PYRAMID)))
        );
    }
}