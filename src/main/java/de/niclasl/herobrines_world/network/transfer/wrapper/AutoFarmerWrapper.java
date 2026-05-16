package de.niclasl.herobrines_world.network.transfer.wrapper;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class AutoFarmerWrapper implements IInventoryWrapper {

    private final NonNullList<ItemStack> items;

    public AutoFarmerWrapper(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public ItemStack get(int slot) {
        return items.get(slot);
    }

    @Override
    public void set(int slot, ItemStack stack) {

        if (stack.getCount() < 0) {
            items.set(slot, ItemStack.EMPTY);
            return;
        }

        items.set(slot, stack);
    }
}