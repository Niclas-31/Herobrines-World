package de.niclasl.herobrines_world.common.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.niclasl.herobrines_world.common.network.safety.AccessMode;
import de.niclasl.herobrines_world.common.network.transfer.TransferMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

public record SmartChipData(Optional<Transfer> transfer, Optional<Access> access) {
    public record Transfer(int range, BlockPos pos, ResourceKey<Level> dim, int speed, TransferMode mode) {
        public static final SmartChipData.Transfer DEFAULT = new SmartChipData.Transfer(
                4,BlockPos.ZERO, Level.OVERWORLD, 0, TransferMode.INSERT
        );

        public static final Codec<SmartChipData.Transfer> CODEC =
                RecordCodecBuilder.create(instance ->
                        instance.group(
                                Codec.INT.fieldOf("range").forGetter(SmartChipData.Transfer::range),
                                BlockPos.CODEC.fieldOf("pos").forGetter(SmartChipData.Transfer::pos),
                                ResourceKey.codec(Registries.DIMENSION).fieldOf("dim").forGetter(SmartChipData.Transfer::dim),
                                Codec.INT.fieldOf("speed").forGetter(SmartChipData.Transfer::speed),
                                TransferMode.CODEC.fieldOf("mode").forGetter(SmartChipData.Transfer::mode)
                        ).apply(instance, SmartChipData.Transfer::new)
                );
    }

    public record Access(UUID owner, AccessMode mode, int level) {
        public static final SmartChipData.Access DEFAULT = new SmartChipData.Access(
                null, AccessMode.PUBLIC, 1
        );

        public static final Codec<SmartChipData.Access> CODEC =
                RecordCodecBuilder.create(instance ->
                        instance.group(
                                UUIDUtil.CODEC.fieldOf("owner").forGetter(SmartChipData.Access::owner),
                                AccessMode.CODEC.fieldOf("mode").forGetter(SmartChipData.Access::mode),
                                Codec.INT.fieldOf("level").forGetter(SmartChipData.Access::level)
                        ).apply(instance, SmartChipData.Access::new));
    }
}