package de.niclasl.herobrines_world.common.registries.blocks.entities;

import de.niclasl.herobrines_world.common.network.access.AccessModeImpl;
import de.niclasl.herobrines_world.common.registries.blocks.custom.CardReaderBlock;
import de.niclasl.herobrines_world.common.registries.components.KeyCardData;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SmartChipData;
import de.niclasl.herobrines_world.common.registries.menus.CardReaderMenu;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class CardReaderBlockEntity extends BlockEntity implements Container, MenuProvider {

    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    public int accessTicks;

    public CardReaderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CARD_READER.get(), pos, state);
    }

    public void tick(Level level, CardReaderBlockEntity be) {
        if (level.isClientSide()) return;

        if (be.accessTicks > 0) {
            be.accessTicks--;

            if (be.accessTicks <= 0) {
                BlockState state = level.getBlockState(be.worldPosition);

                if (state.getValue(CardReaderBlock.POWERED)) {
                    level.setBlock(be.worldPosition, state.setValue(CardReaderBlock.POWERED, false), 3);
                }
            }
        }

        ItemStack chip = be.items.getFirst();
        ItemStack card = be.items.getLast();

        if (chip.isEmpty() || card.isEmpty()) return;

        SmartChipData.Access access = chip.getOrDefault(ModDataComponents.ACCESS, SmartChipData.Access.DEFAULT);
        UUID owner = access.owner();
        int accessLevel = access.level();

        card.set(ModDataComponents.KEY_CARD_DATA, new KeyCardData(
                owner,
                accessLevel
        ));
    }

    public boolean canAccess(ItemStack card) {
        SmartChipData.Access access = items.getFirst().getOrDefault(ModDataComponents.ACCESS, SmartChipData.Access.DEFAULT);

        AccessMode mode = access.mode();

        if (mode == AccessModeImpl.PUBLIC) return true;

        KeyCardData data = card.get(ModDataComponents.KEY_CARD_DATA);

        if (data == null) return false;

        UUID playerUUID = access.owner();

        if (mode == AccessModeImpl.PRIVATE) return playerUUID.equals(data.owner());
        else if (mode == AccessModeImpl.TRUSTED) return data.level() >= access.level();

        return false;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(@NonNull ValueInput input) {
        super.handleUpdateTag(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
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
        return Component.translatable("block.herobrines_world.card_reader");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NonNull Inventory inventory, @NonNull Player player) {
        return new CardReaderMenu(id, inventory, this);
    }
}