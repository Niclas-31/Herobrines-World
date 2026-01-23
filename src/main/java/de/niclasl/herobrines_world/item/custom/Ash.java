package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class Ash extends Item {
	public Ash(Item.Properties properties) {
		super(properties.rarity(Rarity.UNCOMMON).fireResistant());
	}
}