package de.niclasl.herobrines_world.common.world.noise.router;

import de.niclasl.herobrines_world.common.world.noise.ModNoiseSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;


public class ModNoiseRouterData {
  private static final ResourceKey<DensityFunction> Y = createKey("y");
  private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
  private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
  private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");
  public static final ResourceKey<DensityFunction> CONTINENTS = createKey("overworld/continents");
  public static final ResourceKey<DensityFunction> EROSION = createKey("overworld/erosion");
  public static final ResourceKey<DensityFunction> RIDGES = createKey("overworld/ridges");
  public static final ResourceKey<DensityFunction> OFFSET = createKey("overworld/offset");
  public static final ResourceKey<DensityFunction> FACTOR = createKey("overworld/factor");
  public static final ResourceKey<DensityFunction> DEPTH = createKey("overworld/depth");
  private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
  public static final ResourceKey<DensityFunction> CONTINENTS_LARGE = createKey("overworld_large_biomes/continents");
  public static final ResourceKey<DensityFunction> EROSION_LARGE = createKey("overworld_large_biomes/erosion");
  private static final ResourceKey<DensityFunction> OFFSET_LARGE = createKey("overworld_large_biomes/offset");
  private static final ResourceKey<DensityFunction> FACTOR_LARGE = createKey("overworld_large_biomes/factor");
  private static final ResourceKey<DensityFunction> DEPTH_LARGE = createKey("overworld_large_biomes/depth");
  private static final ResourceKey<DensityFunction> SLOPED_CHEESE_LARGE = createKey("overworld_large_biomes/sloped_cheese");
  private static final ResourceKey<DensityFunction> OFFSET_AMPLIFIED = createKey("overworld_amplified/offset");
  private static final ResourceKey<DensityFunction> FACTOR_AMPLIFIED = createKey("overworld_amplified/factor");
  private static final ResourceKey<DensityFunction> DEPTH_AMPLIFIED = createKey("overworld_amplified/depth");
  private static final ResourceKey<DensityFunction> SLOPED_CHEESE_AMPLIFIED = createKey("overworld_amplified/sloped_cheese");
  private static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = createKey("overworld/caves/spaghetti_roughness_function");
  private static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
  private static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
  private static final ResourceKey<DensityFunction> PILLARS = createKey("overworld/caves/pillars");
  private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");

  private static ResourceKey<DensityFunction> createKey(String location) {
    return ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace(location));
  }

  private static DensityFunction offsetToDepth(DensityFunction offset) {
    return DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 1.5, -1.5), offset);
  }

  private static DensityFunction getFunction(HolderGetter<DensityFunction> functions, ResourceKey<DensityFunction> name) {
    return new DensityFunctions.HolderHolder(functions.getOrThrow(name));
  }

  private static DensityFunction underground(
          HolderGetter<DensityFunction> functions, HolderGetter<NormalNoise.NoiseParameters> noises, DensityFunction slopedCheese
  ) {
    DensityFunction spaghetti2DFunction = getFunction(functions, SPAGHETTI_2D);
    DensityFunction spaghettiRoughnessFunction = getFunction(functions, SPAGHETTI_ROUGHNESS_FUNCTION);
    DensityFunction layerNoiseSource = DensityFunctions.noise(noises.getOrThrow(Noises.CAVE_LAYER), 8.0);
    DensityFunction layerizedCavernsFunction = DensityFunctions.mul(DensityFunctions.constant(4.0), layerNoiseSource.square());
    DensityFunction cheese = DensityFunctions.noise(noises.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666);
    DensityFunction solidifedCheeseWithTopSlide = DensityFunctions.add(
            DensityFunctions.add(DensityFunctions.constant(0.27), cheese).clamp(-1.0, 1.0),
            DensityFunctions.add(DensityFunctions.constant(1.5), DensityFunctions.mul(DensityFunctions.constant(-0.64), slopedCheese)).clamp(0.0, 0.5)
    );
    DensityFunction baseCaveDensity = DensityFunctions.add(layerizedCavernsFunction, solidifedCheeseWithTopSlide);
    DensityFunction undergroundSubtractions = DensityFunctions.min(
            DensityFunctions.min(baseCaveDensity, getFunction(functions, ENTRANCES)), DensityFunctions.add(spaghetti2DFunction, spaghettiRoughnessFunction)
    );
    DensityFunction pillarsWithoutCutoff = getFunction(functions, PILLARS);
    DensityFunction pillars = DensityFunctions.rangeChoice(
            pillarsWithoutCutoff, -1000000.0, 0.03, DensityFunctions.constant(-1000000.0), pillarsWithoutCutoff
    );
    return DensityFunctions.max(undergroundSubtractions, pillars);
  }

  private static DensityFunction postProcess(DensityFunction slide) {
    DensityFunction blended = DensityFunctions.blendDensity(slide);
    return DensityFunctions.interpolated(DensityFunctions.mul(blended, DensityFunctions.constant(0.64))).squeeze();
  }

  private static DensityFunction remap(DensityFunction input) {
    double factor = (320.0 - (-64.0)) / (-1.5 - 1.5);
    double offset = -64.0 - 1.5 * factor;
    return DensityFunctions.add(DensityFunctions.mul(input, DensityFunctions.constant(factor)), DensityFunctions.constant(offset));
  }

  public static NoiseRouter herobrinesRealm(
          HolderGetter<DensityFunction> functions, HolderGetter<NormalNoise.NoiseParameters> noises, boolean largeBiomes, boolean amplified
  ) {
    DensityFunction barrierNoise = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_BARRIER), 0.5);
    DensityFunction fluidLevelFloodednessNoise = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67);
    DensityFunction fluidLevelSpreadNoise = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143);
    DensityFunction lavaNoise = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_LAVA));
    DensityFunction shiftX = getFunction(functions, SHIFT_X);
    DensityFunction shiftZ = getFunction(functions, SHIFT_Z);
    DensityFunction temperature = DensityFunctions.shiftedNoise2d(
            shiftX, shiftZ, 0.25, noises.getOrThrow(largeBiomes ? Noises.TEMPERATURE_LARGE : Noises.TEMPERATURE)
    );
    DensityFunction vegetation = DensityFunctions.shiftedNoise2d(
            shiftX, shiftZ, 0.25, noises.getOrThrow(largeBiomes ? Noises.VEGETATION_LARGE : Noises.VEGETATION)
    );
    DensityFunction offset = getFunction(functions, largeBiomes ? OFFSET_LARGE : (amplified ? OFFSET_AMPLIFIED : OFFSET));
    DensityFunction factor = getFunction(functions, largeBiomes ? FACTOR_LARGE : (amplified ? FACTOR_AMPLIFIED : FACTOR));
    DensityFunction depth = getFunction(functions, largeBiomes ? DEPTH_LARGE : (amplified ? DEPTH_AMPLIFIED : DEPTH));
    DensityFunction preliminarySurfaceLevel = preliminarySurfaceLevel(offset, factor, amplified);
    DensityFunction slopedCheese = DensityFunctions.cacheOnce(
            getFunction(functions, largeBiomes ? SLOPED_CHEESE_LARGE : (amplified ? SLOPED_CHEESE_AMPLIFIED : SLOPED_CHEESE))
    );
    DensityFunction surfaceWithEntrances = DensityFunctions.min(
            slopedCheese, DensityFunctions.mul(DensityFunctions.constant(5.0), getFunction(functions, ENTRANCES))
    );
    DensityFunction caves = DensityFunctions.rangeChoice(
            slopedCheese, -1000000.0, 1.5625, surfaceWithEntrances, underground(functions, noises, slopedCheese)
    );
    DensityFunction fullNoise = DensityFunctions.min(postProcess(slideOverworld(amplified, caves)), getFunction(functions, NOODLE));
    DensityFunction y = getFunction(functions, Y);
    int veinMinY = -60;
    int veinMaxY = 50;
    DensityFunction veinToggle = yLimitedInterpolatable(y, DensityFunctions.noise(noises.getOrThrow(Noises.ORE_VEININESS), 1.5, 1.5), veinMinY, veinMaxY);
    DensityFunction veinA = yLimitedInterpolatable(y, DensityFunctions.noise(noises.getOrThrow(Noises.ORE_VEIN_A), 4.0, 4.0), veinMinY, veinMaxY).abs();
    DensityFunction veinB = yLimitedInterpolatable(y, DensityFunctions.noise(noises.getOrThrow(Noises.ORE_VEIN_B), 4.0, 4.0), veinMinY, veinMaxY).abs();
    DensityFunction veinRidged = DensityFunctions.add(DensityFunctions.constant(-0.08F), DensityFunctions.max(veinA, veinB));
    DensityFunction veinGap = DensityFunctions.noise(noises.getOrThrow(Noises.ORE_GAP));
    return new NoiseRouter(
            barrierNoise,
            fluidLevelFloodednessNoise,
            fluidLevelSpreadNoise,
            lavaNoise,
            temperature,
            vegetation,
            getFunction(functions, largeBiomes ? CONTINENTS_LARGE : CONTINENTS),
            getFunction(functions, largeBiomes ? EROSION_LARGE : EROSION),
            depth,
            getFunction(functions, RIDGES),
            preliminarySurfaceLevel,
            fullNoise,
            veinToggle,
            veinRidged,
            veinGap
    );
  }

  public static NoiseRouter underworld(HolderGetter<DensityFunction> functions, HolderGetter<NormalNoise.NoiseParameters> noises) {
    DensityFunction temperature = DensityFunctions.shiftedNoise2d(
            DensityFunctions.zero(), DensityFunctions.zero(), 0.25, noises.getOrThrow(Noises.TEMPERATURE_NETHER)
    );
    DensityFunction vegetation = DensityFunctions.shiftedNoise2d(
            DensityFunctions.zero(), DensityFunctions.zero(), 0.25, noises.getOrThrow(Noises.VEGETATION_NETHER)
    );
    DensityFunction slide = slideNetherLike(functions);
    DensityFunction fullNoise = postProcess(slide);
    return new NoiseRouter(
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            temperature,
            vegetation,
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            fullNoise,
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero()
    );
  }

  private static DensityFunction slideOverworld(boolean isAmplified, DensityFunction caves) {
    return slide(caves, -64, 384, isAmplified ? 16 : 80, isAmplified ? 0 : 64, -0.078125, 0, isAmplified ? 0.4 : 0.1171875);
  }

  private static DensityFunction slideNetherLike(HolderGetter<DensityFunction> functions) {
    return slide(getFunction(functions, BASE_3D_NOISE_NETHER), 0, 128, 24, 0, 0.9375, -8, 2.5);
  }

  private static DensityFunction noiseGradientDensity(DensityFunction factor, DensityFunction depthWithJaggedness) {
    DensityFunction gradientUnscaled = DensityFunctions.mul(depthWithJaggedness, factor);
    return DensityFunctions.mul(DensityFunctions.constant(4.0), gradientUnscaled.quarterNegative());
  }

  private static DensityFunction preliminarySurfaceLevel(DensityFunction offset, DensityFunction factor, boolean amplified) {
    DensityFunction cachedFactor = DensityFunctions.cache2d(factor);
    DensityFunction cachedOffset = DensityFunctions.cache2d(offset);
    DensityFunction upperBound = remap(
            DensityFunctions.add(
                    DensityFunctions.mul(DensityFunctions.constant(0.2734375), cachedFactor.invert()),
                    DensityFunctions.mul(DensityFunctions.constant(-1.0), cachedOffset)
            )
    );
    upperBound = upperBound.clamp(-40.0, 320.0);
    DensityFunction density = DensityFunctions.add(
            slideOverworld(
                    amplified,
                    DensityFunctions.add(noiseGradientDensity(cachedFactor, offsetToDepth(cachedOffset)), DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0)
            ),
            DensityFunctions.constant(-0.390625)
    );
    return DensityFunctions.findTopSurface(density, upperBound, -64, ModNoiseSettings.HEROBRINES_REALM.getCellHeight());
  }

  private static DensityFunction yLimitedInterpolatable(
          DensityFunction y, DensityFunction whenInRange, int minYInclusive, int maxYInclusive
  ) {
    return DensityFunctions.interpolated(
            DensityFunctions.rangeChoice(y, minYInclusive, maxYInclusive + 1, whenInRange, DensityFunctions.constant(0))
    );
  }

  private static DensityFunction slide(
          DensityFunction caves, int minY, int height, int topStartY, int topEndY, double topTarget, int bottomStartY, double bottomTarget
  ) {
    DensityFunction noiseValue = caves;
    DensityFunction topFactor = DensityFunctions.yClampedGradient(minY + height - topStartY, minY + height - topEndY, 1.0, 0.0);
    noiseValue = DensityFunctions.lerp(topFactor, topTarget, noiseValue);
    DensityFunction bottomFactor = DensityFunctions.yClampedGradient(minY + bottomStartY, minY + 24, 0.0, 1.0);
    return DensityFunctions.lerp(bottomFactor, bottomTarget, noiseValue);
  }
}