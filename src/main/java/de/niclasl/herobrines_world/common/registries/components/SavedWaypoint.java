package de.niclasl.herobrines_world.common.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.UUID;

public record SavedWaypoint(UUID id, String name, BlockPos pos, ResourceKey<Level> dimension) {
    public static final Codec<SavedWaypoint> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("id").forGetter(SavedWaypoint::id),
                    Codec.STRING.fieldOf("name").forGetter(SavedWaypoint::name),
                    BlockPos.CODEC.fieldOf("pos").forGetter(SavedWaypoint::pos),
                    ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(SavedWaypoint::dimension)
            ).apply(instance, SavedWaypoint::new)
    );
}