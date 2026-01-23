package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.AxeItem;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class FireAxe extends AxeItem {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_STONE_TOOL,
			5000,
			16f,
			3.6f,
			16,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:fire_axe_repair_items")
			)
	);

	public FireAxe(Item.Properties properties) {
		super(TOOL_MATERIAL, 18f, -3.0f, properties.fireResistant());
	}
}