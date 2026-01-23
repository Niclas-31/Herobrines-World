package de.niclasl.herobrines_world.world.inventory.custom;

import de.niclasl.herobrines_world.block.entity.custom.DelayerEntity;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DelayerMenu extends AbstractContainerMenu implements ModMenus.MenuAccessor {

    private final Level level;
    public BlockPos pos;
    public Player player;

    public DelayerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ModMenus.DELAYER.get(), id);
        this.level = inv.player.level();
        BlockPos pos;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.pos = pos;
        }
        this.player = inv.player;
    }

    public DelayerMenu(int id, Inventory inv, BlockPos pos) {
        super(ModMenus.DELAYER.get(), id);
        this.level = inv.player.level();
        this.pos = pos;
        this.player = inv.player;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int move) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    public DelayerEntity getEntity() {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof DelayerEntity delayer)) {
            throw new IllegalStateException("DelayerEntity not found at " + pos);
        }
        return delayer;
    }

    @Override
    public Map<String, Object> getMenuState() {
        return Map.of();
    }

    @Override
    public Map<Integer, Slot> getSlots() {
        return Map.of();
    }
}