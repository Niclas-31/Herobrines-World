package de.niclasl.herobrines_world.common.network.transfer;

import de.niclasl.herobrines_world.common.network.transfer.wrapper.CombinedChestWrapper;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.SingleInventoryWrapper;
import de.niclasl.herobrines_world.common.registries.blocks.entities.AutoFarmerBlockEntity;
import de.niclasl.herobrines_world.common.registries.blocks.entities.StorageControllerBlockEntity;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTransferSystem {

    public static void tick(InventoryWrapper source, InventoryWrapper target, TransferMode mode,
                            int maxSlot, int keepAmount) {
        if (mode == TransferModeImpl.INSERT) {
            insert(source, target, maxSlot, keepAmount);
        } else if (mode == TransferModeImpl.EXTRACT) {
            extract(source, target, maxSlot, keepAmount);
        }
    }

    public static void insert(InventoryWrapper source, InventoryWrapper target,
                              int maxSlot, int keepAmount) {
        Map<Item, Integer> totalSeeds = new HashMap<>();

        for (int i = 0; i < maxSlot; i++) {
            ItemStack stack = source.get(i);
            if (stack.isEmpty()) continue;
            if (!isSeed(stack)) continue;

            totalSeeds.merge(stack.getItem(), stack.getCount(), Integer::sum);
        }

        Map<Item, Integer> transferableSeeds = new HashMap<>();
        for (var e : totalSeeds.entrySet()) {
            transferableSeeds.put(e.getKey(),
                    Math.max(0, e.getValue() - keepAmount));
        }

        // 🌾 2. Crops: KEINE Begrenzung
        for (int i = 0; i < maxSlot; i++) {

            ItemStack stack = source.get(i);
            if (stack.isEmpty()) continue;

            Item item = stack.getItem();

            int move;

            if (isSeed(stack)) {
                int available = transferableSeeds.getOrDefault(item, 0);
                if (available <= 0) continue;

                move = Math.min(stack.getCount(), available);

                transferableSeeds.put(item, available - move);
            }
            else if (isCrop(stack)) {
                move = stack.getCount();
            }
            else {
                continue;
            }

            ItemStack copy = stack.copy();
            copy.setCount(move);

            ItemStack rest = insertInto(target, copy);
            int moved = move - rest.getCount();

            if (moved > 0) {
                stack.shrink(moved);
                source.set(i, stack.isEmpty() ? ItemStack.EMPTY : stack);
            }
        }
    }

    public static void extract(InventoryWrapper source, InventoryWrapper target,
                               int maxSlot, int keepAmount) {
        Map<Item, Integer> total = new HashMap<>();

        for (int i = 0; i < maxSlot; i++) {
            ItemStack stack = source.get(i);
            if (stack.isEmpty()) continue;
            if (!isSeed(stack)) continue;

            total.merge(stack.getItem(), stack.getCount(), Integer::sum);
        }

        Map<Item, Integer> transferable = new HashMap<>();

        for (var entry : total.entrySet()) {
            int canMove = Math.max(0, entry.getValue() - keepAmount);

            transferable.put(entry.getKey(), canMove);
        }

        for (int i = 0; i < maxSlot; i++) {

            ItemStack stack = target.get(i);
            if (stack.isEmpty()) continue;

            Item item = stack.getItem();

            int available = transferable.getOrDefault(item, 0);
            if (available <= 0) continue;

            int move;

            if (isSeed(stack)) {
                move = Math.min(stack.getCount(), available);
            } else if (isCrop(stack)) {
                move = stack.getCount();
            } else {
                continue;
            }

            ItemStack copy = stack.copy();
            copy.setCount(move);

            ItemStack rest = insertInto(source, copy);
            int moved = move - rest.getCount();

            if (moved > 0) {
                stack.shrink(moved);
                target.set(i, stack.isEmpty() ? ItemStack.EMPTY : stack);
            }
        }
    }

    public static ItemStack insertInto(InventoryWrapper target, ItemStack stack) {

        if (!target.canAccept(stack)) return stack;

        for (int slot = 0; slot < target.size(); slot++) {

            ItemStack existing = target.get(slot);

            if (existing.isEmpty()) {
                target.set(slot, stack.copy());
                return ItemStack.EMPTY;
            }

            int max = existing.getMaxStackSize();
            int space = max - existing.getCount();

            if (space <= 0) continue;

            int move = Math.min(space, stack.getCount());

            existing.grow(move);
            stack.shrink(move);

            target.set(slot, existing);

            if (stack.isEmpty()) return ItemStack.EMPTY;
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

    private static boolean isSeed(ItemStack stack) {
        return stack.is(Items.WHEAT_SEEDS)
                || stack.is(Items.BEETROOT_SEEDS)
                || stack.is(Items.POTATO)
                || stack.is(Items.CARROT);
    }

    private static boolean isCrop(ItemStack stack) {
        return stack.is(Items.WHEAT)
                || stack.is(Items.BEETROOT);
    }
}