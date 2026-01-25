package de.niclasl.herobrines_world.world.inventory.custom;

import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import de.niclasl.herobrines_world.network.data.VisibleNetwork;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WirelessReceiverMenu extends AbstractContainerMenu {

    private final WirelessReceiverBlockEntity be;

    public WirelessReceiverMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv,
                (WirelessReceiverBlockEntity)
                        inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public WirelessReceiverMenu(int id, Inventory inv, WirelessReceiverBlockEntity be) {
        super(ModMenus.WIRELESS_RECEIVER.get(), id);
        this.be = be;
    }

    public List<VisibleNetwork> getNetworks() {
        if (be != null) {
            be.updateVisibleNetworks();
            return be.getVisibleNetworks();
        }
        return List.of();
    }

    public BlockPos getBlockPos() {
        return be.getBlockPos();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
        return ItemStack.EMPTY;
    }
}
