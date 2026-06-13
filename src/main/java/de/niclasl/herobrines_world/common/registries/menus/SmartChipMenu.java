package de.niclasl.herobrines_world.common.registries.menus;

import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SmartChipData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class SmartChipMenu extends AbstractContainerMenu {

    private final SmartChipData.Transfer transfer;
    private final SmartChipData.Access access;

    public SmartChipMenu(int id, @NonNull Inventory inv, FriendlyByteBuf buf) {
        this(id, inv.player.getMainHandItem());
    }

    public SmartChipMenu(int id, ItemStack chip) {
        super(ModMenuTypes.SMART_CHIP.get(), id);
        this.transfer = chip.getOrDefault(ModDataComponents.TRANSFER, SmartChipData.Transfer.DEFAULT);
        this.access = chip.getOrDefault(ModDataComponents.ACCESS, SmartChipData.Access.DEFAULT);
    }

    public SmartChipData.Transfer getTransferData() {
        return transfer;
    }

    public SmartChipData.Access getAccessData() {
        return access;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return true;
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int i) {
        return ItemStack.EMPTY;
    }
}