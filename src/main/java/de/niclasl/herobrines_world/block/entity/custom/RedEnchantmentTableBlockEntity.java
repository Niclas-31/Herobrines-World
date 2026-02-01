package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.world.inventory.custom.RedEnchantmentTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedEnchantmentTableBlockEntity extends BlockEntity implements Container, MenuProvider {

    private final NonNullList<ItemStack> items =
            NonNullList.withSize(1, ItemStack.EMPTY);

    public RedEnchantmentTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RED_ENCHANTMENT_TABLE.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.herobrines_world.red_enchantment_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new RedEnchantmentTableMenu(id, inventory, this, this.getBlockPos());
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.getFirst().isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(items, slot, amount);
        if (!stack.isEmpty()) setChanged();
        return stack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        items.set(slot, stack);
        setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }
}
