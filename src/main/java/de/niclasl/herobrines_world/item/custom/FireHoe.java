package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.HoeItem;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class FireHoe extends HoeItem {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_STONE_TOOL,
			5000,
			8f,
			0,
			16,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:fire_hoe_repair_items")
			)
	);

	public FireHoe(Item.Properties properties) {
		super(TOOL_MATERIAL, 5.6f, -2.8f, properties.fireResistant());
	}
}