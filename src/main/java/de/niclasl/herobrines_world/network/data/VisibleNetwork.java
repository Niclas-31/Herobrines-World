package de.niclasl.herobrines_world.network.data;

import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import net.minecraft.core.BlockPos;

public record VisibleNetwork(
        BlockPos pos,
        String name,
        boolean locked,
        int strength,
        WirelessReceiverBlockEntity receiver
) { }