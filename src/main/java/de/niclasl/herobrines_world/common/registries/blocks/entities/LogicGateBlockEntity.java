package de.niclasl.herobrines_world.common.registries.blocks.entities;

import de.niclasl.herobrines_world.common.registries.blocks.custom.LogicGateBlock;
import de.niclasl.herobrines_world.common.registries.blocks.properties.LogicMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LogicGateBlockEntity extends BlockEntity {

    private int currentSignal = 0;

    public LogicGateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LOGIC_GATE_BLOCK.get(), pos, state);
    }

    public LogicMode getMode() {
        return getBlockState().getValue(LogicGateBlock.MODE);
    }

    public int getCurrentSignal() {
        return currentSignal;
    }

    public void setCurrentSignal(int signal) {
        this.currentSignal = signal;
        setChanged();
    }
}