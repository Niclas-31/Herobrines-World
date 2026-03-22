package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.block.entity.custom.AutoFarmerBlockEntity;
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
import org.jetbrains.annotations.Nullable;

public class AutoFarmerMenu extends AbstractContainerMenu {

    private final AutoFarmerBlockEntity blockEntity;
    private final BlockPos pos;

    private static final int SLOT_SIZE = 18;

    public AutoFarmerMenu(int id, Inventory playerInventory, @Nullable FriendlyByteBuf buf) {
        super(ModMenuTypes.AUTO_FARMER.get(), id);

        int startX = 8;
        int startY = 18;

        if (buf != null) {
            this.pos = buf.readBlockPos();
            BlockEntity be = playerInventory.player.level().getBlockEntity(pos);

            if (be instanceof AutoFarmerBlockEntity autoFarmer) {
                this.blockEntity = autoFarmer;

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 9; col++) {
                        int index = col + row * 9;
                        this.addSlot(new Slot(blockEntity, index,
                                startX + col * SLOT_SIZE,
                                startY + row * SLOT_SIZE));
                    }
                }
            } else {
                this.blockEntity = null;
            }
        } else {
            this.pos = null;
            this.blockEntity = null;
        }

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

    public AutoFarmerMenu(int id, Inventory playerInventory, AutoFarmerBlockEntity blockEntity) {
        super(ModMenuTypes.AUTO_FARMER.get(), id);

        this.blockEntity = blockEntity;
        this.pos = blockEntity.getBlockPos();

        int startX = 8;
        int startY = 17;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int index = col + row * 9;
                this.addSlot(new Slot(blockEntity, index,
                        startX + col * SLOT_SIZE,
                        startY + row * SLOT_SIZE));
            }
        }

        int playerStartY = startY + 3 * SLOT_SIZE + 4;

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
        if (blockEntity == null) return false;

        return player.distanceToSqr(
                blockEntity.getBlockPos().getX() + 0.5,
                blockEntity.getBlockPos().getY() + 0.5,
                blockEntity.getBlockPos().getZ() + 0.5
        ) <= 64.0;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        if (blockEntity == null) return ItemStack.EMPTY;

        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            stack = original.copy();

            int blockSize = 27;

            if (index < blockSize) {
                if (!this.moveItemStackTo(original, blockSize, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(original, 0, blockSize, false))
                    return ItemStack.EMPTY;
            }

            if (original.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }

        return stack;
    }
}