package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class NaturePickaxe extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_WOODEN_TOOL,
			2500,
			6f,
			0,
			14,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:nature_pickaxe_repair_items")));

	public NaturePickaxe(Item.Properties properties) {
		super(properties.pickaxe(
				TOOL_MATERIAL,
				1.4f,
				-3.0f
		).fireResistant());
	}
}