package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.AxeItem;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class NatureAxe extends AxeItem {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_WOODEN_TOOL,
			2500,
			14f,
			3.4f,
			14,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:nature_axe_repair_items")
			)
	);

	public NatureAxe(Item.Properties properties) {
		super(TOOL_MATERIAL, 16f, -3.2f, properties.fireResistant());
	}
}