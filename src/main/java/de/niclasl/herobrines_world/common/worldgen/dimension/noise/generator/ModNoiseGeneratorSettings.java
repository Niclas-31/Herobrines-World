package de.niclasl.herobrines_world.common.worldgen.dimension.noise.generator;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.worldgen.dimension.noise.ModNoiseSettings;
import de.niclasl.herobrines_world.common.worldgen.dimension.noise.router.ModNoiseRouter;
import de.niclasl.herobrines_world.common.worldgen.dimension.surface.ModSurfaceRuleData;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;

public class ModNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> HEROBRINES_REALM = ResourceKey.create(
            Registries.NOISE_SETTINGS, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "herobrines_realm")
    );
    public static final ResourceKey<NoiseGeneratorSettings> UNDERWORLD = ResourceKey.create(
            Registries.NOISE_SETTINGS, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "underworld")
    );

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        context.register(HEROBRINES_REALM, herobrines_realm(context));
        context.register(UNDERWORLD, underworld(context));
    }

    private static NoiseGeneratorSettings underworld(BootstrapContext<?> context) {
        return new NoiseGeneratorSettings(
                ModNoiseSettings.UNDERWORLD,
                Blocks.NETHERRACK.defaultBlockState(),
                Blocks.LAVA.defaultBlockState(),
                ModNoiseRouter.underworld(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE)),
                ModSurfaceRuleData.underworld(),
                List.of(),
                32,
                false,
                false,
                false,
                true
        );
    }

    private static NoiseGeneratorSettings herobrines_realm(BootstrapContext<?> context) {
        return new NoiseGeneratorSettings(
                ModNoiseSettings.HEROBRINES_REALM,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                ModNoiseRouter.herobrinesRealm(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE), false, false),
                ModSurfaceRuleData.herobrine(),
                new OverworldBiomeBuilder().spawnTarget(),
                63,
                false,
                true,
                true,
                false
        );
    }
}