package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.custom.WirelessReceiverBlock;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.network.data.VisibleNetwork;
import de.niclasl.herobrines_world.network.data.WirelessSenderData;
import de.niclasl.herobrines_world.network.manager.WirelessNetworkManager;
import de.niclasl.herobrines_world.world.inventory.custom.WirelessReceiverMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WirelessReceiverBlockEntity extends BlockEntity
        implements MenuProvider {

    private List<VisibleNetwork> visibleNetworks = List.of();
    private boolean connected = false;
    private boolean powered = false;
    private BlockPos connectedSenderPos;

    public WirelessReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIRELESS_RECEIVER.get(), pos, state);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.connected = input.getBooleanOr("connected", false);
        this.powered = input.getBooleanOr("powered", false);
        input.getLong("sender").ifPresent(value -> connectedSenderPos = BlockPos.of(value));
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("connected", this.connected);
        output.putBoolean("powered", this.powered);
        if (connectedSenderPos != null) {
            output.putLong("sender", connectedSenderPos.asLong());
        }
    }

    public void updateVisibleNetworks() {
        visibleNetworks = WirelessNetworkManager
                .getAllSenders()
                .stream()
                .filter(sender -> sender.pos().distManhattan(this.worldPosition) <= sender.getRange())
                .map(s -> new VisibleNetwork(
                        s.pos(),
                        s.name(),
                        s.hasPassword(),
                        Math.max(1, 15 - s.pos().distManhattan(worldPosition)),
                        this
                ))
                .toList();

        setChanged();
    }

    public void updateRedstone() {
        if (level == null || level.isClientSide()) return;

        Direction facing = getBlockState().getValue(WirelessReceiverBlock.FACING);

        Direction inputSide = facing.getOpposite();

        boolean nowPowered = level.getSignal(worldPosition.relative(inputSide), inputSide) > 0;

        if (nowPowered != powered) {
            setPowered(nowPowered);
        }
    }

    public List<VisibleNetwork> getVisibleNetworks() {
        return visibleNetworks;
    }

    public boolean isConnected() {
        return getConnectedSender() != null && connected;
    }

    public void setConnected(boolean connected, WirelessSenderData sender, String password) {
        if (sender.hasPassword() && !sender.password().equals(password)) {
            return;
        }

        this.connectedSenderPos = sender.pos();
        this.connected = connected;
        updateBlockState();
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
        updateBlockState();
    }

    public boolean isPowered() {
        return powered;
    }

    private void updateBlockState() {
        if (level != null) {
            level.setBlock(
                    worldPosition,
                    getBlockState()
                            .setValue(WirelessReceiverBlock.CONNECTED, connected)
                            .setValue(WirelessReceiverBlock.POWERED, powered),
                    Block.UPDATE_ALL
            );
        }
        setChanged();
    }

    public WirelessSenderData getConnectedSender() {
        if (connectedSenderPos == null) return null;
        return WirelessNetworkManager.getSender(connectedSenderPos);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.herobrines_world.wireless_receiver_block");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new WirelessReceiverMenu(id, inv, this);
    }

    public static <T extends BlockEntity> void tick(Level level,
                                                    BlockPos pos,
                                                    BlockState state,
                                                    T t) {
        if (t instanceof WirelessReceiverBlockEntity receiver) {
            if (level.isClientSide()) return;
            if (level.getGameTime() % 20 != 0) return;
            receiver.updateVisibleNetworks();
        }
    }

    public void connectToNetwork(boolean connected) {
        if (level != null) {
            level.setBlock(
                    worldPosition,
                    getBlockState().setValue(WirelessReceiverBlock.CONNECTED, connected),
                    Block.UPDATE_ALL
            );
        }
        setChanged();
    }
}
