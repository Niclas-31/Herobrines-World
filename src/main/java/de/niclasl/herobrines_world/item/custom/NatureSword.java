package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class NatureSword extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_WOODEN_TOOL,
			2500,
			6f,
			0,
			10,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:nature_sword_repair_items")));

	public NatureSword(Item.Properties properties) {
		super(properties.sword(TOOL_MATERIAL, 5.4f, -2.6f).fireResistant());
	}
}