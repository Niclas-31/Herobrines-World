package de.niclasl.herobrines_world.block.custom;

import de.niclasl.herobrines_world.block.entity.custom.RedEnchantmentTableBlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedEnchantmentTable extends Block implements EntityBlock {

	public RedEnchantmentTable(BlockBehaviour.Properties properties) {
		super(properties.strength(5f, 1200f).lightLevel(s -> 7).requiresCorrectToolForDrops().noOcclusion().pushReaction(PushReaction.BLOCK).isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new RedEnchantmentTableBlockEntity(pos, state);
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState state) {
		return true;
	}

	@Override
	public int getLightBlock(@NotNull BlockState state) {
		return 0;
	}

	@Override
	public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return box(0, 0, 0, 16, 12, 16);
	}

	@Override
	protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level world, @NotNull BlockPos pos,
												   @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (!world.isClientSide()) {
			BlockEntity entity = world.getBlockEntity(pos);
			if (entity instanceof RedEnchantmentTableBlockEntity redEnchantmentTableBlockEntity) {
				player.openMenu(new SimpleMenuProvider(redEnchantmentTableBlockEntity, Component.translatable("block.herobrines_world.red_enchantment_table")), pos);
			} else {
				throw new IllegalStateException("Missing container provider!");
			}
		}
		return InteractionResult.SUCCESS;
	}
}
