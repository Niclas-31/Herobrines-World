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

    public static final ResourceKey<PlacedFeature> ORE_ASH_SMALL = registerKey("ore_ash_small");
    public static final ResourceKey<PlacedFeature> ORE_ASH_MIDDLE = registerKey("ore_ash_middle");
    public static final ResourceKey<PlacedFeature> ORE_ASH_UPPER = registerKey("ore_ash_upper");

    public static final ResourceKey<PlacedFeature> ORE_FROZEN_HEART_SMALL = registerKey("ore_frozen_heart_small");
    public static final ResourceKey<PlacedFeature> ORE_FROZEN_HEART_MIDDLE = registerKey("ore_frozen_heart_middle");
    public static final ResourceKey<PlacedFeature> ORE_FROZEN_HEART_UPPER = registerKey("ore_frozen_heart_upper");

    public static final ResourceKey<PlacedFeature> ORE_GREEN_SMALL = registerKey("ore_green_small");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_MIDDLE = registerKey("ore_green_middle");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_UPPER = registerKey("ore_green_upper");

    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE = registerKey("ore_herobrine");
    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE_BURIED = registerKey("ore_herobrine_buried");
    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE_LARGE = registerKey("ore_herobrine_large");
    public static final ResourceKey<PlacedFeature> ORE_HEROBRINE_MEDIUM = registerKey("ore_herobrine_medium");

    public static final ResourceKey<PlacedFeature> ORE_PLATIN = registerKey("ore_platin");

    public static final ResourceKey<PlacedFeature> CURSED_STONE_PLACED_KEY = registerKey("cursed_stone_placed");
    public static final ResourceKey<PlacedFeature> ABYSSAL_BLOCK_PLACED_KEY = registerKey("abyssal_block_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ORE_ASH_SMALL, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_ASH_SMALL),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(72))));
        register(context, ORE_ASH_MIDDLE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_ASH),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        register(context, ORE_ASH_UPPER, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_ASH),
                ModOrePlacement.commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));

        register(context, ORE_FROZEN_HEART_SMALL, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_FROZEN_HEART_SMALL),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(72))));
        register(context, ORE_FROZEN_HEART_MIDDLE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_FROZEN_HEART),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        register(context, ORE_FROZEN_HEART_UPPER, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_FROZEN_HEART),
                ModOrePlacement.rareOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));

        register(context, ORE_GREEN_SMALL, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_GREEN_SMALL),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(72))));
        register(context, ORE_GREEN_MIDDLE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_GREEN),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        register(context, ORE_GREEN_UPPER, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_GREEN),
                ModOrePlacement.commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));

        register(context, ORE_HEROBRINE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_SMALL),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        register(context, ORE_HEROBRINE_BURIED, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_BURIED),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        register(context, ORE_HEROBRINE_LARGE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_LARGE),
                ModOrePlacement.rareOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        register(context, ORE_HEROBRINE_MEDIUM, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_HEROBRINE_MEDIUM),
                ModOrePlacement.commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4))));

        register(context, ORE_PLATIN, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_PLATIN),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

        register(context, CURSED_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CURSED_STONE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(128))));

        register(context, ABYSSAL_BLOCK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ABYSSAL_BLOCK_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(128))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}