package de.niclasl.herobrines_world.common.registries.menus;

import de.niclasl.herobrines_world.common.registries.blocks.entities.DelayerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class DelayerMenu extends AbstractContainerMenu {

    private final Level level;
    public BlockPos pos;
    public Player player;

    public DelayerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, extraData.readBlockPos());
    }

    public DelayerMenu(int id, Inventory inv, BlockPos pos) {
        super(ModMenuTypes.DELAYER.get(), id);
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

    public DelayerBlockEntity getEntity() {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof DelayerBlockEntity delayer)) {
            throw new IllegalStateException("DelayerEntity not found at " + pos);
        }
        return delayer;
    }
}