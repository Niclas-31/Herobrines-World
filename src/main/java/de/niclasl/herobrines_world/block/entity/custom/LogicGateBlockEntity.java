package de.niclasl.herobrines_world.block.entity.custom;

import de.niclasl.herobrines_world.block.custom.LogicGateBlock;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.properties.LogicMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LogicGateBlockEntity extends BlockEntity {

    public LogicGateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LOGIC_GATE_BLOCK.get(), pos, state);
    }

    public LogicMode getMode() {
        return getBlockState().getValue(LogicGateBlock.MODE);
    }
}
