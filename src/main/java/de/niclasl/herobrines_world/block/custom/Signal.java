package de.niclasl.herobrines_world.block.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.block.ModBlocks;
import de.niclasl.herobrines_world.world.inventory.custom.SignalColorChanger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.ToIntFunction;

public class Signal extends Block {

	public static final MapCodec<Signal> CODEC = simpleCodec(Signal::new);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final EnumProperty<ColorProperty> COLOR = EnumProperty.create("color", ColorProperty.class);

	@Override
	protected @NotNull MapCodec<Signal> codec() {
		return CODEC;
	}

	public Signal(BlockBehaviour.Properties properties) {
		super(properties
				.mapColor(MapColor.TERRACOTTA_ORANGE)
				.lightLevel(litBlockEmission())
				.strength(0.3F)
				.sound(SoundType.GLASS)
				.isValidSpawn((blockState, blockGetter, blockPos, entityType) -> ModBlocks.always()));
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(COLOR, ColorProperty.RED));
	}

	private static ToIntFunction<BlockState> litBlockEmission() {
		return p_50763_ -> p_50763_.getValue(BlockStateProperties.LIT) ? 15 : 0;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		return this.defaultBlockState().setValue(LIT, context.getLevel().hasNeighborSignal(context.getClickedPos())).setValue(COLOR, ColorProperty.RED);
	}

	@Override
	protected void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
								   @NotNull Block block, @Nullable Orientation orientation, boolean flag) {
		if (!level.isClientSide()) {
			boolean flag1 = state.getValue(LIT);
			if (flag1 != level.hasNeighborSignal(pos)) {
				if (flag1) {
					level.scheduleTick(pos, this, 4);
				} else {
					level.setBlock(pos, state.cycle(LIT), 2);
				}
			}
		}
	}

	@Override
	protected void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos,
						@NotNull RandomSource random) {
		if (state.getValue(LIT) && !level.hasNeighborSignal(pos)) {
			level.setBlock(pos, state.cycle(LIT), 2);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(LIT, COLOR);
	}

	@Override
	public @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player entity, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (!world.isClientSide() && entity instanceof ServerPlayer player) {
			player.openMenu(new MenuProvider() {
				@Override
				public @NotNull Component getDisplayName() {
					return Component.translatable("block.herobrines_world.signal");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
					return new SignalColorChanger(id, inventory, pos);
				}
			}, pos);
		}
		return InteractionResult.SUCCESS;
	}

	public static ItemStack setModeOnStack(ItemStack stack, ColorProperty color) {
		stack.set(DataComponents.BLOCK_STATE, stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).with(COLOR, color));
		return stack;
	}

	@Override
	public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean includeData, @NotNull Player player) {
		ItemStack itemstack = super.getCloneItemStack(level, pos, state, includeData, player);
		return setModeOnStack(itemstack, state.getValue(COLOR));
	}

	public enum ColorProperty implements StringRepresentable {
		RED("red"), ORANGE("orange"), YELLOW("yellow"), LIME("lime"),
		GREEN("green"), CYAN("cyan"), BLUE("blue"), MAGENTA("magenta"),
		PINK("pink"), GRAY("gray"), LIGHT_GRAY("light_gray"), LIGHT_BLUE("light_blue");

		private final String name;
		ColorProperty( String name) {
			this.name = name;
		}

		@Override
		public @NotNull String getSerializedName() {
			return this.name;
		}
	}
}
