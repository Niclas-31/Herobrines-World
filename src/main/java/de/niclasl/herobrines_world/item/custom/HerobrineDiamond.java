package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class HerobrineDiamond extends Item {
	public HerobrineDiamond(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC));
	}
}