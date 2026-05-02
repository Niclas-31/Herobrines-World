package de.niclasl.herobrines_world.registries.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.registries.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.registries.block.entity.custom.BatteryChargerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import org.jspecify.annotations.NonNull;

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
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state,
                                                   @NonNull Level level, @NonNull BlockPos pos,
                                                   @NonNull Player player, @NonNull InteractionHand hand,
                                                   @NonNull BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof BatteryChargerBlockEntity batteryCharger) {
            if(player.isCrouching() && !level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider(batteryCharger, Component.translatable("block.herobrines_world.battery_charger")), pos);
                return InteractionResult.SUCCESS;
            } else if(!player.isCrouching() && batteryCharger.items.getFirst().isEmpty() && !stack.isEmpty()) {
                batteryCharger.items.set(0, stack.copy());
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if(!player.isCrouching() && stack.isEmpty()) {
                ItemStack stackOnBatteryCharger = batteryCharger.items.getFirst();
                player.setItemInHand(InteractionHand.MAIN_HAND, stackOnBatteryCharger);
                batteryCharger.items.set(0, ItemStack.EMPTY);
                batteryCharger.clearContent();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
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
