package de.niclasl.herobrines_world.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.worldgen.biome.ModBiomes;
import de.niclasl.herobrines_world.worldgen.dimension.noise.generator.ModNoiseGeneratorSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;

import java.util.List;

public class ModDimensions {
    public static final ResourceKey<Level> HEROBRINE_REALM =
            ResourceKey.create(Registries.DIMENSION,
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_realm"));
    public static final ResourceKey<DimensionType> HEROBRINE_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_type"));
    public static final ResourceKey<LevelStem> HEROBRINE_REALM_STEM =
            ResourceKey.create(Registries.LEVEL_STEM,
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_realm"));

    public static final ResourceKey<Level> UNDERWORLD =
            ResourceKey.create(Registries.DIMENSION,
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "underworld"));
    public static final ResourceKey<DimensionType> UNDERWORLD_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "underworld_type"));
    public static final ResourceKey<LevelStem> UNDERWORLD_REALM_STEM =
            ResourceKey.create(Registries.LEVEL_STEM,
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "underworld"));

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        HolderGetter<Timeline> holdergetter = context.lookup(Registries.TIMELINE);
        EnvironmentAttributeMap herobrineAttributes = EnvironmentAttributeMap.builder()
                .set(EnvironmentAttributes.FOG_COLOR, -6750208)
                .set(EnvironmentAttributes.SKY_COLOR, -6750208)
                .set(EnvironmentAttributes.CLOUD_COLOR, ARGB.white(0.8F))
                .set(EnvironmentAttributes.CLOUD_HEIGHT, 192.33F)
                .set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.OVERWORLD)
                .set(EnvironmentAttributes.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK)
                .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                .set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, true)
                .set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS)
                .set(EnvironmentAttributes.PIGLINS_ZOMBIFY, false)
                .build();
        context.register(ModDimensions.HEROBRINE_TYPE,
                new DimensionType(
                        false,
                        true,
                        false,
                        1.0,
                        -64,
                        384,
                        384,
                        BlockTags.INFINIBURN_OVERWORLD,
                        0.0F,
                        new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0),
                        DimensionType.Skybox.OVERWORLD,
                        DimensionType.CardinalLightType.DEFAULT,
                        herobrineAttributes,
                        holdergetter.getOrThrow(TimelineTags.IN_OVERWORLD)
                )
        );
        context.register(ModDimensions.UNDERWORLD_TYPE,
                new DimensionType(
                        true,
                        false,
                        true,
                        8.0,
                        0,
                        256,
                        128,
                        BlockTags.INFINIBURN_NETHER,
                        0.1F,
                        new DimensionType.MonsterSettings(ConstantInt.of(7), 15),
                        DimensionType.Skybox.NONE,
                        DimensionType.CardinalLightType.NETHER,
                        EnvironmentAttributeMap.builder()
                                .set(EnvironmentAttributes.FOG_START_DISTANCE, 10.0F)
                                .set(EnvironmentAttributes.FOG_END_DISTANCE, 96.0F)
                                .set(EnvironmentAttributes.SKY_LIGHT_COLOR, Timelines.NIGHT_SKY_LIGHT_COLOR)
                                .set(EnvironmentAttributes.SKY_LIGHT_LEVEL, 4.0F)
                                .set(EnvironmentAttributes.SKY_LIGHT_FACTOR, 0.0F)
                                .set(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, ParticleTypes.DRIPPING_DRIPSTONE_LAVA)
                                .set(EnvironmentAttributes.BED_RULE, BedRule.EXPLODES)
                                .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, true)
                                .set(EnvironmentAttributes.WATER_EVAPORATES, true)
                                .set(EnvironmentAttributes.FAST_LAVA, true)
                                .set(EnvironmentAttributes.PIGLINS_ZOMBIFY, false)
                                .set(EnvironmentAttributes.CAN_START_RAID, false)
                                .set(EnvironmentAttributes.SNOW_GOLEM_MELTS, true)
                                .build(),
                        holdergetter.getOrThrow(TimelineTags.IN_NETHER)
                )
        );
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {

        var herobrineType = context.lookup(Registries.DIMENSION_TYPE)
                .getOrThrow(ModDimensions.HEROBRINE_TYPE);

        var herobrineBiomeLookup = context.lookup(Registries.BIOME);

        var herobrineBiomeSource = MultiNoiseBiomeSource.createFromList(
                new Climate.ParameterList<>(List.of(

                        Pair.of(Climate.parameters(
                                Climate.Parameter.span(0.7F, 1.0F),
                                Climate.Parameter.span(0.0F, 0.2F),
                                Climate.Parameter.span(0.4F, 1.0F),
                                Climate.Parameter.span(0.2F, 0.8F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                0.0F
                        ), herobrineBiomeLookup.getOrThrow(ModBiomes.ASH_DESERT)),

                        Pair.of(Climate.parameters(
                                Climate.Parameter.span(0.2F, 0.6F),
                                Climate.Parameter.span(0.3F, 0.7F),
                                Climate.Parameter.span(-0.3F, 0.6F),
                                Climate.Parameter.span(0.2F, 0.8F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                0.0F
                        ), herobrineBiomeLookup.getOrThrow(ModBiomes.CURSED_FOREST)),

                        Pair.of(Climate.parameters(
                                Climate.Parameter.span(-1.0F, -0.4F),
                                Climate.Parameter.span(0.2F, 1.0F),
                                Climate.Parameter.span(-1.0F, 0.2F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                0.0F
                        ), herobrineBiomeLookup.getOrThrow(ModBiomes.FROZEN_FOREST)),

                        Pair.of(Climate.parameters(
                                Climate.Parameter.span(0.85F, 1.0F),
                                Climate.Parameter.span(0.2F, 0.6F),
                                Climate.Parameter.span(-1.0F, 0.5F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                0.0F
                        ), herobrineBiomeLookup.getOrThrow(ModBiomes.FIRE_LAND))

                ))
        );

        var noiseSettings = context.lookup(Registries.NOISE_SETTINGS)
                .getOrThrow(ModNoiseGeneratorSettings.HEROBRINES_REALM);

        var generator = new NoiseBasedChunkGenerator(herobrineBiomeSource, noiseSettings);

        context.register(ModDimensions.HEROBRINE_REALM_STEM,
                new LevelStem(herobrineType, generator));

        var underworldType = context.lookup(Registries.DIMENSION_TYPE)
                .getOrThrow(ModDimensions.UNDERWORLD_TYPE);

        var underworldBiomeLookup = context.lookup(Registries.BIOME);

        var underworldBiomeSource = MultiNoiseBiomeSource.createFromList(
                new Climate.ParameterList<>(List.of(

                        Pair.of(Climate.parameters(
                                Climate.Parameter.span(-1.0F, -0.5F),
                                Climate.Parameter.span(-1.0F, 0.0F),
                                Climate.Parameter.span(-1.0F, 0.3F),
                                Climate.Parameter.span(0.2F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.3F, 1.0F),
                                0.0F
                        ), underworldBiomeLookup.getOrThrow(ModBiomes.ABYSSAL_WASTES)),

                        Pair.of(Climate.parameters(
                                Climate.Parameter.span(-0.3F, 0.4F),
                                Climate.Parameter.span(0.3F, 1.0F),
                                Climate.Parameter.span(-0.5F, 0.6F),
                                Climate.Parameter.span(0.0F, 0.8F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 0.5F),
                                0.0F
                        ), underworldBiomeLookup.getOrThrow(ModBiomes.MISTY_CHASMS)),

                        Pair.of(Climate.parameters(
                                Climate.Parameter.span(0.5F, 1.0F),
                                Climate.Parameter.span(0.0F, 0.6F),
                                Climate.Parameter.span(-1.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                Climate.Parameter.span(0.6F, 1.0F),
                                0.0F
                        ), underworldBiomeLookup.getOrThrow(ModBiomes.VOID_DEPTHS))

                ))
        );

        var underworldNoise = context.lookup(Registries.NOISE_SETTINGS)
                .getOrThrow(ModNoiseGeneratorSettings.UNDERWORLD);

        var underworldGenerator = new NoiseBasedChunkGenerator(underworldBiomeSource, underworldNoise);

        context.register(ModDimensions.UNDERWORLD_REALM_STEM,
                new LevelStem(underworldType, underworldGenerator));
    }
}