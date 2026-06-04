package de.niclasl.herobrines_world.common.registries.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.common.registries.block.entity.BatteryChargerBlockEntity;
import de.niclasl.herobrines_world.common.registries.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.common.registries.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class BatteryChargerBlock extends BaseEntityBlock {
    public static final MapCodec<BatteryChargerBlock> CODEC = simpleCodec(BatteryChargerBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(getShape());

    @Override
    protected @NotNull MapCodec<BatteryChargerBlock> codec() {
        return CODEC;
    }

    public BatteryChargerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false));
    }

    private static VoxelShape getShape() {
        return Shapes.or(
                Block.box(4, 6, 13, 12, 7, 16),
                Block.box(4, 12, 14, 12, 13, 16),
                Block.box(4, 7, 15, 12, 12, 16),
                Block.box(11, 7, 14, 12, 12, 15),
                Block.box(4, 7, 14, 5, 12, 15),
                Block.box(7, 7, 14, 9, 12, 15)
        );
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                           @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    protected boolean canSurvive(BlockState state, @NonNull LevelReader level, @NonNull BlockPos pos) {
        return canSurvive(level, pos, state.getValue(FACING));
    }

    public static boolean canSurvive(LevelReader level, BlockPos pos, Direction facing) {
        BlockPos blockpos = pos.relative(facing.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy(level, blockpos, facing);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    @Override
    protected @NonNull BlockState updateShape(@NotNull BlockState state, @NonNull LevelReader reader,
                                              @NonNull ScheduledTickAccess tick, @NonNull BlockPos pos,
                                              @NotNull Direction direction, @NonNull BlockPos neighborPos, @NonNull BlockState neighborState,
                                              @NonNull RandomSource randomSource) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(reader, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack,
                                                   @NotNull BlockState state,
                                                   @NotNull Level level,
                                                   @NotNull BlockPos pos,
                                                   @NotNull Player player,
                                                   @NotNull InteractionHand hand,
                                                   @NotNull BlockHitResult hitResult) {

        if (!(level.getBlockEntity(pos) instanceof BatteryChargerBlockEntity batteryCharger)) {
            return InteractionResult.PASS;
        }

        if (player.isCrouching()) {
            if (!level.isClientSide()) {
                player.openMenu(
                        new SimpleMenuProvider(
                                batteryCharger,
                                Component.translatable("block.herobrines_world.battery_charger")
                        ),
                        pos
                );
            }
            return InteractionResult.SUCCESS;
        }

        if (!stack.isEmpty() && canInsert(stack)) {

            int slot = -1;

            if (batteryCharger.items.get(0).isEmpty()) {
                slot = 0;
            } else if (batteryCharger.items.get(1).isEmpty()) {
                slot = 1;
            }

            if (slot != -1) {
                ItemStack inserted = stack.copyWithCount(1);
                batteryCharger.items.set(slot, inserted);
                stack.shrink(1);

                level.playSound(
                        player,
                        pos,
                        SoundEvents.ITEM_PICKUP,
                        SoundSource.BLOCKS,
                        1f,
                        2f
                );

                return InteractionResult.SUCCESS;
            }
        }

        if (stack.isEmpty()) {

            int slot = -1;

            if (!batteryCharger.items.get(1).isEmpty()) {
                slot = 1;
            } else if (!batteryCharger.items.get(0).isEmpty()) {
                slot = 0;
            }

            if (slot != -1) {
                ItemStack taken = batteryCharger.items.get(slot);

                player.setItemInHand(hand, taken);
                batteryCharger.items.set(slot, ItemStack.EMPTY);

                level.playSound(
                        player,
                        pos,
                        SoundEvents.ITEM_PICKUP,
                        SoundSource.BLOCKS,
                        1f,
                        1f
                );

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private boolean canInsert(ItemStack stack) {
        return stack.is(ModItems.BATTERY.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING);
    }

    @Override
    protected void neighborChanged(BlockState state,
                                   Level level,
                                   BlockPos pos,
                                   @NonNull Block neighborBlock,
                                   @Nullable Orientation orientation,
                                   boolean movedByPiston) {

        Direction facing = state.getValue(FACING);

        BlockPos supportPos = pos.relative(facing.getOpposite());
        BlockState supportState = level.getBlockState(supportPos);

        if (!supportState.isFaceSturdy(
                level,
                supportPos,
                facing.getOpposite())) {

            level.destroyBlock(pos, true);
            return;
        }

        if (level.isClientSide()) return;

        boolean powered = level.getSignal(supportPos, facing) > 0;

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