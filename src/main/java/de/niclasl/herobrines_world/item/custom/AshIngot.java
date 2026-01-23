package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class AshIngot extends Item {
	public AshIngot(Item.Properties properties) {
		super(properties.rarity(Rarity.UNCOMMON).fireResistant());
	}
}