package de.niclasl.herobrines_world.common.network.transfer.wrapper;

import de.niclasl.herobrines_world.common.registries.blocks.entities.StorageControllerBlockEntity;
import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class StorageControllerInventoryWrapper implements InventoryWrapper {

    private final StorageControllerBlockEntity controller;

    public StorageControllerInventoryWrapper(BlockEntity be) {
        this.controller = (StorageControllerBlockEntity) be;
    }

    @Override
    public int size() {
        return controller.getContainerSize();
    }

    @Override
    public ItemStack get(int slot) {
        return controller.getItem(slot);
    }

    @Override
    public void set(int slot, ItemStack stack) {
        controller.setItem(slot, stack);
    }

    @Override
    public boolean canAccept(ItemStack stack) {
        return true;
    }
}