package de.niclasl.herobrines_world.common.worldgen.dimension.noise.data;

import de.niclasl.herobrines_world.common.worldgen.dimension.noise.ModNoises;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModNoiseData {

    public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
        register(context, ModNoises.CHAOS, -5, 1.8);
    }

    private static void register(
            BootstrapContext<NormalNoise.NoiseParameters> context,
            ResourceKey<NormalNoise.NoiseParameters> key,
            int firstOctave,
            double amplitude,
            double... otherAmplitudes
    ) {
        context.register(key, new NormalNoise.NoiseParameters(firstOctave, amplitude, otherAmplitudes));
    }
}