package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import net.minecraft.world.item.ItemStack;

public interface IInventoryWrapper {
    int size();

    ItemStack get(int slot);

    void set(int slot, ItemStack stack);

    default boolean canAccept(ItemStack stack) {
        return false;
    }
}