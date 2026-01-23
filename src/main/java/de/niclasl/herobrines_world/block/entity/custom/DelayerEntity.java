package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.custom.Delayer;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.world.inventory.custom.DelayerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class DelayerEntity extends BlockEntity implements MenuProvider {

    private boolean active = false;
    private int remainingTicks = 0;

    private int ticks = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    public DelayerEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DELAYER.get(), pos, state);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putInt("ticks", ticks);
        tag.putInt("seconds", seconds);
        tag.putInt("minutes", minutes);
        tag.putInt("hours", hours);
        tag.putInt("remainingTicks", remainingTicks);
        tag.putBoolean("active", active);
        return tag;
    }

    @Override
    public void handleUpdateTag(@NotNull ValueInput input) {
        super.handleUpdateTag(input);
        this.ticks = input.getIntOr("ticks", 0);
        this.seconds = input.getIntOr("seconds", 0);
        this.minutes = input.getIntOr("minutes", 0);
        this.hours = input.getIntOr("hours", 0);
        this.remainingTicks = input.getIntOr("remaining_ticks", 0);
        this.active = input.getBooleanOr("active", false);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.ticks = input.getIntOr("ticks", 0);
        this.seconds = input.getIntOr("seconds", 0);
        this.minutes = input.getIntOr("minutes", 0);
        this.hours = input.getIntOr("hours", 0);
        this.remainingTicks = input.getIntOr("remaining_ticks", 0);
        this.active = input.getBooleanOr("active", false);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("ticks", ticks);
        output.putInt("seconds", seconds);
        output.putInt("minutes", minutes);
        output.putInt("hours", hours);
        output.putInt("remainingTicks", remainingTicks);
        output.putBoolean("active", active);
    }

    public void activate() {
        active = true;
        remainingTicks = ticks + seconds*20 + minutes*1200 + hours*72000;
        setChanged();
    }

    public boolean isActive() {
        return active;
    }

    public int getOutputSignal() {
        return active ? 15 : 0;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(Delayer.FACING);
        int inputSignal = level.getSignal(pos.relative(facing), facing);

        if (inputSignal > 0) {
            if (!state.getValue(Delayer.POWERED)) {
                level.setBlock(pos, state.setValue(Delayer.POWERED, true), 2);
            }
            return;
        }

        if (remainingTicks > 0) {
            remainingTicks--;
            if (!state.getValue(Delayer.POWERED)) {
                level.setBlock(pos, state.setValue(Delayer.POWERED, true), 2);
            }
        } else {
            if (state.getValue(Delayer.POWERED)) {
                level.setBlock(pos, state.setValue(Delayer.POWERED, false), 2);
            }
            active = false;
            setChanged();
        }
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.herobrines_world.delayer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new DelayerMenu(id, inv, this.getBlockPos());
    }

    public void setTimes(int ticks, int seconds, int minutes, int hours) {
        this.ticks = ticks;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;

        this.remainingTicks = ticks + seconds*20 + minutes*1200 + hours*72000;
        setChanged();
    }

    public int getTicks() {
        return ticks;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public String getFormattedTime() {
        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s ");
        }
        if (ticks > 0) {
            sb.append(ticks).append("t ");
        }

        if (sb.isEmpty()) {
            return "0s";
        }

        return sb.toString().trim();
    }
}