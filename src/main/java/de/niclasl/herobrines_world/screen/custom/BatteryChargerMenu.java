package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.block.entity.custom.BatteryChargerBlockEntity;
import de.niclasl.herobrines_world.item.custom.BatteryItem;
import de.niclasl.herobrines_world.screen.ModMenuTypes;
import net.minecraft.core.BlockPos;
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
    private final BlockPos pos;

    public BatteryChargerMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenuTypes.BATTERY_CHARGER.get(), id);

        if (buf == null) {
            throw new IllegalStateException("Buffer is null!");
        }

        this.pos = buf.readBlockPos();

        BlockEntity be = inventory.player.level().getBlockEntity(pos);
        if (!(be instanceof BatteryChargerBlockEntity batteryCharger)) {
            throw new IllegalStateException("BlockEntity missing at: " + pos);
        }

        this.entity = batteryCharger;

        this.addSlot(new Slot(entity, 0, 80, 36) {
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

    public BatteryChargerMenu(int id, Inventory inventory, BatteryChargerBlockEntity entity) {
        super(ModMenuTypes.BATTERY_CHARGER.get(), id);

        this.entity = entity;
        this.pos = entity.getBlockPos();

        this.addSlot(new Slot(entity, 0, 80, 36) {
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
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        result = stack.copy();

        int batterySlotIndex = 0;
        int playerStart = 1;
        int playerEnd = this.slots.size();

        if (index == batterySlotIndex) {
            if (!this.moveItemStackTo(stack, playerStart, playerEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (stack.getItem() instanceof BatteryItem) {
                if (!this.moveItemStackTo(stack, batterySlotIndex, batterySlotIndex + 1, false)) {
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

    @Override
    public boolean stillValid(@NotNull Player player) {
        return entity != null && entity.stillValid(player);
    }
}