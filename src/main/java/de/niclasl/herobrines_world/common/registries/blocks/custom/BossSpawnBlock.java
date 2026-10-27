package de.niclasl.herobrines_world.common.registries.blocks.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.common.registries.blocks.entities.BossSpawnBlockEntity;
import de.niclasl.herobrines_world.common.registries.items.custom.BossSpawnItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BossSpawnBlock extends BaseEntityBlock {
    public static final MapCodec<BossSpawnBlock> CODEC = simpleCodec(BossSpawnBlock::new);

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(3, 1, 3, 13, 2, 13),
            Block.box(4, 2, 4, 12, 3, 12),
            Block.box(5, 3, 5, 11, 10, 11),
            Block.box(4, 10, 4, 12, 11, 12),
            Block.box(3, 11, 3, 13, 12, 13),
            Block.box(2, 12, 2, 4, 13, 14),
            Block.box(1, 13, 2, 3, 14, 14),
            Block.box(12, 12, 2, 14, 13, 14),
            Block.box(13, 13, 2, 15, 14, 14),
            Block.box(4, 12, 2, 12, 13, 4),
            Block.box(3, 13, 1, 13, 14, 3),
            Block.box(4, 12, 12, 12, 13, 14),
            Block.box(3, 13, 13, 13, 14, 15),
            Block.box(2, 13, 1, 3, 14, 2),
            Block.box(13, 13, 1, 14, 14, 2),
            Block.box(13, 13, 14, 14, 14, 15),
            Block.box(2, 13, 14, 3, 14, 15)
    );

    public BossSpawnBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level,
                                                   @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand,
                                                   @NonNull BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.FAIL;
        }

        if (!(stack.getItem() instanceof BossSpawnItem)) {
            return InteractionResult.FAIL;
        }

        if (!(level.getBlockEntity(pos) instanceof BossSpawnBlockEntity spawnBlock)) {
            return InteractionResult.PASS;
        }

        if (!stack.isEmpty() && canInsert(stack)) {
            if (spawnBlock.items.getFirst().isEmpty()) {
                ItemStack inserted = stack.copyWithCount(1);
                spawnBlock.items.set(0, inserted);
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

        return InteractionResult.SUCCESS;
    }

    private boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof BossSpawnItem;
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos,
                                           @NonNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new BossSpawnBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, _, _, be) -> {
            if (be instanceof BossSpawnBlockEntity spawner) spawner.tick(lvl);
        };
    }
}