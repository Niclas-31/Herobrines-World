package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignalAmplifierBlock extends HorizontalDirectionalBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final MapCodec<SignalAmplifierBlock> CODEC = simpleCodec(SignalAmplifierBlock::new);

    @Override
    protected @NotNull MapCodec<SignalAmplifierBlock> codec() {
        return CODEC;
    }

    public SignalAmplifierBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POWERED, false)
        );
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, @NotNull BlockGetter level,
                         @NotNull BlockPos pos, @NotNull Direction direction) {

        if (!state.getValue(POWERED)) return 0;

        Direction facing = state.getValue(FACING);

        return direction == facing ? 15 : 0;
    }

    @Override
    protected void neighborChanged(@NotNull BlockState state,
                                   Level level,
                                   @NotNull BlockPos pos,
                                   @NotNull Block neighborBlock,
                                   @Nullable Orientation orientation,
                                   boolean movedByPiston) {

        if (level.isClientSide()) return;

        Direction facing = state.getValue(FACING);
        BlockPos inputPos = pos.relative(facing.getOpposite());

        int power = level.getSignal(inputPos, facing);

        boolean powered = power > 0;

        if (powered != state.getValue(POWERED)) {
            level.setBlock(pos,
                    state.setValue(POWERED, powered),
                    3);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING,
                        context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }
}