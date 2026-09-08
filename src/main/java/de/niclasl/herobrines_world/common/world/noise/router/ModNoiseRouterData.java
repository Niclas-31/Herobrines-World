package de.niclasl.herobrines_world.common.world.noise.router;

import de.niclasl.herobrines_world.common.world.noise.ModNoises;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;


public class ModNoiseRouterData {
    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    private static final ResourceKey<DensityFunction> DEPTH_AMPLIFIED = createKey("overworld_amplified/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
    private static final ResourceKey<DensityFunction> DEPTH_LARGE = createKey("overworld_large_biomes/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_LARGE = createKey("overworld_large_biomes/sloped_cheese");
    private static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
    private static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_AMPLIFIED = createKey("overworld_amplified/sloped_cheese");
    private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");
    private static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = createKey("overworld/caves/spaghetti_roughness_function");
    private static final ResourceKey<DensityFunction> PILLARS = createKey("overworld/caves/pillars");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");

    private static ResourceKey<DensityFunction> createKey(String location) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace(location));
    }

    public static NoiseRouter underworld(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        return noNewCaves(densityFunctions, noiseParameters, slideNetherLike(densityFunctions));
    }

    public static NoiseRouter herobrinesRealm(
            HolderGetter<DensityFunction> densityFunctionRegistry,
            HolderGetter<NormalNoise.NoiseParameters> noiseParameters,
            boolean large,
            boolean amplified
    ) {

        DensityFunction aquiferBarrier =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER),
                        0.5
                );

        DensityFunction aquiferFloodedness =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS),
                        0.67
                );

        DensityFunction aquiferSpread =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD),
                        0.7142857142857143
                );

        DensityFunction aquiferLava =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.AQUIFER_LAVA)
                );

        DensityFunction shiftX =
                getFunction(densityFunctionRegistry, SHIFT_X);

        DensityFunction shiftZ =
                getFunction(densityFunctionRegistry, SHIFT_Z);

        DensityFunction temperature =
                DensityFunctions.shiftedNoise2d(
                        shiftX,
                        shiftZ,
                        0.25,
                        noiseParameters.getOrThrow(
                                large ? Noises.TEMPERATURE_LARGE : Noises.TEMPERATURE
                        )
                );

        DensityFunction vegetation =
                DensityFunctions.shiftedNoise2d(
                        shiftX,
                        shiftZ,
                        0.25,
                        noiseParameters.getOrThrow(
                                large ? Noises.VEGETATION_LARGE : Noises.VEGETATION
                        )
                );

        DensityFunction continents =
                getFunction(
                        densityFunctionRegistry,
                        large ? NoiseRouterData.CONTINENTS_LARGE : NoiseRouterData.CONTINENTS
                );

        DensityFunction erosion =
                getFunction(
                        densityFunctionRegistry,
                        large ? NoiseRouterData.EROSION_LARGE : NoiseRouterData.EROSION
                );

        DensityFunction depth =
                getFunction(
                        densityFunctionRegistry,
                        large
                                ? DEPTH_LARGE
                                : (amplified ? DEPTH_AMPLIFIED : NoiseRouterData.DEPTH)
                );

        DensityFunction ridges =
                getFunction(
                        densityFunctionRegistry,
                        NoiseRouterData.RIDGES
                );

        DensityFunction slopedCheese =
                getFunction(
                        densityFunctionRegistry,
                        large
                                ? SLOPED_CHEESE_LARGE
                                : (amplified
                                ? SLOPED_CHEESE_AMPLIFIED
                                : SLOPED_CHEESE)
                );

        DensityFunction chaos =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(ModNoises.CHAOS),
                        1.6
                );

        DensityFunction terrain =
                DensityFunctions.add(
                        slopedCheese,
                        DensityFunctions.mul(
                                DensityFunctions.constant(0.35),
                                chaos
                        )
                );

        DensityFunction surfaceWithEntrances =
                DensityFunctions.min(
                        terrain,
                        DensityFunctions.mul(
                                DensityFunctions.constant(5.0),
                                getFunction(
                                        densityFunctionRegistry,
                                        ENTRANCES
                                )
                        )
                );

        DensityFunction caves =
                DensityFunctions.rangeChoice(
                        terrain,
                        -1000000.0,
                        1.5625,
                        surfaceWithEntrances,
                        underground(
                                densityFunctionRegistry,
                                noiseParameters,
                                terrain
                        )
                );

        DensityFunction fullNoise =
                DensityFunctions.min(
                        postProcess(
                                slideOverworld(
                                        amplified,
                                        caves
                                )
                        ),
                        getFunction(
                                densityFunctionRegistry,
                                NOODLE
                        )
                );

        DensityFunction oreVeininess =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.ORE_VEININESS),
                        1.5,
                        1.5
                );

        DensityFunction oreA =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.ORE_VEIN_A),
                        4.0,
                        4.0
                ).abs();

        DensityFunction oreB =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.ORE_VEIN_B),
                        4.0,
                        4.0
                ).abs();

        DensityFunction oreRidged =
                DensityFunctions.add(
                        DensityFunctions.constant(-0.08),
                        DensityFunctions.max(oreA, oreB)
                );

        DensityFunction oreGaps =
                DensityFunctions.noise(
                        noiseParameters.getOrThrow(Noises.ORE_GAP)
                );

        return new NoiseRouter(
                aquiferBarrier,
                aquiferFloodedness,
                aquiferSpread,
                aquiferLava,
                temperature,
                vegetation,
                continents,
                erosion,
                depth,
                ridges,
                DensityFunctions.zero(),
                fullNoise,
                oreVeininess,
                oreRidged,
                oreGaps
        );
    }

    private static DensityFunction underground(HolderGetter<DensityFunction> functions, HolderGetter<NormalNoise.NoiseParameters> noises, DensityFunction slopedCheese) {
        DensityFunction spaghetti2DFunction = getFunction(functions, SPAGHETTI_2D);
        DensityFunction spaghettiRoughnessFunction = getFunction(functions, SPAGHETTI_ROUGHNESS_FUNCTION);
        DensityFunction layerNoiseSource = DensityFunctions.noise(noises.getOrThrow(Noises.CAVE_LAYER), 8.0);
        DensityFunction layerizedCavernsFunction = DensityFunctions.mul(DensityFunctions.constant(4.0), layerNoiseSource.square());
        DensityFunction cheese = DensityFunctions.noise(noises.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666);
        DensityFunction solidifedCheeseWithTopSlide = DensityFunctions.add(DensityFunctions.add(DensityFunctions.constant(0.27), cheese).clamp(-1.0, 1.0), DensityFunctions.add(DensityFunctions.constant(1.5), DensityFunctions.mul(DensityFunctions.constant(-0.64), slopedCheese)).clamp(0.0, 0.5));
        DensityFunction baseCaveDensity = DensityFunctions.add(layerizedCavernsFunction, solidifedCheeseWithTopSlide);
        DensityFunction undergroundSubtractions = DensityFunctions.min(DensityFunctions.min(baseCaveDensity, getFunction(functions, ENTRANCES)), DensityFunctions.add(spaghetti2DFunction, spaghettiRoughnessFunction));
        DensityFunction pillarsWithoutCutoff = getFunction(functions, PILLARS);
        DensityFunction pillars = DensityFunctions.rangeChoice(pillarsWithoutCutoff, -1000000.0, 0.03, DensityFunctions.constant(-1000000.0), pillarsWithoutCutoff);
        return DensityFunctions.max(undergroundSubtractions, pillars);
    }

    private static NoiseRouter noNewCaves(
            HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters, DensityFunction postProcessor
    ) {
        DensityFunction densityfunction = getFunction(densityFunctions, SHIFT_X);
        DensityFunction densityfunction1 = getFunction(densityFunctions, SHIFT_Z);
        DensityFunction densityfunction2 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction densityfunction3 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction densityfunction4 = postProcess(postProcessor);
        return new NoiseRouter(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                densityfunction2,
                densityfunction3,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                densityfunction4,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero()
        );
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctionRegistry, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctionRegistry.getOrThrow(key));
    }

    private static DensityFunction postProcess(DensityFunction densityFunction) {
        DensityFunction densityfunction = DensityFunctions.blendDensity(densityFunction);
        return DensityFunctions.mul(DensityFunctions.interpolated(densityfunction), DensityFunctions.constant(0.64)).squeeze();
    }

    private static DensityFunction slideOverworld(boolean amplified, DensityFunction densityFunction) {
        return slide(densityFunction, -64, 384, amplified ? 16 : 80, amplified ? 0 : 64, -0.078125, 0, amplified ? 0.4 : 0.1171875);
    }

    private static DensityFunction slideNetherLike(HolderGetter<DensityFunction> densityFunctions) {
        return slide(getFunction(densityFunctions, BASE_3D_NOISE_NETHER), 0, 128, 24, 0, 0.9375, -8, 2.5);
    }

    private static DensityFunction slide(
            DensityFunction input, int minY, int height, int topStartOffset, int topEndOffset, double topDelta, int bottomStartOffset, double bottomDelta
    ) {
        DensityFunction densityfunction1 = DensityFunctions.yClampedGradient(minY + height - topStartOffset, minY + height - topEndOffset, 1.0, 0.0);
        DensityFunction $$9 = DensityFunctions.lerp(densityfunction1, topDelta, input);
        DensityFunction densityfunction2 = DensityFunctions.yClampedGradient(minY + bottomStartOffset, minY + 24, 0.0, 1.0);
        return DensityFunctions.lerp(densityfunction2, bottomDelta, $$9);
    }
}