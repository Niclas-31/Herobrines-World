package de.niclasl.herobrines_world.worldgen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.registries.entity.ModEntities;
import de.niclasl.herobrines_world.worldgen.biome.ModBiomes;
import de.niclasl.herobrines_world.util.ModTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ASH_ORE = registerKey("add_ash_ore");
    public static final ResourceKey<BiomeModifier> ADD_FROZEN_HEART_ORE = registerKey("add_frozen_heart_ore");
    public static final ResourceKey<BiomeModifier> ADD_GREEN_ORE = registerKey("add_green_ore");

    public static final ResourceKey<BiomeModifier> ADD_HEROBRINE_ORE_SMALL = registerKey("add_herobrine_ore_small");
    public static final ResourceKey<BiomeModifier> ADD_HEROBRINE_ORE_MEDIUM = registerKey("add_herobrine_ore_medium");
    public static final ResourceKey<BiomeModifier> ADD_HEROBRINE_ORE_LARGE = registerKey("add_herobrine_ore_large");
    public static final ResourceKey<BiomeModifier> ADD_HEROBRINE_ORE_BURIED = registerKey("add_herobrine_ore_buried");

    public static final ResourceKey<BiomeModifier> ADD_TOXENIUM_ORE = registerKey("add_toxium_ore");

    public static final ResourceKey<BiomeModifier> ADD_CURSED_STONE = registerKey("add_cursed_stone");
    public static final ResourceKey<BiomeModifier> ADD_ABYSSAL_BLOCK = registerKey("add_abyssal_block");

    public static final ResourceKey<BiomeModifier> SPAWN_BAD_HEROBRINE = registerKey("spawn_bad_herobrine");
    public static final ResourceKey<BiomeModifier> SPAWN_CHRISTMAS_NICLASL = registerKey("spawn_christmas_niclasl");
    public static final ResourceKey<BiomeModifier> SPAWN_GOOD_HEROBRINE = registerKey("spawn_good_herobrine");
    public static final ResourceKey<BiomeModifier> SPAWN_NICLASL = registerKey("spawn_niclasl");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ASH_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_ASH_ORE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ASH_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_FROZEN_HEART_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_ALL_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FROZEN_HEART_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_GREEN_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_GREEN_ORE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GREEN_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_HEROBRINE_ORE_SMALL, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_HEROBRINE_ORE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ORE_HEROBRINE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_HEROBRINE_ORE_MEDIUM, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_HEROBRINE_ORE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ORE_HEROBRINE_MEDIUM)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_HEROBRINE_ORE_LARGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_HEROBRINE_ORE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ORE_HEROBRINE_LARGE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_HEROBRINE_ORE_BURIED, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_HEROBRINE_ORE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ORE_HEROBRINE_BURIED)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_TOXENIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.IS_TOXENIUM_ORE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TOXENIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_CURSED_STONE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CURSED_STONE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ABYSSAL_BLOCK, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ABYSSAL_BLOCK_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(SPAWN_BAD_HEROBRINE, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.FIRE_LAND), biomes.getOrThrow(ModBiomes.CURSED_FOREST),
                        biomes.getOrThrow(ModBiomes.ASH_DESERT)),
                WeightedList.of(new MobSpawnSettings.SpawnerData(ModEntities.BAD_HEROBRINE.get(), 1, 2))));

        context.register(SPAWN_CHRISTMAS_NICLASL, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.FIRE_LAND), biomes.getOrThrow(ModBiomes.FROZEN_FOREST)),
                WeightedList.of(new MobSpawnSettings.SpawnerData(ModEntities.CHRISTMAS_NICLASL.get(), 1, 2))));

        context.register(SPAWN_GOOD_HEROBRINE, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.FIRE_LAND), biomes.getOrThrow(ModBiomes.CURSED_FOREST),
                        biomes.getOrThrow(ModBiomes.ASH_DESERT)),
                WeightedList.of(new MobSpawnSettings.SpawnerData(ModEntities.GOOD_HEROBRINE.get(), 4, 4))));

        context.register(SPAWN_NICLASL, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.FIRE_LAND)),
                WeightedList.of(new MobSpawnSettings.SpawnerData(ModEntities.NICLASL.get(), 1, 2))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, name));
    }
}