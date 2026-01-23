package de.niclasl.herobrines_world.item.custom;

import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import java.util.Map;

public abstract class Fire extends Item {
	public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(
			5000,
			Map.of(
					ArmorType.BOOTS, 400,
					ArmorType.LEGGINGS, 700,
					ArmorType.CHESTPLATE, 800,
					ArmorType.HELMET, 500,
					ArmorType.BODY, 800
			),
			30,
			DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("item.armor.equip_iron")),
			3.6f,
			2.0f,
			TagKey.create(Registries.ITEM, ResourceLocation.parse("herobrines_world:fire_repair_items")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse("herobrines_world:fire"))
	);

	private Fire(Item.Properties properties) {
		super(properties);
	}

	public static class Helmet extends Fire {
		public Helmet(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.HELMET).fireResistant());
		}
	}

	public static class Chestplate extends Fire {
		public Chestplate(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.CHESTPLATE).fireResistant());
		}
	}

	public static class Leggings extends Fire {
		public Leggings(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.LEGGINGS).fireResistant());
		}
	}

	public static class Boots extends Fire {
		public Boots(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.BOOTS).fireResistant());
		}
	}
}