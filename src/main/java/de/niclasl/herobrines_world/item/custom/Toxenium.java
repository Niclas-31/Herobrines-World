package de.niclasl.herobrines_world.item.custom;

import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.procedures.ToxeniumArmorTickEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import java.util.Map;

public abstract class Toxenium extends Item {
	public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(
			10000,
			Map.of(
					ArmorType.BOOTS, 600,
					ArmorType.LEGGINGS, 900,
					ArmorType.CHESTPLATE, 1000,
					ArmorType.HELMET, 700,
					ArmorType.BODY, 1000
			),
			50,
			DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("item.armor.equip_netherite")),
			4.3f,
			3.2f,
			TagKey.create(Registries.ITEM, ResourceLocation.parse("herobrines_world:toxenium_repair_items")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse("herobrines_world:toxenium")));

	private Toxenium(Item.Properties properties) {
		super(properties);
	}

	public static class Helmet extends Toxenium {
		public Helmet(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.HELMET).fireResistant());
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				ToxeniumArmorTickEvent.execute(entity);
			}
		}
	}

	public static class Chestplate extends Toxenium {
		public Chestplate(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.CHESTPLATE).fireResistant());
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				ToxeniumArmorTickEvent.execute(entity);
			}
		}
	}

	public static class Leggings extends Toxenium {
		public Leggings(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.LEGGINGS).fireResistant());
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				ToxeniumArmorTickEvent.execute(entity);
			}
		}
	}

	public static class Boots extends Toxenium {
		public Boots(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.BOOTS).fireResistant());
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				ToxeniumArmorTickEvent.execute(entity);
			}
		}
	}
}