package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class HerobrinePickaxe extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			7500,
			10f,
			0,
			18,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:herobrine_pickaxe_repair_items")
			)
	);

	public HerobrinePickaxe(Item.Properties properties) {
		super(properties.pickaxe(TOOL_MATERIAL, 1.8f, -2.6f).fireResistant());
	}
}