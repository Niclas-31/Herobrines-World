package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.AxeItem;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class HerobrineAxe extends AxeItem {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			7500,
			18f,
			3.8f,
			18,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:herobrine_axe_repair_items")
			)
	);

	public HerobrineAxe(Item.Properties properties) {
		super(TOOL_MATERIAL, 20f, -2.8f, properties.fireResistant());
	}
}