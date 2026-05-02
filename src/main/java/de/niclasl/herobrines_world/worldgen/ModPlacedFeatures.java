package de.niclasl.herobrines_world.worldgen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ASH_ORE_PLACED_KEY = registerKey("ash_ore_placed");
    public static final ResourceKey<PlacedFeature> FROZEN_HEART_ORE_PLACED_KEY = registerKey("frozen_heart_ore_placed");
    public static final ResourceKey<PlacedFeature> GREEN_ORE_PLACED_KEY = registerKey("green_ore_placed");

    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE = registerKey("ore_herobrine");
    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE_BURIED = registerKey("ore_herobrine_buried");
    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE_LARGE = registerKey("ore_herobrine_large");
    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE_MEDIUM = registerKey("ore_herobrine_medium");

    public static final ResourceKey<PlacedFeature> TOXENIUM_ORE_PLACED_KEY = registerKey("toxium_ore_placed");

    public static final ResourceKey<PlacedFeature> CURSED_STONE_PLACED_KEY = registerKey("cursed_stone_placed");
    public static final ResourceKey<PlacedFeature> ABYSSAL_BLOCK_PLACED_KEY = registerKey("abyssal_block_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ASH_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ASH_ORE_KEY),
                ModOrePlacement.commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
        register(context, FROZEN_HEART_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FROZEN_HEART_ORE_KEY),
                ModOrePlacement.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(256))));
        register(context, GREEN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GREEN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(256))));

        register(context, ORE_HEROBRINE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_SMALL),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        register(context, ORE_HEROBRINE_BURIED, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_BURIED),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        register(context, ORE_HEROBRINE_LARGE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_SMALL),
                ModOrePlacement.rareOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        register(context, ORE_HEROBRINE_MEDIUM, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_SMALL),
                ModOrePlacement.commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(-4))));

        register(context, TOXENIUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TOXENIUM_ORE_KEY),
                ModOrePlacement.rareOrePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24))));

        register(context, CURSED_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CURSED_STONE_KEY),
                ModOrePlacement.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256))));
        register(context, ABYSSAL_BLOCK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ABYSSAL_BLOCK_KEY),
                ModOrePlacement.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}