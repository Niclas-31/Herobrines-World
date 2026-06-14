package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CombinedChestWrapper implements InventoryWrapper {

    private final List<Container> containers;
    private final ItemStack filter;

    public CombinedChestWrapper(List<Container> containers, ItemStack filter) {
        this.containers = containers;
        this.filter = filter;
    }

    @Override
    public int size() {
        return containers.stream()
                .mapToInt(Container::getContainerSize)
                .sum();
    }

    @Override
    public ItemStack get(int slot) {
        int index = slot;

        for (Container c : containers) {
            if (index < c.getContainerSize()) {
                return c.getItem(index);
            }
            index -= c.getContainerSize();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void set(int slot, ItemStack stack) {

        int index = slot;

        for (Container c : containers) {
            if (index < c.getContainerSize()) {
                c.setItem(index, stack);
                return;
            }
            index -= c.getContainerSize();
        }
    }

    @Override
    public boolean canAccept(ItemStack stack) {
        if (filter.isEmpty()) return true;
        else return ItemStack.isSameItemSameComponents(filter, stack);
    }
}