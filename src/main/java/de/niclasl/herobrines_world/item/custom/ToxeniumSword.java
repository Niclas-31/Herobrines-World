package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.procedures.ToxeniumItemInInventoryTick;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ToxeniumSword extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
			10000,
			12f,
			0,
			20,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:toxenium_sword_repair_items")));

	public ToxeniumSword(Item.Properties properties) {
		super(properties.sword(TOOL_MATERIAL, 6f, -2f).fireResistant());
	}

	@Override
	public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		ToxeniumItemInInventoryTick.execute(entity);
	}
}