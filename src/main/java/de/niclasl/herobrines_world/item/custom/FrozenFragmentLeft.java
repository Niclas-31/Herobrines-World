package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class FrozenFragmentLeft extends Item {
	public FrozenFragmentLeft(Item.Properties properties) {
		super(properties.rarity(Rarity.RARE));
	}
}