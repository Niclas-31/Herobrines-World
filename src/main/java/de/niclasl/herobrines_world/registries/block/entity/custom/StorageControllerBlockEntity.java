package de.niclasl.herobrines_world.registries.block.entity.custom;

import de.niclasl.herobrines_world.network.transfer.ItemTransferSystem;
import de.niclasl.herobrines_world.network.transfer.node.StorageNode;
import de.niclasl.herobrines_world.network.transfer.wrapper.BlockInventoryWrapper;
import de.niclasl.herobrines_world.network.transfer.wrapper.IInventoryWrapper;
import de.niclasl.herobrines_world.registries.block.custom.StorageControllerBlock;
import de.niclasl.herobrines_world.registries.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.registries.screen.custom.StorageControllerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StorageControllerBlockEntity extends BlockEntity implements Container, MenuProvider {

    private static final int RANGE = 16;

    private NonNullList<ItemStack> items = NonNullList.withSize(54, ItemStack.EMPTY);

    private final List<StorageNode> network = new ArrayList<>();

    private int scanTimer;
    private int sortTimer;

    public StorageControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CONTROLLER.get(), pos, state);
    }

    @Override
    public void handleUpdateTag(@NotNull ValueInput input) {
        super.handleUpdateTag(input);

        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);

        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);

        ContainerHelper.saveAllItems(output, this.items);
    }

    public boolean getMode() {
        if (level == null) return false;
        return level.getBlockState(worldPosition).getValue(StorageControllerBlock.POWERED);
    }

    public void tick(Level level, StorageControllerBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        if (!getMode()) return;

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

            IInventoryWrapper wrapper = ItemTransferSystem.getWrapper(level, pos, filter);

            if (wrapper == null) continue;

            network.add(new StorageNode(
                    wrapper,
                    filter
            ));
        }

        setChanged();
    }

    private void sortItems() {

        IInventoryWrapper source =
                new BlockInventoryWrapper(this);

        for (int slot = 0; slot < source.size(); slot++) {

            ItemStack stack = source.get(slot);

            if (stack.isEmpty()) {
                continue;
            }

            boolean inserted = tryInsert(
                    stack,
                    true
            );

            if (!inserted) {
                inserted = tryInsert(
                        stack,
                        false
                );
            }

            if (stack.isEmpty()) {
                source.set(slot, ItemStack.EMPTY);
            }
        }
    }

    private boolean tryInsert(
            ItemStack stack,
            boolean filteredOnly
    ) {

        for (StorageNode node : network) {

            IInventoryWrapper target = node.inventory();

            if (target == null) {
                continue;
            }

            boolean filtered =
                    !node.filter().isEmpty();

            if (filteredOnly && !filtered) {
                continue;
            }

            if (!filteredOnly && filtered) {
                continue;
            }

            if (target.canAccept(stack)) {
                continue;
            }

            ItemStack moving = stack.copy();

            int before = moving.getCount();

            ItemStack remaining =
                    ItemTransferSystem.insertInto(
                            target,
                            moving
                    );

            int moved =
                    before - remaining.getCount();

            if (moved <= 0) {
                continue;
            }

            stack.shrink(moved);

            return true;
        }

        return false;
    }

    private ItemStack readFilter(Level level, BlockPos chestPos) {

        for (Direction dir : Direction.values()) {

            BlockPos sidePos = chestPos.relative(dir);

            AABB box = new AABB(sidePos);

            List<ItemFrame> frames = level.getEntitiesOfClass(ItemFrame.class, box);

            for (ItemFrame frame : frames) {

                if (frame.getDirection() != dir) continue;

                ItemStack displayed = frame.getItem();

                if (!displayed.isEmpty()) {
                    return displayed.copyWithCount(1);
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public void setItem(NonNullList<ItemStack> items) {
        this.items = items;
        setChanged();
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NonNull ItemStack getItem(int slot) {
        return items.get(slot);
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
    public void setItem(int slot, @NonNull ItemStack stack) {
        items.set(slot, stack);
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5,
                worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
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