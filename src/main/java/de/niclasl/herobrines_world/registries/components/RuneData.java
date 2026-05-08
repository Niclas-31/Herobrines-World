package de.niclasl.herobrines_world.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record RuneData(double x, double y, double z, ResourceKey<Level> dimension, boolean saved, float yaw, float pitch) {
    public static final Codec<RuneData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("x").forGetter(RuneData::x),
                    Codec.DOUBLE.fieldOf("y").forGetter(RuneData::y),
                    Codec.DOUBLE.fieldOf("z").forGetter(RuneData::z),
                    ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(RuneData::dimension),
                    Codec.BOOL.fieldOf("saved").forGetter(RuneData::saved),
                    Codec.FLOAT.fieldOf("yaw").forGetter(RuneData::yaw),
                    Codec.FLOAT.fieldOf("pitch").forGetter(RuneData::pitch)
            ).apply(instance, RuneData::new)
    );
}