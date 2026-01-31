package de.niclasl.herobrines_world.block.custom;

import de.niclasl.herobrines_world.block.entity.custom.LogicGateBlockEntity;
import de.niclasl.herobrines_world.block.properties.LogicMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LogicGateBlock extends Block implements EntityBlock {

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    public static final EnumProperty<Direction> FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty POWERED =
            BlockStateProperties.POWERED;

    public static final EnumProperty<LogicMode> MODE =
            EnumProperty.create("mode", LogicMode.class);

    public LogicGateBlock(BlockBehaviour.Properties props) {
        super(props.instabreak());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(POWERED, false)
                .setValue(MODE, LogicMode.AND);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state,
                                                   @NotNull Level level, @NotNull BlockPos pos,
                                                   @NotNull Player player, @NotNull InteractionHand hand,
                                                   @NotNull BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        LogicMode next = switch (state.getValue(MODE)) {
            case AND -> LogicMode.OR;
            case OR  -> LogicMode.NOT;
            case NOT -> LogicMode.XOR;
            case XOR -> LogicMode.AND;
        };

        level.setBlock(pos, state.setValue(MODE, next), 3);

        return InteractionResult.CONSUME;
    }

    @Override
    protected void neighborChanged(@NotNull BlockState state, @NotNull Level level,
                                   @NotNull BlockPos pos, @NotNull Block block,
                                   @Nullable Orientation orientation, boolean isMoving) {

        Direction facing = state.getValue(FACING);
        Direction left  = facing.getCounterClockWise();
        Direction right = facing.getClockWise();

        int leftSignal  = level.getSignal(pos.relative(left), left);
        int rightSignal = level.getSignal(pos.relative(right), right);

        boolean powered = evaluate(
                state.getValue(MODE), leftSignal, rightSignal
        );

        if (powered != state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, powered), 3);
        }
    }

    private boolean evaluate(
            LogicMode mode, int left, int right) {

        return switch (mode) {
            case AND -> left > 0 && right > 0;
            case OR  -> left > 0 || right > 0;
            case NOT -> right > 0 && left == 0;
            case XOR -> (left > 0) ^ (right > 0);
        };
    }

    @Override
    public int getSignal(BlockState state, @NotNull BlockGetter level,
                         @NotNull BlockPos pos, @NotNull Direction direction) {

        return direction == state.getValue(FACING)
                && state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, MODE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new LogicGateBlockEntity(blockPos, blockState);
    }
}
