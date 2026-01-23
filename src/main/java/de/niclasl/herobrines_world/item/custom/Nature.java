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

public abstract class Nature extends Item {
	public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(
			2500,
			Map.of(
					ArmorType.BOOTS, 300,
					ArmorType.LEGGINGS, 600,
					ArmorType.CHESTPLATE, 700,
					ArmorType.HELMET, 400,
					ArmorType.BODY, 700
			),
			20,
			DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("item.armor.equip_chain")),
			2.7f,
			1.8f,
			TagKey.create(Registries.ITEM, ResourceLocation.parse("herobrines_world:nature_repair_items")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse("herobrines_world:nature"))
	);

	private Nature(Item.Properties properties) {
		super(properties);
	}

	public static class Helmet extends Nature {
		public Helmet(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.HELMET).fireResistant());
		}
	}

	public static class Chestplate extends Nature {
		public Chestplate(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.CHESTPLATE).fireResistant());
		}
	}

	public static class Leggings extends Nature {
		public Leggings(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.LEGGINGS).fireResistant());
		}
	}

	public static class Boots extends Nature {
		public Boots(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.BOOTS).fireResistant());
		}
	}
}