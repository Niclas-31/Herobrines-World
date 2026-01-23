package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class HerobrineSword extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			7500,
			10f,
			0,
			18,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:herobrine_sword_repair_items")
			)
	);

	public HerobrineSword(Item.Properties properties) {
		super(properties.sword(
				TOOL_MATERIAL,
				5.8f,
				-2.2f
		).fireResistant());
	}
}