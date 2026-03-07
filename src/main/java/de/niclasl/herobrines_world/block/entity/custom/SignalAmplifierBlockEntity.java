package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.screen.custom.SignalAmplifierMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignalAmplifierBlockEntity extends BlockEntity implements MenuProvider {

    private int signalStrength;

    public SignalAmplifierBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SIGNAL_AMPLIFIER.get(), pos, blockState);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putInt("signal_strange", signalStrength);
        return tag;
    }

    @Override
    public void handleUpdateTag(@NotNull ValueInput input) {
        super.handleUpdateTag(input);
        this.signalStrength = input.getIntOr("signal_strange", 0);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.signalStrength = input.getIntOr("signal_strange", 0);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("signal_strange", signalStrength);
    }

    public void setSignalStrange(int signalStrange) {
        this.signalStrength = signalStrange;
        setChanged();

        if (level != null) {
            level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
        }
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.herobrines_world.signal_amplifier");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new SignalAmplifierMenu(id, inventory, this.getBlockPos());
    }
}