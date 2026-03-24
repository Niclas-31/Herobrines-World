package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.custom.AutoFarmerBlock;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.properties.FarmerMode;
import de.niclasl.herobrines_world.screen.custom.AutoFarmerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AutoFarmerBlockEntity extends BlockEntity implements Container, MenuProvider {

    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private static final int RADIUS = 4;
    private int cleanupTimer = 0;

    public AutoFarmerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_FARMER.get(), pos, state);
    }

    @Override
    public void handleUpdateTag(@NotNull ValueInput input) {
        super.handleUpdateTag(input);

        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);

        String modeName = input.getStringOr("FarmerMode", FarmerMode.BREAKER.name());
        try {
            this.setMode(FarmerMode.valueOf(modeName));
        } catch (IllegalArgumentException e) {
            this.setMode(FarmerMode.BREAKER);
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);

        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);

        ContainerHelper.saveAllItems(output, this.items);
    }

    public FarmerMode getMode() {
        if (level == null) return FarmerMode.BREAKER;
        return level.getBlockState(worldPosition).getValue(AutoFarmerBlock.FARMER_MODE);
    }

    private void setMode(FarmerMode farmerMode) {
        assert level != null;
        level.setBlock(getBlockPos(), getBlockState().setValue(AutoFarmerBlock.FARMER_MODE, farmerMode), 3);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int count) {
        ItemStack stack = ContainerHelper.removeItem(items, index, count);
        if (!stack.isEmpty()) setChanged();
        return stack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = items.get(index);
        items.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        items.set(index, stack);
        setChanged();
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(
                worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5,
                worldPosition.getZ() + 0.5
        ) <= 64.0;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AutoFarmerBlockEntity be) {
        if (level.isClientSide()) return;

        if (level.getGameTime() % 20 == 0) {
            be.doWork(state);
        }

        be.cleanupTimer++;

        if (be.cleanupTimer >= 1200) {
            be.cleanupSeeds();
            be.cleanupTimer = 0;
        }
    }

    private void cleanupSeeds() {
        boolean keptNormalSeeds = false;
        boolean keptBeetrootSeeds = false;

        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);

            if (stack.isEmpty()) continue;

            if (stack.getItem() == Items.POISONOUS_POTATO) {
                items.set(i, ItemStack.EMPTY);
                continue;
            }

            if (stack.getItem() == Items.BEETROOT_SEEDS) {
                if (!keptBeetrootSeeds) {
                    if (stack.getCount() > stack.getMaxStackSize()) {
                        stack.setCount(stack.getMaxStackSize());
                    }
                    keptBeetrootSeeds = true;
                } else {
                    items.set(i, ItemStack.EMPTY);
                }
                continue;
            }

            if (isSeed(stack)) {
                if (!keptNormalSeeds) {
                    if (stack.getCount() > stack.getMaxStackSize()) {
                        stack.setCount(stack.getMaxStackSize());
                    }
                    keptNormalSeeds = true;
                } else {
                    items.set(i, ItemStack.EMPTY);
                }
            }
        }

        setChanged();
    }

    private boolean isSeed(ItemStack stack) {
        if (stack.getItem() == Items.CARROT || stack.getItem() == Items.POTATO) {
            return false;
        }

        if (stack.getItem() instanceof BlockItem blockItem) {
            return blockItem.getBlock() instanceof CropBlock;
        }
        return false;
    }

    private void breakCrops() {
        if (level == null || level.isClientSide()) return;

        BlockPos origin = this.getBlockPos();

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                BlockPos target = origin.offset(x, 0, z);
                BlockState state = level.getBlockState(target);

                if (state.getBlock() instanceof CropBlock crop) {

                    if (crop.isMaxAge(state)) {
                        List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, target, null);

                        for (ItemStack stack : drops) {
                            addItemToInventory(stack);
                        }

                        level.destroyBlock(target, false);
                    }
                }
            }
        }
    }

    private void placeCrops() {
        if (level == null || level.isClientSide()) return;

        BlockPos origin = this.getBlockPos();

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                BlockPos target = origin.offset(x, 0, z);
                BlockState state = level.getBlockState(target);

                if (state.isAir()) {
                    BlockPos below = target.below();
                    BlockState belowState = level.getBlockState(below);

                    if (belowState.getBlock() instanceof FarmBlock) {

                        for (ItemStack stack : items) {
                            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                                Block block = blockItem.getBlock();

                                if (block instanceof CropBlock) {
                                    level.setBlock(target, block.defaultBlockState(), 3);

                                    stack.shrink(1);
                                    setChanged();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addItemToInventory(ItemStack stack) {
        for (int i = 0; i < items.size(); i++) {
            ItemStack slot = items.get(i);

            if (slot.isEmpty()) {
                items.set(i, stack.copy());
                return;
            } else if (ItemStack.isSameItemSameComponents(slot, stack) && slot.getCount() < slot.getMaxStackSize()) {
                int space = slot.getMaxStackSize() - slot.getCount();
                int toAdd = Math.min(space, stack.getCount());

                slot.grow(toAdd);
                stack.shrink(toAdd);

                if (stack.isEmpty()) return;
            }
        }
    }

    private void doWork(BlockState state) {
        if (!state.getValue(AutoFarmerBlock.POWERED)) return;

        switch (getMode()) {
            case BREAKER -> breakCrops();
            case PLACER -> placeCrops();
            case BOTH -> {
                breakCrops();
                placeCrops();
            }
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.herobrines_world.auto_farmer");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new AutoFarmerMenu(id, playerInventory, this);
    }
}