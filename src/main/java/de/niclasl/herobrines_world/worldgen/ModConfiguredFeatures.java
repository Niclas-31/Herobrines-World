package de.niclasl.herobrines_world.worldgen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.registries.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASH_SMALL = registerKey("ore_ash_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASH = registerKey("ore_ash");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_HEART_ORE_KEY = registerKey("frozen_heart_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_ORE_KEY = registerKey("green_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HEROBRINE_SMALL = registerKey("ore_herobrine_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HEROBRINE_MEDIUM = registerKey("ore_herobrine_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HEROBRINE_LARGE = registerKey("ore_herobrine_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HEROBRINE_BURIED = registerKey("ore_herobrine_buried");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TOXENIUM_LARGE = registerKey("ore_toxenium_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TOXENIUM_SMALL = registerKey("ore_toxenium_small");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CURSED_STONE_KEY = registerKey("cursed_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSAL_BLOCK_KEY = registerKey("abyssal_block");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);

        List<OreConfiguration.TargetBlockState> overworldAshOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ASH_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_ASH_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldFrozenHeartOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.FROZEN_HEART_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldGreenOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.GREEN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_GREEN_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldHerobrineOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.HEROBRINE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_HEROBRINE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldCursedStone = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.CURSED_STONE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldAbyssalBlock = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ABYSSAL_BLOCK.get().defaultBlockState()));

        register(context, ORE_ASH_SMALL, Feature.ORE, new OreConfiguration(overworldAshOres, 9));
        register(context, ORE_ASH, Feature.ORE, new OreConfiguration(overworldAshOres, 4));

        register(context, FROZEN_HEART_ORE_KEY, Feature.ORE, new OreConfiguration(overworldFrozenHeartOres, 9));
        register(context, GREEN_ORE_KEY, Feature.ORE, new OreConfiguration(overworldGreenOres, 9));

        register(context, ORE_HEROBRINE_SMALL, Feature.ORE, new OreConfiguration(overworldHerobrineOres, 4, 0.5F));
        register(context, ORE_HEROBRINE_MEDIUM, Feature.ORE, new OreConfiguration(overworldHerobrineOres, 8, 0.5F));
        register(context, ORE_HEROBRINE_LARGE, Feature.ORE, new OreConfiguration(overworldHerobrineOres, 12, 0.7F));
        register(context, ORE_HEROBRINE_BURIED, Feature.ORE, new OreConfiguration(overworldHerobrineOres, 8, 1.0F));

        register(context, ORE_TOXENIUM_LARGE, Feature.SCATTERED_ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.TOXENIUM_ORE.get().defaultBlockState(), 3, 1.0F));
        register(context, ORE_TOXENIUM_SMALL, Feature.SCATTERED_ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.TOXENIUM_ORE.get().defaultBlockState(), 2, 1.0F));

        register(context, CURSED_STONE_KEY, Feature.ORE, new OreConfiguration(overworldCursedStone, 7));
        register(context, ABYSSAL_BLOCK_KEY, Feature.ORE, new OreConfiguration(overworldAbyssalBlock, 10));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}