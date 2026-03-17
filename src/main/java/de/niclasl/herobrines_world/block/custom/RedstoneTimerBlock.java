package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.entity.custom.RedstoneTimerBlockEntity;
import de.niclasl.herobrines_world.screen.custom.RedstoneTimerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedstoneTimerBlock extends BaseEntityBlock {

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<RedstoneTimerBlock> CODEC = simpleCodec(RedstoneTimerBlock::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public RedstoneTimerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POWERED, false));
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

        if(direction == facing.getOpposite()) {
            return 15;
        }

        return 0;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player entity, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player) {
            player.openMenu(new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.translatable("block.herobrines_world.redstone_timer");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
                    return new RedstoneTimerMenu(id, inventory, pos);
                }
            }, pos);
        }
        return InteractionResult.SUCCESS;
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
    protected void neighborChanged(@NotNull BlockState state,
                                   Level level,
                                   @NotNull BlockPos pos,
                                   @NotNull Block neighborBlock,
                                   @Nullable Orientation orientation,
                                   boolean movedByPiston) {

        if (level.isClientSide()) return;

        Direction facing = state.getValue(FACING);
        BlockPos inputPos = pos.relative(facing.getOpposite());

        boolean powered = level.getSignal(inputPos, facing) > 0;
        boolean oldPowered = state.getValue(POWERED);

        if (powered != oldPowered) {
            level.setBlock(pos, state.setValue(POWERED, powered), 2);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new RedstoneTimerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level,
                                                                            @NotNull BlockState state,
                                                                            @NotNull BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                ModBlockEntities.REDSTONE_TIMER.get(),
                RedstoneTimerBlockEntity::tick
        );
    }
}