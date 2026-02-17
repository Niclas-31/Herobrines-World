package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.procedures.AshPickaxeBlockDestroyedWithTool;
import org.jetbrains.annotations.NotNull;

public class AshPickaxe extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 1500, 9f, 0, 15, TagKey.create(Registries.ITEM, ResourceLocation.parse("herobrines_world:ash_pickaxe_repair_items")));

	public AshPickaxe(Item.Properties properties) {
		super(properties.pickaxe(TOOL_MATERIAL, 3f, -3f).fireResistant());
	}

	@Override
	public boolean mineBlock(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull BlockState blockstate, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
		boolean retval = super.mineBlock(itemstack, world, blockstate, pos, entity);

		if ((world.getBlockState(BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()))).getBlock() == ModBlocks.ASH_ORE.get() || (world.getBlockState(BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()))).getBlock() == ModBlocks.DEEPSLATE_ASH_ORE.get()) {
			world.setBlock(BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.ASH_INGOT.get()));
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
		}
		return retval;
	}
}
