package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import de.niclasl.herobrines_world_api.transfer.wrapper.InventoryWrapper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SingleInventoryWrapper implements InventoryWrapper {

    private final Container container;

    public SingleInventoryWrapper(Container container) {
        this.container = container;
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
    }

    @Override
    public boolean canAccept(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            if (!container.canPlaceItem(slot, stack)) {
                continue;
            }

            ItemStack existing = container.getItem(slot);

            if (existing.isEmpty()) {
                return true;
            }

            if (ItemStack.isSameItemSameComponents(existing, stack)
                    && existing.getCount() < Math.min(
                    existing.getMaxStackSize(),
                    container.getMaxStackSize())) {
                return true;
            }
        }

        return false;
    }
}