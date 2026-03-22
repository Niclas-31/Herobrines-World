package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.entity.custom.AutoFarmerBlockEntity;
import de.niclasl.herobrines_world.block.properties.FarmerMode;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoFarmerBlock extends BaseEntityBlock {

    public static final EnumProperty<FarmerMode> FARMER_MODE =
            EnumProperty.create("farmer_mode", FarmerMode.class);

    public static final MapCodec<AutoFarmerBlock> CODEC = simpleCodec(AutoFarmerBlock::new);

    public AutoFarmerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FARMER_MODE, FarmerMode.BREAKER));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hitResult) {

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof AutoFarmerBlockEntity) {

                if (player.isShiftKeyDown()) {
                    FarmerMode current = state.getValue(FARMER_MODE);

                    FarmerMode next = switch (current) {
                        case BREAKER -> FarmerMode.PLACER;
                        case PLACER -> FarmerMode.BOTH;
                        case BOTH -> FarmerMode.BREAKER;
                    };

                    level.setBlock(pos, state.setValue(FARMER_MODE, next), 3);

                    player.displayClientMessage(
                            Component.literal("Mode: " + next.name()),
                            true
                    );
                } else {
                    BlockEntity entity = level.getBlockEntity(pos);
                    if (entity instanceof AutoFarmerBlockEntity autoFarmerBlockEntity) {
                        player.openMenu(new SimpleMenuProvider(autoFarmerBlockEntity, Component.translatable("block.herobrines_world.auto_farmer")), pos);
                    } else {
                        throw new IllegalStateException("Missing container provider!");
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AutoFarmerBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FARMER_MODE);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == ModBlockEntities.AUTO_FARMER.get()
                ? (lvl, pos, st, be) -> AutoFarmerBlockEntity.tick(lvl, pos, st, (AutoFarmerBlockEntity) be)
                : null;
    }
}