package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class FrozenHeart extends Item {
	public FrozenHeart(Item.Properties properties) {
		super(properties.rarity(Rarity.RARE));
	}
}