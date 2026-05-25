package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SingleInventoryWrapper implements IInventoryWrapper {

    private final Container container;
    private final ItemStack filter;

    public SingleInventoryWrapper(Container container, ItemStack filter) {
        this.container = container;
        this.filter = filter;
    }

    @Override
    public int size() {
        return container.getContainerSize();
    }

    @Override
    public ItemStack get(int slot) {
        return container.getItem(slot);
    }

    @Override
    public void set(int slot, ItemStack stack) {
        container.setItem(slot, stack);
        container.setChanged();
    }

    @Override
    public boolean canAccept(ItemStack stack) {

        if (filter.isEmpty()) {
            return false;
        }

        return !ItemStack.isSameItemSameComponents(filter, stack);
    }
}