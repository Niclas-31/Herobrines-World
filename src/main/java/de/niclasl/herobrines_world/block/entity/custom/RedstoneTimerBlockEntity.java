package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.screen.custom.RedstoneTimerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedstoneTimerBlockEntity extends BlockEntity implements MenuProvider {

    private int interval = 20;
    private int counter = 0;

    public RedstoneTimerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_TIMER.get(), pos, state);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.interval = input.getIntOr("interval", 20);
        this.counter = input.getIntOr("counter", 0);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("interval", this.interval);
        output.putInt("counter", this.counter);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, RedstoneTimerBlockEntity blockEntity) {
        if(level.isClientSide()) return;

        blockEntity.counter++;

        if(blockEntity.counter >= blockEntity.interval) {

            blockEntity.counter = 0;

            boolean powered = state.getValue(BlockStateProperties.POWERED);

            level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, !powered), 3);

            level.updateNeighborsAt(pos, state.getBlock());
        }
    }

    public void setInterval(int interval) {
        this.interval = interval;
        setChanged();

        if(level != null)
            level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
    }

    public int getInterval() {
        return interval;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.herobrines_world.redstone_timer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new RedstoneTimerMenu(id, inventory, this.getBlockPos());
    }
}