package de.niclasl.herobrines_world.registries.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.registries.block.entity.custom.DelayerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DelayerBlock extends DiodeBlock implements EntityBlock {

    public static final MapCodec<DelayerBlock> CODEC = simpleCodec(DelayerBlock::new);

    public DelayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false));
    }

    @Override
    protected @NotNull MapCodec<? extends DiodeBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DelayerBlockEntity(pos, state);
    }

    @Override
    protected int getDelay(@NotNull BlockState state) {
        return 2;
    }

    @Override
    protected boolean shouldTurnOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return getInputSignal(level, pos, state) > 0;
    }

    @Override
    protected int getInputSignal(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        Direction facing = state.getValue(FACING);
        BlockPos inputPos = pos.relative(facing);
        return level.getSignal(inputPos, facing);
    }

    @Override
    protected void checkTickOnNeighbor(Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof DelayerBlockEntity delayer)) return;

        if (shouldTurnOn(level, pos, state) && !delayer.isActive()) {
            delayer.activate();
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level world, @NotNull BlockPos pos,
                                                   @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!world.isClientSide()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof DelayerBlockEntity delayerEntity) {
                player.openMenu(new SimpleMenuProvider(delayerEntity, Component.translatable("block.herobrines_world.delayer")), pos);
            } else {
                throw new IllegalStateException("Missing container provider!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected int getOutputSignal(@NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof DelayerBlockEntity delayer) {
            return delayer.getOutputSignal();
        }
        return 0;
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos neighborPos, @NotNull BlockState neighborState, @NotNull RandomSource random) {
        if (direction == Direction.DOWN && !this.canSurviveOn(level, neighborPos, neighborState)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, bs, be) -> {
            if (be instanceof DelayerBlockEntity delayer) delayer.tick(lvl, pos, bs);
        };
    }
}