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

public abstract class Herobrine extends Item {
	public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(
			7500,
			Map.of(
					ArmorType.BOOTS, 500,
					ArmorType.LEGGINGS, 800,
					ArmorType.CHESTPLATE, 900,
					ArmorType.HELMET, 600,
					ArmorType.BODY, 900
			),
			40,
			DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("item.armor.equip_diamond")),
			3.7f,
			2.5f,
			TagKey.create(Registries.ITEM, ResourceLocation.parse("herobrines_world:herobrine_repair_items")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse("herobrines_world:herobrine"))
	);

	private Herobrine(Item.Properties properties) {
		super(properties);
	}

	public static class Helmet extends Herobrine {
		public Helmet(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.HELMET).fireResistant());
		}
	}

	public static class Chestplate extends Herobrine {
		public Chestplate(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.CHESTPLATE).fireResistant());
		}
	}

	public static class Leggings extends Herobrine {
		public Leggings(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.LEGGINGS).fireResistant());
		}
	}

	public static class Boots extends Herobrine {
		public Boots(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.BOOTS).fireResistant());
		}
	}
}