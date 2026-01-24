package de.niclasl.herobrines_world.world.inventory.custom;


import de.niclasl.herobrines_world.block.entity.custom.WirelessSenderBlockEntity;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WirelessSenderMenu extends AbstractContainerMenu implements ModMenus.MenuAccessor {

    private final WirelessSenderBlockEntity blockEntity;

    private final Map<Integer, Slot> slots = new HashMap<>();

    public WirelessSenderMenu(int id, WirelessSenderBlockEntity be) {
        super(ModMenus.WIRELESS_SENDER.get(), id);
        this.blockEntity = be;
    }

    public WirelessSenderMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        super(ModMenus.WIRELESS_SENDER.get(), id);
        this.blockEntity = inv.player.level().getBlockEntity(buf.readBlockPos()) instanceof WirelessSenderBlockEntity be
                ? be
                : null;
    }

    public WirelessSenderBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return blockEntity != null && player.distanceToSqr(
                blockEntity.getBlockPos().getX() + 0.5,
                blockEntity.getBlockPos().getY() + 0.5,
                blockEntity.getBlockPos().getZ() + 0.5
        ) <= 64;
    }

    @Override
    public Map<String, Object> getMenuState() {
        if (blockEntity == null) return Map.of();
        return Map.of(
                "networkName", blockEntity.getNetworkName(),
                "password", blockEntity.getPassword(),
                "range", blockEntity.getRange(),
                "powered", blockEntity.isPowered()
        );
    }

    @Override
    public Map<Integer, Slot> getSlots() {
        return slots;
    }
}