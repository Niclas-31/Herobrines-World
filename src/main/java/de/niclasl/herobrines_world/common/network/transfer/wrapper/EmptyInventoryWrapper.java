package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import net.minecraft.world.item.ItemStack;

public class EmptyInventoryWrapper implements IInventoryWrapper {

    public static final EmptyInventoryWrapper INSTANCE = new EmptyInventoryWrapper();

    private EmptyInventoryWrapper() {}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ItemStack get(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void set(int slot, ItemStack stack) {}
}