package de.niclasl.herobrines_world.common.network.transfer.node;

import de.niclasl.herobrines_world.common.network.transfer.wrapper.IInventoryWrapper;
import net.minecraft.world.item.ItemStack;

public record StorageNode(
        IInventoryWrapper inventory,
        ItemStack filter
) {
}