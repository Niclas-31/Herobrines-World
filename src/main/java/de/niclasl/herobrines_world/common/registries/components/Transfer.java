package de.niclasl.herobrines_world.common.registries.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.niclasl.herobrines_world.common.network.transfer.TransferModeImpl;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record Transfer(int range, BlockPos pos, ResourceKey<Level> dim, int speed, TransferMode mode) {
    public static final Transfer DEFAULT = new Transfer(
            4,BlockPos.ZERO, Level.OVERWORLD, 0, TransferModeImpl.INSERT
    );

    public static final Codec<Transfer> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.INT.fieldOf("range").forGetter(Transfer::range),
                            BlockPos.CODEC.fieldOf("pos").forGetter(Transfer::pos),
                            ResourceKey.codec(Registries.DIMENSION).fieldOf("dim").forGetter(Transfer::dim),
                            Codec.INT.fieldOf("speed").forGetter(Transfer::speed),
                            TransferMode.CODEC.fieldOf("mode").forGetter(Transfer::mode)
                    ).apply(instance, Transfer::new)
            );
}