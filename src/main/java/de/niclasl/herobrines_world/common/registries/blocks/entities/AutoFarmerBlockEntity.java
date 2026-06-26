package de.niclasl.herobrines_world.common.registries.blocks.entities;

import de.niclasl.herobrines_world.common.network.transfer.ItemTransferSystem;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.AutoFarmerWrapper;
import de.niclasl.herobrines_world.common.registries.blocks.custom.AutoFarmerBlock;
import de.niclasl.herobrines_world.common.registries.blocks.properties.FarmerMode;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SmartChipData;
import de.niclasl.herobrines_world.common.registries.items.custom.BatteryItem;
import de.niclasl.herobrines_world.common.registries.menus.AutoFarmerMenu;
import de.niclasl.herobrines_world_api.api.transfer.TransferAPI;
import de.niclasl.herobrines_world_api.api.transfer.resolver.InventoryResolver;
import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
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
    private NonNullList<ItemStack> items = NonNullList.withSize(29, ItemStack.EMPTY);
    private int cleanupTimer = 0;
    private int ticks = 0;

    public AutoFarmerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_FARMER.get(), pos, state);
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
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);

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
        items = NonNullList.withSize(28, ItemStack.EMPTY);
        setChanged();
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
        if (!(level instanceof ServerLevel serverLevel)) return;

        ItemStack chipStack = be.items.get(28);
        SmartChipData.Transfer chip = chipStack.getOrDefault(ModDataComponents.TRANSFER, SmartChipData.Transfer.DEFAULT);

        if (chipStack.isEmpty()) {
            level.setBlock(pos, state.setValue(AutoFarmerBlock.POWERED, false), 3);
            return;
        }

        int range = chip.range();
        int speed = chip.speed();

        ItemStack battery = be.items.get(27);

        boolean hasEnergy = battery.getItem() instanceof BatteryItem b &&
                b.getEnergy(battery) > 0;

        if (state.getValue(AutoFarmerBlock.POWERED) != hasEnergy) {
            level.setBlock(pos, state.setValue(AutoFarmerBlock.POWERED, hasEnergy), 3);
        }

        if (!hasEnergy) return;

        int interval = switch (speed) {
            case 1 -> 3000;
            case 2 -> 6000;
            default -> 1500;
        };

        int workDelay = switch (speed) {
            case 1 -> 50;
            case 2 -> 20;
            default -> 1200;
        };

        if (level.getGameTime() % workDelay == 0) {
            switch (be.getMode()) {
                case BREAKER -> be.breakCrops(range);
                case PLACER -> be.placeCrops(range);
                case BOTH -> {
                    be.breakCrops(range);
                    be.placeCrops(range);
                }
            }
        }

        be.ticks++;

        if (be.ticks >= interval) {
            be.useEnergyOfBattery();
            be.ticks = 0;
        }

        be.cleanupTimer++;

        if (be.cleanupTimer >= workDelay) {
            be.transferAndFilter(serverLevel, chip);
            be.cleanupTimer = 0;
        }
    }

    private void transferAndFilter(ServerLevel level, SmartChipData.Transfer chip) {
        InventoryWrapper source = new AutoFarmerWrapper(this.items);

        List<InventoryResolver> resolvers = TransferAPI.getResolvers();

        for (InventoryResolver resolver : resolvers) {
            InventoryWrapper target = resolver.resolve(level, chip.pos(), chip.dim());

            applyBufferRules(chip.voidTrash());

            ItemTransferSystem.tick(source, target, chip.mode(), 27, chip.keepAmount());
        }
    }

    private void applyBufferRules(boolean voidTrash) {
        for (int i = 0; i < 27; i++) {

            ItemStack stack = items.get(i);
            if (stack.isEmpty()) continue;

            if (voidTrash && isTrash(stack)) {
                items.set(i, ItemStack.EMPTY);
            }
        }

        setChanged();
    }

    private boolean isTrash(ItemStack stack) {
        return stack.is(Items.POISONOUS_POTATO)
                || stack.is(Items.ROTTEN_FLESH)
                || stack.is(Items.DIRT)
                || stack.is(Items.COBBLESTONE);
    }

    private void useEnergyOfBattery() {
        ItemStack stack = items.get(27);
        if (stack.getItem() instanceof BatteryItem item) {
            item.consumeEnergy(stack, 10);
            setChanged();
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    private void breakCrops(int range) {
        if (level == null || level.isClientSide()) return;

        BlockPos origin = this.getBlockPos();

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
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

    private void placeCrops(int range) {
        if (level == null || level.isClientSide()) return;

        BlockPos origin = this.getBlockPos();

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
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
                setChanged();
                return;
            } else if (ItemStack.isSameItemSameComponents(slot, stack) && slot.getCount() < slot.getMaxStackSize()) {
                int space = slot.getMaxStackSize() - slot.getCount();
                int toAdd = Math.min(space, stack.getCount());

                slot.grow(toAdd);
                stack.shrink(toAdd);

                setChanged();

                if (stack.isEmpty()) return;
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