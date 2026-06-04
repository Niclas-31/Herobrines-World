package de.niclasl.herobrines_world.common.registries.menu;

import de.niclasl.herobrines_world.common.registries.block.entity.BatteryChargerBlockEntity;
import de.niclasl.herobrines_world.common.registries.item.custom.BatteryItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class BatteryChargerMenu extends AbstractContainerMenu {

    private final BatteryChargerBlockEntity entity;

    public BatteryChargerMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public BatteryChargerMenu(int id, Inventory inventory, BlockEntity blockEntity) {
        super(ModMenuTypes.BATTERY_CHARGER.get(), id);

        this.entity = (BatteryChargerBlockEntity) blockEntity;

        this.addSlot(new Slot(entity, 0, 62, 36) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof BatteryItem;
            }
        });

        this.addSlot(new Slot(entity, 1, 98, 36) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof BatteryItem;
            }
        });

        int startX = 8;
        int startY = 18;

        int playerStartY = startY + 3 * SLOT_SIZE + 12;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9,
                        startX + col * SLOT_SIZE,
                        playerStartY + row * SLOT_SIZE));
            }
        }

        int hotbarY = playerStartY + 3 * SLOT_SIZE + 4;

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col,
                    startX + col * SLOT_SIZE,
                    hotbarY));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack result = stack.copy();

        int batteryStart = 0;
        int batteryEnd = 2;

        int playerStart = 2;
        int playerEnd = this.slots.size();

        if (index < batteryEnd) {
            if (!this.moveItemStackTo(stack, playerStart, playerEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (stack.getItem() instanceof BatteryItem) {
                if (!this.moveItemStackTo(stack, batteryStart, batteryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
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

    @Override
    public boolean stillValid(@NotNull Player player) {
        return entity != null && entity.stillValid(player);
    }
}