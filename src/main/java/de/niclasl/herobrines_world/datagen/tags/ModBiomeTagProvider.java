package de.niclasl.herobrines_world.datagen.tags;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.ModTags;
import de.niclasl.herobrines_world.common.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends BiomeTagsProvider {

    public ModBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, HerobrinesWorld.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {

        tag(ModTags.Biomes.IS_ASH_ORE)
                .add(ModBiomes.ASH_DESERT)
                .addTag(BiomeTags.IS_OVERWORLD);

        tag(ModTags.Biomes.IS_GREEN_ORE)
                .add(ModBiomes.FROZEN_FOREST)
                .addTag(BiomeTags.IS_OVERWORLD);

        tag(ModTags.Biomes.IS_HEROBRINE_ORE)
                .add(ModBiomes.CURSED_FOREST)
                .addTag(BiomeTags.IS_OVERWORLD);

        tag(ModTags.Biomes.IS_PLATIN_ORE)
                .add(ModBiomes.FIRE_LAND)
                .addTag(BiomeTags.IS_MOUNTAIN);

        tag(ModTags.Biomes.IS_HEROBRINE_REALM)
                .add(ModBiomes.FIRE_LAND)
                .add(ModBiomes.ASH_DESERT)
                .add(ModBiomes.CURSED_FOREST)
                .add(ModBiomes.FROZEN_FOREST);

        tag(ModTags.Biomes.IS_ALL_BIOMES)
                .addTag(ModTags.Biomes.IS_HEROBRINE_REALM)
                .addTag(BiomeTags.IS_OVERWORLD);

        tag(ModTags.Biomes.HAS_ASH_DESERT_PYRAMID)
                .add(ModBiomes.ASH_DESERT);

        tag(BiomeTags.HAS_VILLAGE_SNOWY)
                .add(ModBiomes.FROZEN_FOREST);

        tag(BiomeTags.HAS_NETHER_FORTRESS)
                .add(ModBiomes.MISTY_CHASMS)
                .add(ModBiomes.VOID_DEPTHS)
                .add(ModBiomes.ABYSSAL_WASTES);

        tag(BiomeTags.HAS_IGLOO)
                .add(ModBiomes.FROZEN_FOREST);

        tag(BiomeTags.HAS_BASTION_REMNANT)
                .add(ModBiomes.MISTY_CHASMS)
                .add(ModBiomes.ABYSSAL_WASTES);
    }
}