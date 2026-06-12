package de.niclasl.herobrines_world.common.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.niclasl.herobrines_world.common.network.access.AccessModeImpl;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record Access(UUID owner, int level,  AccessMode mode) {
    public static final Access DEFAULT = new Access(
            null, 1, AccessModeImpl.PUBLIC
    );

    public static final Codec<Access> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            UUIDUtil.CODEC.fieldOf("owner").forGetter(Access::owner),
                            Codec.INT.fieldOf("level").forGetter(Access::level),
                            AccessMode.CODEC.fieldOf("mode").forGetter(Access::mode)
                    ).apply(instance, Access::new)
            );
}