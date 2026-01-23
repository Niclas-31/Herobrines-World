package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class FireShovel extends ShovelItem {

	private static final ToolMaterial FIRE_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_STONE_TOOL,
			5000,
			8f,
			0f,
			16,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:fire_shovel_repair_items")
			)
	);

	public FireShovel(Item.Properties properties) {
		super(
				FIRE_MATERIAL,
				1.6f,
				-3.0f,
				properties.fireResistant()
		);
	}
}