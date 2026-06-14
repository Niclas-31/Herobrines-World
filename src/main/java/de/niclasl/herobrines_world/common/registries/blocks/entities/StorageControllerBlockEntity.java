package de.niclasl.herobrines_world.common.registries.blocks.entities;

import de.niclasl.herobrines_world.common.network.transfer.ItemTransferSystem;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.StorageControllerInventoryWrapper;
import de.niclasl.herobrines_world.common.registries.blocks.custom.StorageControllerBlock;
import de.niclasl.herobrines_world.common.registries.menus.StorageControllerMenu;
import de.niclasl.herobrines_world_api.api.transfer.node.StorageNode;
import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StorageControllerBlockEntity extends BlockEntity implements Container, MenuProvider {

    private static final int RANGE = 16;

    private final NonNullList<ItemStack> items = NonNullList.withSize(54, ItemStack.EMPTY);
    private final List<StorageNode> network = new ArrayList<>();

    private int scanTimer;
    private int sortTimer;

    public StorageControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CONTROLLER.get(), pos, state);
    }

    public void tick(Level level, StorageControllerBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!be.getMode()) return;

        be.scanTimer++;
        be.sortTimer++;

        if (be.scanTimer >= 100) {
            be.scanNetwork(serverLevel);
            be.scanTimer = 0;
        }

        if (be.sortTimer >= 20) {
            be.sortItems();
            be.sortTimer = 0;
        }
    }

    private void scanNetwork(ServerLevel level) {

        network.clear();

        BlockPos min = worldPosition.offset(-RANGE, -RANGE, -RANGE);
        BlockPos max = worldPosition.offset(RANGE, RANGE, RANGE);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {

            if (pos.equals(worldPosition)) continue;

            ItemStack filter = readFilter(level, pos);

            InventoryWrapper wrapper = ItemTransferSystem.getWrapper(level, pos, filter);

            if (wrapper == null) continue;

            network.add(new StorageNode(wrapper, filter));
        }

        setChanged();
    }

    private void sortItems() {

        InventoryWrapper source = new StorageControllerInventoryWrapper(this);

        for (int slot = 0; slot < source.size(); slot++) {

            ItemStack stack = source.get(slot);
            if (stack.isEmpty()) continue;

            ItemStack original = stack.copy();

            for (StorageNode node : network) {

                InventoryWrapper target = node.inventory();
                if (target == null) continue;

                ItemStack filter = node.filter();

                if (filter.isEmpty()) continue;

                if (!target.canAccept(stack)) continue;

                stack = ItemTransferSystem.insertInto(target, stack);

                if (stack.isEmpty()) break;
            }

            if (!ItemStack.matches(original, stack)) {
                source.set(slot, stack);
            }
        }
    }

    private ItemStack readFilter(Level level, BlockPos pos) {

        AABB box = new AABB(pos).inflate(0.1);

        List<ItemFrame> frames =
                level.getEntitiesOfClass(ItemFrame.class, box);

        for (ItemFrame frame : frames) {

            ItemStack item = frame.getItem();

            if (!item.isEmpty()) {
                return item.copyWithCount(1);
            }
        }

        return ItemStack.EMPTY;
    }

    public boolean getMode() {
        if (level == null) return false;
        return level.getBlockState(worldPosition)
                .getValue(StorageControllerBlock.POWERED);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack s : items) if (!s.isEmpty()) return false;
        return true;
    }

    @Override
    public @NonNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public void setItem(int slot, @NonNull ItemStack stack) {
        items.set(slot, stack);
        setChanged();
    }

    @Override
    public @NonNull ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(items, slot, amount);
        if (!stack.isEmpty()) setChanged();
        return stack;
    }

    @Override
    public @NonNull ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getCenter()) <= 64;
    }

    @Override
    public @NonNull Component getDisplayName() {
        return Component.translatable("block.herobrines_world.storage_controller");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NonNull Inventory inv, @NonNull Player player) {
        return new StorageControllerMenu(id, inv, this);
    }
}