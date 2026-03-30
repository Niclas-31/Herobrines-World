package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.entity.custom.BatteryChargerBlockEntity;
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
import net.minecraft.world.level.block.RenderShape;
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
import org.jetbrains.annotations.Nullable;

public class BatteryChargerBlock extends BaseEntityBlock {

    public static final MapCodec<BatteryChargerBlock> CODEC = simpleCodec(BatteryChargerBlock::new);

    public static final VoxelShape SOUTH_SHAPE = Shapes.or(
            Block.box(3, 0, 9, 13, 1, 16),
            Block.box(3, 9, 10, 13, 10, 16),
            Block.box(3, 1, 15, 13, 9, 16),
            Block.box(3, 10, 11, 13, 11, 16)
    );

    public static final VoxelShape NORTH_SHAPE = Shapes.or(
            Block.box(3, 0, 0, 13, 1, 7),
            Block.box(3, 9, 0, 13, 10, 6),
            Block.box(3, 1, 0, 13, 9, 1),
            Block.box(3, 10, 0, 13, 11, 5)
    );

    public static final VoxelShape WEST_SHAPE = Shapes.or(
            Block.box(0, 0, 3, 7, 1, 13),
            Block.box(0, 9, 3, 6, 10, 13),
            Block.box(0, 1, 3, 1, 9, 13),
            Block.box(0, 10, 3, 5, 11, 13)
    );

    public static final VoxelShape EAST_SHAPE = Shapes.or(
            Block.box(9, 0, 3, 16, 1, 13),
            Block.box(10, 9, 3, 16, 10, 13),
            Block.box(15, 1, 3, 16, 9, 13),
            Block.box(11, 10, 3, 16, 11, 13)
    );

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BatteryChargerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false));
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(POWERED, false);
    }

    @Override
    protected @NotNull MapCodec<BatteryChargerBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hitResult) {

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof BatteryChargerBlockEntity batteryChargerBlockEntity) {
                player.openMenu(new SimpleMenuProvider(batteryChargerBlockEntity, Component.translatable("block.herobrines_world.battery_charger")), pos);
            } else {
                throw new IllegalStateException("Missing container provider!");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state,
                                           @NotNull BlockGetter level,
                                           @NotNull BlockPos pos,
                                           @NotNull CollisionContext context) {

        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST  -> EAST_SHAPE;
            case WEST  -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING);
    }

    @Override
    protected void neighborChanged(@NotNull BlockState state, @NotNull Level level,
                                   @NotNull BlockPos pos, @NotNull Block neighborBlock,
                                   @Nullable Orientation orientation, boolean movedByPiston) {
        if (level.isClientSide()) return;

        Direction facing = state.getValue(FACING);
        BlockPos backPos = pos.relative(facing);

        boolean powered = level.getSignal(backPos, facing) > 0;

        if (powered != state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_ALL);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new BatteryChargerBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == ModBlockEntities.BATTERY_CHARGER.get()
                ? (lvl, pos, st, be) -> BatteryChargerBlockEntity.tick(state, (BatteryChargerBlockEntity) be)
                : null;
    }
}
