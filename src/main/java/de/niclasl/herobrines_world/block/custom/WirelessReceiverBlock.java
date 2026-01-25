package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WirelessReceiverBlock extends BaseEntityBlock {

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final MapCodec<WirelessReceiverBlock> CODEC =
            simpleCodec(WirelessReceiverBlock::new);

    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(2, 1, 3, 14, 2, 14),
            Block.box(2, 2, 4, 14, 3, 14),
            Block.box(2, 3, 5, 14, 4, 14),
            Block.box(14, 3, 11, 15, 5, 12),
            Block.box(1, 3, 11, 2, 5, 12),
            Block.box(14, 4, 12, 15, 6, 13),
            Block.box(1, 4, 12, 2, 6, 13),
            Block.box(14, 5, 13, 15, 11, 14),
            Block.box(1, 5, 13, 2, 11, 14),
            Block.box(14, 11, 13, 15, 12, 14),
            Block.box(1, 11, 13, 2, 12, 14)
    );

    private static final VoxelShape SHAPE_SOUTH = Shapes.or(
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(2, 1, 2, 14, 2, 13),
            Block.box(2, 2, 2, 14, 3, 12),
            Block.box(2, 3, 2, 14, 4, 11),
            Block.box(1, 3, 4, 2, 5, 5),
            Block.box(14, 3, 4, 15, 5, 5),
            Block.box(1, 4, 3, 2, 6, 4),
            Block.box(14, 4, 3, 15, 6, 4),
            Block.box(1, 5, 2, 2, 11, 3),
            Block.box(14, 5, 2, 15, 11, 3),
            Block.box(1, 11, 2, 2, 12, 3),
            Block.box(14, 11, 2, 15, 12, 3)
    );

    private static final VoxelShape SHAPE_WEST = Shapes.or(
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(3, 1, 2, 14, 2, 14),
            Block.box(4, 2, 2, 14, 3, 14),
            Block.box(5, 3, 2, 14, 4, 14),
            Block.box(11, 3, 1, 12, 5, 2),
            Block.box(11, 3, 14, 12, 5, 15),
            Block.box(12, 4, 1, 13, 6, 2),
            Block.box(12, 4, 14, 13, 6, 15),
            Block.box(13, 5, 1, 14, 11, 2),
            Block.box(13, 5, 14, 14, 11, 15),
            Block.box(13, 11, 1, 14, 12, 2),
            Block.box(13, 11, 14, 14, 12, 15)
    );

    private static final VoxelShape SHAPE_EAST = Shapes.or(
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(2, 1, 2, 13, 2, 14),
            Block.box(2, 2, 2, 12, 3, 14),
            Block.box(2, 3, 2, 11, 4, 14),
            Block.box(4, 3, 14, 5, 5, 15),
            Block.box(4, 3, 1, 5, 5, 2),
            Block.box(3, 4, 14, 4, 6, 15),
            Block.box(3, 4, 1, 4, 6, 2),
            Block.box(2, 5, 14, 3, 11, 15),
            Block.box(2, 5, 1, 3, 11, 2),
            Block.box(2, 11, 14, 3, 12, 15),
            Block.box(2, 11, 1, 3, 12, 2)
    );

    public WirelessReceiverBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(POWERED, false)
                        .setValue(CONNECTED, false)
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state,
                                        @NotNull BlockGetter level,
                                        @NotNull BlockPos pos,
                                        @NotNull CollisionContext context) {

        return switch (state.getValue(FACING)) {
            case SOUTH -> SHAPE_SOUTH;
            case WEST  -> SHAPE_WEST;
            case EAST  -> SHAPE_EAST;
            default    -> SHAPE_NORTH;
        };
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, CONNECTED, FACING);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level,
                                                        @NotNull BlockPos pos, @NotNull Player player,
                                                        @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof WirelessReceiverBlockEntity wirelessReceiverBlockEntity) {
                player.openMenu(new SimpleMenuProvider(wirelessReceiverBlockEntity, Component.translatable("block.herobrines_world.delayer")), pos);
            } else {
                throw new IllegalStateException("Missing container provider!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new WirelessReceiverBlockEntity(pos, state);
    }

    @Override
    protected boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction side) {
        if (!state.getValue(POWERED) || !state.getValue(CONNECTED)) return 0;

        Direction inputSide = state.getValue(FACING);
        if (side == inputSide) return 0;

        return 15;
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, Level level,
                                @NotNull BlockPos pos, @NotNull Block block, Orientation orientation, boolean isMoving) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof WirelessReceiverBlockEntity receiver) {
            receiver.updateRedstone();
        }
        super.neighborChanged(state, level, pos, block, orientation, isMoving);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        if (type == ModBlockEntities.WIRELESS_RECEIVER.get()) {
            return WirelessReceiverBlockEntity::tick;
        }
        return null;
    }
}
