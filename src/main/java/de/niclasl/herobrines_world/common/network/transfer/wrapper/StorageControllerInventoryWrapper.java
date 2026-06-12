package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import de.niclasl.herobrines_world.common.registries.blocks.entities.StorageControllerBlockEntity;
import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class StorageControllerInventoryWrapper implements InventoryWrapper {

    private final BlockEntity be;

    public StorageControllerInventoryWrapper(BlockEntity be) {
        this.be = be;
    }

    @Override
    public int size() {

        if (be instanceof StorageControllerBlockEntity controller) {
            return controller.getItems().size();
        }

        return 0;
    }

    @Override
    public ItemStack get(int slot) {

        if (be instanceof StorageControllerBlockEntity controller) {
            return controller.getItems().get(slot);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void set(int slot, ItemStack stack) {

        if (be instanceof StorageControllerBlockEntity controller) {
            controller.getItems().set(slot, stack);
            controller.setChanged();
        }
    }
}