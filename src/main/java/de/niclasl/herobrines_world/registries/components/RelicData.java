package de.niclasl.herobrines_world.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record RelicData(
        UUID owner,
        String ownerName,
        int cooldownTicks,
        String bossUUID
) {
    public static final Codec<RelicData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("owner").forGetter(RelicData::owner),
                    Codec.STRING.fieldOf("owner_name").forGetter(RelicData::ownerName),
                    Codec.INT.fieldOf("cooldown_ticks").forGetter(RelicData::cooldownTicks),
                    Codec.STRING.optionalFieldOf("boss_uuid", "").forGetter(RelicData::bossUUID)
            ).apply(instance, RelicData::new));
}