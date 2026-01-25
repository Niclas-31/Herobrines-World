package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.network.data.WirelessSenderData;
import de.niclasl.herobrines_world.network.manager.WirelessNetworkManager;
import de.niclasl.herobrines_world.world.inventory.custom.WirelessSenderMenu;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WirelessSenderBlockEntity extends BlockEntity implements MenuProvider {

    private boolean powered = false;
    private String networkName = "Wireless";
    private String password = "";
    private int range = 16;

    public WirelessSenderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIRELESS_SENDER.get(), pos, state);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putString("networkName", networkName);
        tag.putString("password", password);
        tag.putInt("range", range);
        return tag;
    }

    @Override
    public void handleUpdateTag(@NotNull ValueInput input) {
        super.handleUpdateTag(input);
        this.networkName = input.getStringOr("networkName", "Wireless");
        this.password = input.getStringOr("password", "");
        this.range = input.getIntOr("range", 16);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (powered) {
            WirelessNetworkManager.registerSender(worldPosition, new WirelessSenderData(worldPosition, networkName, password, range));
        }
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.powered = input.getBooleanOr("powered", false);
        this.networkName = input.getStringOr("networkName", "Wireless");
        this.password = input.getStringOr("password", "");
        this.range = input.getIntOr("range", 16);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("powered", this.powered);
        output.putString("networkName", this.networkName);
        output.putString("password", this.password);
        output.putInt("range", this.range);
    }

    public void setNetwork(String networkName, String password, int range) {
        this.networkName = networkName;
        this.password = password;
        this.range = range;
        setChanged();
    }

    public boolean isPowered() {
        return this.powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public int getRange() {
        return this.range;
    }

    public String getNetworkName() {
        return this.networkName;
    }

    public String getPassword() {
        return this.password;
    }

    public void updateRedstone(Level level) {
        if (level.isClientSide()) return;

        boolean nowPowered = level.getBestNeighborSignal(worldPosition) > 0;
        if (nowPowered != powered) {
            powered = nowPowered;

            if (powered) {
                WirelessNetworkManager.registerSender(
                        worldPosition,
                        new WirelessSenderData(worldPosition, networkName, password, range)
                );
            } else {
                WirelessNetworkManager.removeSender(worldPosition);
            }

            setChanged();
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.herobrines_world.wireless_sender_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new WirelessSenderMenu(id, this);
    }
}
