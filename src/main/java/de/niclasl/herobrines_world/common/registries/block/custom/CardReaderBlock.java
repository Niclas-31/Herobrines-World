package de.niclasl.herobrines_world.common.registries.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.common.registries.block.entity.CardReaderBlockEntity;
import de.niclasl.herobrines_world.common.registries.item.custom.KeyCard;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CardReaderBlock extends BaseEntityBlock {
    public static final MapCodec<CardReaderBlock> CODEC = simpleCodec(CardReaderBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ACCESS_GRANTED = BooleanProperty.create("access_granted");

    public CardReaderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ACCESS_GRANTED, false));
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(ACCESS_GRANTED, false);
    }

    @Override
    public boolean isSignalSource(@NonNull BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return state.getValue(ACCESS_GRANTED) ? 15 : 0;
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level level,
                                                   @NonNull BlockPos pos, @NonNull Player player,
                                                   @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos)
                instanceof CardReaderBlockEntity be)) {
            return InteractionResult.PASS;
        }

        if (!player.isCrouching()) {
            if (!(stack.getItem() instanceof KeyCard)) return InteractionResult.PASS;

            boolean access = be.canAccess(player, stack);

            be.accessTicks = 40;

            if (!level.isClientSide()) {

                if (access) {
                    level.setBlock(
                            pos,
                            state.setValue(ACCESS_GRANTED, true),
                            3
                    );

                    player.displayClientMessage(
                            Component.literal("Access Granted"),
                            true
                    );
                } else {
                    player.displayClientMessage(
                            Component.literal("Access Denied"),
                            true
                    );
                }
            }

            return InteractionResult.SUCCESS;
        } else {
            if (!level.isClientSide()) {
                BlockEntity entity = level.getBlockEntity(pos);
                if (entity instanceof CardReaderBlockEntity card) {
                    player.openMenu(new SimpleMenuProvider(card, Component.translatable("block.herobrines_world.card_reader")), pos);
                } else {
                    throw new IllegalStateException("Missing container provider!");
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, Level level, @NonNull BlockPos pos,
                                                        @NonNull Player player, @NonNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof CardReaderBlockEntity card) {
                player.openMenu(new SimpleMenuProvider(card, Component.translatable("block.herobrines_world.card_reader")), pos);
            } else {
                throw new IllegalStateException("Missing container provider!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            @NonNull BlockState state,
            @NonNull BlockEntityType<T> type
    ) {
        if (level.isClientSide()) return null;
        return (lvl, pos, bs, be) -> {
            if (be instanceof CardReaderBlockEntity card) {
                card.tick(level, card);
            }
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACCESS_GRANTED);
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new CardReaderBlockEntity(blockPos, blockState);
    }
}