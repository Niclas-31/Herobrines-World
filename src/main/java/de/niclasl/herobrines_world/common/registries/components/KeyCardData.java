package de.niclasl.herobrines_world.common.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record KeyCardData(UUID owner, int level) {
    public static final Codec<KeyCardData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("owner").forGetter(KeyCardData::owner),
                    Codec.INT.fieldOf("level").forGetter(KeyCardData::level)
            ).apply(instance, KeyCardData::new));
}