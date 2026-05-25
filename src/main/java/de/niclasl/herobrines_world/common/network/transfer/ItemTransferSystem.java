package de.niclasl.herobrines_world.common.network.transfer;

import de.niclasl.herobrines_world.common.network.transfer.wrapper.*;
import de.niclasl.herobrines_world.common.registries.block.entity.AutoFarmerBlockEntity;
import de.niclasl.herobrines_world.common.registries.block.entity.CardReaderBlockEntity;
import de.niclasl.herobrines_world.common.registries.block.entity.StorageControllerBlockEntity;
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
            IInventoryWrapper source,
            IInventoryWrapper target,
            TransferMode mode,
            int maxSlot
    ) {
        switch (mode) {

            case INSERT -> insert(source, target, maxSlot);
            case EXTRACT -> extract(source, target, maxSlot);
            case BALANCE -> balance(source, target, maxSlot);
        }
    }

    private static void insert(IInventoryWrapper source, IInventoryWrapper target, int maxSlot) {
        for (int i = 0; i < maxSlot; i++) {

            ItemStack original = source.get(i);

            if (original.isEmpty()) continue;

            int before = original.getCount();

            ItemStack moving = original.copy();

            ItemStack remaining = insertInto(target, moving);

            int moved = before - remaining.getCount();

            if (moved <= 0) continue;

            original.shrink(moved);

            if (original.isEmpty()) {
                source.set(i, ItemStack.EMPTY);
            }
        }
    }

    private static void extract(IInventoryWrapper source, IInventoryWrapper target, int maxSlot) {
        for (int i = 0; i < maxSlot; i++) {

            ItemStack original = target.get(i);

            if (original.isEmpty()) continue;

            ItemStack moving = original.copy();

            ItemStack remaining = insertInto(source, moving);

            int moved = moving.getCount() - remaining.getCount();

            if (moved <= 0) continue;

            original.shrink(moved);

            if (original.isEmpty()) {
                target.set(i, ItemStack.EMPTY);
            }
        }
    }

    private static void balance(IInventoryWrapper a, IInventoryWrapper b, int maxSlot) {
        for (int i = 0; i < maxSlot; i++) {

            ItemStack original = a.get(i);

            if (original.isEmpty()) continue;

            int totalA = count(a, original, maxSlot);
            int totalB = count(b, original, maxSlot);

            int total = totalA + totalB;

            int desired = total / 2;

            if (totalA <= desired) continue;

            int transferAmount = totalA - desired;

            ItemStack transferStack = original.copyWithCount(transferAmount);

            ItemStack remaining = insertInto(b, transferStack);

            int moved = transferAmount - remaining.getCount();

            if (moved > 0) {
                original.shrink(moved);
            }

            if (original.isEmpty()) {
                a.set(i, ItemStack.EMPTY);
            }
        }
    }

    public static ItemStack insertInto(
            IInventoryWrapper target,
            ItemStack stack
    ) {

        if (target.canAccept(stack)) {
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

    private static int count(IInventoryWrapper inventory, ItemStack match, int maxSlot) {
        int count = 0;

        for (int slot = 0; slot < maxSlot; slot++) {

            ItemStack stack = inventory.get(slot);

            if (ItemStack.isSameItemSameComponents(stack, match)) {
                count += stack.getCount();
            }
        }

        return count;
    }

    public static IInventoryWrapper getWrapper(Level level, BlockPos pos, ItemStack filter) {

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

        if (be instanceof CardReaderBlockEntity) {
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

    public static IInventoryWrapper getChestWrapper(Level level, BlockPos pos, ItemStack filter) {

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