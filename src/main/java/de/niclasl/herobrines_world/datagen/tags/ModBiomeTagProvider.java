package de.niclasl.herobrines_world.datagen.tags;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.worldgen.biome.ModBiomes;
import de.niclasl.herobrines_world.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
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

        tag(ModTags.Biomes.IS_TOXENIUM_ORE)
                .add(Biomes.WARPED_FOREST)
                .add(Biomes.SOUL_SAND_VALLEY)
                .add(Biomes.NETHER_WASTES)
                .add(Biomes.BASALT_DELTAS)
                .add(Biomes.CRIMSON_FOREST)
                .add(ModBiomes.VOID_DEPTHS)
                .add(ModBiomes.MISTY_CHASMS)
                .add(ModBiomes.ABYSSAL_WASTES);

        tag(ModTags.Biomes.IS_HEROBRINE_REALM)
                .add(ModBiomes.FIRE_LAND)
                .add(ModBiomes.ASH_DESERT)
                .add(ModBiomes.CURSED_FOREST)
                .add(ModBiomes.FROZEN_FOREST);

        tag(ModTags.Biomes.IS_ALL_BIOMES)
                .addTag(ModTags.Biomes.IS_HEROBRINE_REALM)
                .addTag(BiomeTags.IS_OVERWORLD);
    }
}