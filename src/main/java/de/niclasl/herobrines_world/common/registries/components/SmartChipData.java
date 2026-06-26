package de.niclasl.herobrines_world.common.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.niclasl.herobrines_world.common.network.access.AccessModeImpl;
import de.niclasl.herobrines_world.common.network.transfer.TransferModeImpl;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

public record SmartChipData(Optional<Transfer> transfer, Optional<Access> access) {
    public record Transfer(int range, BlockPos pos, ResourceKey<Level> dim, int speed, TransferMode mode,
                           int keepAmount, boolean voidTrash) {
        public static final Transfer DEFAULT = new Transfer(
                4,BlockPos.ZERO, Level.OVERWORLD, 0, TransferModeImpl.INSERT,
                64, true
        );

        public static final Codec<Transfer> CODEC =
                RecordCodecBuilder.create(instance ->
                        instance.group(
                                Codec.INT.fieldOf("range").forGetter(Transfer::range),
                                BlockPos.CODEC.fieldOf("pos").forGetter(Transfer::pos),
                                ResourceKey.codec(Registries.DIMENSION).fieldOf("dim").forGetter(Transfer::dim),
                                Codec.INT.fieldOf("speed").forGetter(Transfer::speed),
                                TransferMode.CODEC.fieldOf("mode").forGetter(Transfer::mode),
                                Codec.INT.fieldOf("keepAmount").forGetter(Transfer::keepAmount),
                                Codec.BOOL.fieldOf("voidTrash").forGetter(Transfer::voidTrash)
                        ).apply(instance, Transfer::new)
                );
    }

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
}