package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.world.item.ItemStack;

public class EmptyInventoryWrapper implements InventoryWrapper {

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

    @Override
    public boolean canAccept(ItemStack stack) {
        return false;
    }
}