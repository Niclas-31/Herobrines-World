package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.block.entity.custom.DelayerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

public class Delayer extends DiodeBlock implements EntityBlock {

    public static final MapCodec<Delayer> CODEC = simpleCodec(Delayer::new);

    public Delayer(Properties properties) {
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
        return new DelayerEntity(pos, state);
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
        if (!(be instanceof DelayerEntity delayer)) return;

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
            if (entity instanceof DelayerEntity delayerEntity) {
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
        if (be instanceof DelayerEntity delayer) {
            return delayer.getOutputSignal();
        }
        return 0;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, bs, be) -> {
            if (be instanceof DelayerEntity delayer) delayer.tick(lvl, pos, bs);
        };
    }
}