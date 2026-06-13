package de.niclasl.herobrines_world.common.network.transfer;

import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.*;
import de.niclasl.herobrines_world.common.registries.blocks.entities.AutoFarmerBlockEntity;
import de.niclasl.herobrines_world.common.registries.blocks.entities.StorageControllerBlockEntity;
import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ItemTransferSystem {

    public static void tick(
            InventoryWrapper source,
            InventoryWrapper target,
            TransferMode mode,
            int maxSlot
    ) {
        if (mode == TransferModeImpl.INSERT) {
            insert(source, target, maxSlot);
        } else if (mode == TransferModeImpl.EXTRACT) {
            extract(source, target, maxSlot);
        }
    }

    public static void insert(InventoryWrapper source, InventoryWrapper target, int maxSlot) {
        for (int i = 0; i < maxSlot; i++) {

            ItemStack original = source.get(i);
            if (original.isEmpty()) continue;

            ItemStack working = original.copy();

            ItemStack remaining = insertInto(target, working);

            int moved = original.getCount() - remaining.getCount();

            if (moved > 0) {
                original.shrink(moved);
                source.set(i, original.isEmpty() ? ItemStack.EMPTY : original);
            }
        }
    }

    public static void extract(InventoryWrapper source, InventoryWrapper target, int maxSlot) {
        for (int i = 0; i < maxSlot; i++) {

            ItemStack original = target.get(i);
            if (original.isEmpty()) continue;

            ItemStack working = original.copy();

            ItemStack remaining = insertInto(source, working);

            int moved = original.getCount() - remaining.getCount();

            if (moved > 0) {
                original.shrink(moved);
                target.set(i, original.isEmpty() ? ItemStack.EMPTY : original);
            }
        }
    }

    public static ItemStack insertInto(InventoryWrapper target, ItemStack stack) {
        if (!target.canAccept(stack)) {
            return stack;
        }

        for (int slot = 0; slot < target.size(); slot++) {

            ItemStack existing = target.get(slot);

            if (!existing.isEmpty()
                    && !ItemStack.isSameItemSameComponents(existing, stack)) {
                continue;
            }

            if (existing.isEmpty()) {

                target.set(slot, stack.copy());

                stack.setCount(0);

                return ItemStack.EMPTY;
            }

            int max = existing.getMaxStackSize();

            int space = max - existing.getCount();

            if (space <= 0) {
                continue;
            }

            int move = Math.min(space, stack.getCount());

            existing.grow(move);

            stack.shrink(move);

            target.set(slot, existing);

            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
        }

        return stack;
    }

    public static InventoryWrapper getWrapper(Level level, BlockPos pos, ItemStack filter) {

        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof AutoFarmerBlockEntity) {
            return null;
        }

        if (be instanceof AbstractFurnaceBlockEntity) {
            return null;
        }

        if (be instanceof BrewingStandBlockEntity) {
            return null;
        }

        if (be instanceof StorageControllerBlockEntity) {
            return null;
        }

        if (be instanceof ChestBlockEntity) {
            return getChestWrapper(level, pos, filter);
        }

        if (be instanceof Container container) {
            return new SingleInventoryWrapper(container, filter);
        }

        return null;
    }

    public static InventoryWrapper getChestWrapper(Level level, BlockPos pos, ItemStack filter) {

        BlockEntity be = level.getBlockEntity(pos);

        if (!(be instanceof Container container)) {
            return null;
        }

        List<Container> parts = new ArrayList<>();
        parts.add(container);

        BlockState state = level.getBlockState(pos);

        for (Direction dir : Direction.Plane.HORIZONTAL) {

            BlockPos neighborPos = pos.relative(dir);

            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.getBlock() != state.getBlock()) {
                continue;
            }

            BlockEntity otherBe = level.getBlockEntity(neighborPos);

            if (otherBe instanceof Container otherContainer) {
                parts.add(otherContainer);
            }
        }

        return new CombinedChestWrapper(parts, filter);
    }
}