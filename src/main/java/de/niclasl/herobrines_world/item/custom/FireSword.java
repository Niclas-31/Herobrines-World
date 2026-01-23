package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class FireSword extends Item {

	private static final ToolMaterial FIRE_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_STONE_TOOL,
			5000,
			8f,
			0f,
			15,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:fire_sword_repair_items")
			)
	);

	public FireSword(Item.Properties properties) {
		super(properties.sword(
				FIRE_MATERIAL,
				5.6f,
				-2.4f
		).fireResistant());
	}
}