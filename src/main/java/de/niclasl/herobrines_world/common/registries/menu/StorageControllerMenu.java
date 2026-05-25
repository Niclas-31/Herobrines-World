package de.niclasl.herobrines_world.common.registries.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class StorageControllerMenu extends AbstractContainerMenu {

    private final Container container;

    public StorageControllerMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, new SimpleContainer(54));
    }

    public StorageControllerMenu(int id, Inventory playerInventory, Container container) {
        super(ModMenuTypes.STORAGE_CONTROLLER.get(), id);

        this.container = container;

        checkContainerSize(container, 54);

        container.startOpen(playerInventory.player);

        int slot = 0;

        for (int row = 0; row < 6; row++) {

            for (int col = 0; col < 9; col++) {

                addSlot(new Slot(
                        container,
                        slot++,
                        8 + col * 18,
                        18 + row * 18
                ));
            }
        }

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 9; col++) {

                addSlot(new Slot(
                        playerInventory,
                        col + row * 9 + 9,
                        8 + col * 18,
                        140 + row * 18
                ));
            }
        }

        for (int col = 0; col < 9; col++) {

            addSlot(new Slot(
                    playerInventory,
                    col,
                    8 + col * 18,
                    198
            ));
        }
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return container.stillValid(player);
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int index) {

        ItemStack result;

        Slot slot = slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();

        result = stack.copy();

        int containerSlots = 54;

        if (index < containerSlots) {

            if (!moveItemStackTo(
                    stack,
                    containerSlots,
                    slots.size(),
                    true
            )) {
                return ItemStack.EMPTY;
            }

        } else {

            if (!moveItemStackTo(
                    stack,
                    0,
                    containerSlots,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return result;
    }
}