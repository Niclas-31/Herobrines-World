package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class NatureShovel extends ShovelItem {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_WOODEN_TOOL,
			2500,
			6f,
			0f,
			14,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:nature_shovel_repair_items")));

	public NatureShovel(Item.Properties properties) {
		super(TOOL_MATERIAL, 1.4f, -3.2f, properties.fireResistant());
	}
}