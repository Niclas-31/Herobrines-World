package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.block.entity.custom.SignalAmplifierBlockEntity;
import de.niclasl.herobrines_world.screen.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SignalAmplifierMenu extends AbstractContainerMenu {

    public BlockPos pos;
    public Player player;
    public SignalAmplifierBlockEntity be;

    public SignalAmplifierMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ModMenuTypes.SIGNAL_AMPLIFIER.get(), id);
        BlockPos pos;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.pos = pos;
            this.be = (SignalAmplifierBlockEntity) inv.player.level().getBlockEntity(pos);
        }
        this.player = inv.player;
    }

    public SignalAmplifierMenu(int id, Inventory inv, BlockPos pos) {
        super(ModMenuTypes.SIGNAL_AMPLIFIER.get(), id);
        this.pos = pos;
        this.player = inv.player;
        this.be = (SignalAmplifierBlockEntity) inv.player.level().getBlockEntity(pos);
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