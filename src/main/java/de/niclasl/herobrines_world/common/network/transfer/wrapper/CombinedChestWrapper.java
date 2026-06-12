package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.Objects;

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

        for (Container container : containers) {
            int size = container.getContainerSize();

            if (index < size) {
                return container.getItem(index);
            }

            index -= size;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void set(int slot, ItemStack stack) {

        if (stack.isEmpty()) {
            Objects.requireNonNull(findContainer(slot)).setItem(localSlot(slot), ItemStack.EMPTY);
            return;
        }

        Container container = findContainer(slot);
        int local = localSlot(slot);

        assert container != null;
        ItemStack existing = container.getItem(local);

        if (existing.isEmpty()) {
            container.setItem(local, stack);
            return;
        }

        if (ItemStack.isSameItemSameComponents(existing, stack)) {

            int max = existing.getMaxStackSize();
            int space = max - existing.getCount();

            int move = Math.min(space, stack.getCount());

            existing.grow(move);
            stack.shrink(move);

            container.setItem(local, existing);
        } else {
            container.setItem(local, stack);
        }
    }

    @Override
    public boolean canAccept(ItemStack stack) {

        if (filter.isEmpty()) {
            return false;
        }

        return !ItemStack.isSameItemSameComponents(filter, stack);
    }

    private Container findContainer(int slot) {

        int index = slot;

        for (Container container : containers) {

            int size = container.getContainerSize();

            if (index < size) {
                return container;
            }

            index -= size;
        }

        return null;
    }

    private int localSlot(int slot) {

        int index = slot;

        for (Container container : containers) {

            int size = container.getContainerSize();

            if (index < size) {
                return index;
            }

            index -= size;
        }

        return -1;
    }
}