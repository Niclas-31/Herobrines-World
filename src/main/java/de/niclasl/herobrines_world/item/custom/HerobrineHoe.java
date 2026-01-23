package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.HoeItem;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class HerobrineHoe extends HoeItem {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			7500,
			10f,
			0,
			18,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:herobrine_hoe_repair_items")
			)
	);

	public HerobrineHoe(Item.Properties properties) {
		super(TOOL_MATERIAL, 5.8f, -2.6f, properties.fireResistant());
	}
}