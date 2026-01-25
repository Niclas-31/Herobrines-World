package de.niclasl.herobrines_world.network.data;

import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import net.minecraft.core.BlockPos;

public record VisibleNetwork(
        WirelessSenderData senderData,
        int strength,
        WirelessReceiverBlockEntity receiver
) {
    public String getName() {
        return senderData.name();
    }

    public BlockPos getPos() {
        return senderData.pos();
    }

    public WirelessSenderData getSenderData() {
        return senderData;
    }
}
