package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.block.entity.custom.AutoFarmerBlockEntity;
import de.niclasl.herobrines_world.item.custom.BatteryItem;
import de.niclasl.herobrines_world.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class AutoFarmerMenu extends AbstractContainerMenu {

    private final AutoFarmerBlockEntity blockEntity;

    private static final int SLOT_SIZE = 18;

    public AutoFarmerMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, playerInventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public AutoFarmerMenu(int id, Inventory playerInventory, BlockEntity blockEntity) {
        super(ModMenuTypes.AUTO_FARMER.get(), id);

        this.blockEntity = (AutoFarmerBlockEntity) blockEntity;

        int startX = 8;
        int startY = 18;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int index = col + row * 9;
                this.addSlot(new Slot(this.blockEntity, index,
                        startX + col * SLOT_SIZE,
                        startY + row * SLOT_SIZE));
            }
        }

        this.addSlot(new Slot(this.blockEntity, 27, 174, 36) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof BatteryItem;
            }
        });

        int playerStartY = startY + 3 * SLOT_SIZE + 12;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        startX + col * SLOT_SIZE,
                        playerStartY + row * SLOT_SIZE));
            }
        }

        int hotbarY = playerStartY + 3 * SLOT_SIZE + 4;

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col,
                    startX + col * SLOT_SIZE,
                    hotbarY));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return blockEntity != null && blockEntity.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack result;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        result = stack.copy();

        int blockStart = 0;
        int batterySlot = 27;
        int playerStart = 28;

        if (index >= blockStart && index < batterySlot) {
            if (!this.moveItemStackTo(stack, playerStart, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }

        } else if (index == batterySlot) {
            if (!this.moveItemStackTo(stack, playerStart, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }

        } else {
            if (stack.getItem() instanceof BatteryItem) {
                if (!this.moveItemStackTo(stack, batterySlot, batterySlot + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(stack, blockStart, batterySlot, false)) {
                    return ItemStack.EMPTY;
                }
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