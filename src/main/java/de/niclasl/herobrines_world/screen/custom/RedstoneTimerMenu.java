package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.block.entity.custom.RedstoneTimerBlockEntity;
import de.niclasl.herobrines_world.screen.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RedstoneTimerMenu extends AbstractContainerMenu {

    public BlockPos pos;
    public Player player;
    public RedstoneTimerBlockEntity be;

    public RedstoneTimerMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        super(ModMenuTypes.REDSTONE_TIMER.get(), id);
        BlockPos pos;
        if (buf != null) {
            pos = buf.readBlockPos();
            this.pos = pos;
            this.be = (RedstoneTimerBlockEntity) inv.player.level().getBlockEntity(pos);
        }
        this.player = inv.player;
    }

    public RedstoneTimerMenu(int id, Inventory inv, BlockPos pos) {
        super(ModMenuTypes.REDSTONE_TIMER.get(), id);
        this.pos = pos;
        this.player = inv.player;
        this.be = (RedstoneTimerBlockEntity) inv.player.level().getBlockEntity(pos);
    }

    public RedstoneTimerBlockEntity getBlockEntity() {
        return be;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}