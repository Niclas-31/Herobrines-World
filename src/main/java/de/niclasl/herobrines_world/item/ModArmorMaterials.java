package de.niclasl.herobrines_world.item;

import de.niclasl.herobrines_world.util.ModTags;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

public class ModArmorMaterials {
    public static ResourceKey<EquipmentAsset> NATURE = EquipmentAssets.createId("nature");
    public static ResourceKey<EquipmentAsset> FIRE = EquipmentAssets.createId("fire");
    public static ResourceKey<EquipmentAsset> HEROBRINE = EquipmentAssets.createId("herobrine");
    public static ResourceKey<EquipmentAsset> TOXENIUM = EquipmentAssets.createId("toxenium");

    public static final ArmorMaterial NATURE_ARMOR_MATERIAL = new ArmorMaterial(2500,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 300);
                attribute.put(ArmorType.LEGGINGS, 600);
                attribute.put(ArmorType.CHESTPLATE, 700);
                attribute.put(ArmorType.HELMET, 400);
                attribute.put(ArmorType.BODY, 700);
            }), 20, SoundEvents.ARMOR_EQUIP_CHAIN,
            2.7f, 1.8f, ModTags.Items.NATURE_REPAIRABLE, NATURE);

    public static final ArmorMaterial FIRE_ARMOR_MATERIAL = new ArmorMaterial(5000,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 400);
                attribute.put(ArmorType.LEGGINGS, 700);
                attribute.put(ArmorType.CHESTPLATE, 800);
                attribute.put(ArmorType.HELMET, 500);
                attribute.put(ArmorType.BODY, 800);
            }),30, SoundEvents.ARMOR_EQUIP_IRON,
            3.6f, 2.0f, ModTags.Items.FIRE_REPAIRABLE, FIRE);

    public static final ArmorMaterial HEROBRINE_ARMOR_MATERIAL = new ArmorMaterial(7500,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 500);
                attribute.put(ArmorType.LEGGINGS, 800);
                attribute.put(ArmorType.CHESTPLATE, 900);
                attribute.put(ArmorType.HELMET, 600);
                attribute.put(ArmorType.BODY, 900);
            }), 40, SoundEvents.ARMOR_EQUIP_DIAMOND,
            3.7f, 2.5f, ModTags.Items.HEROBRINE_REPAIRABLE, HEROBRINE);

    public static final ArmorMaterial TOXENIUM_ARMOR_MATERIAL = new ArmorMaterial(10000,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 600);
                attribute.put(ArmorType.LEGGINGS, 900);
                attribute.put(ArmorType.CHESTPLATE, 1000);
                attribute.put(ArmorType.HELMET, 700);
                attribute.put(ArmorType.BODY, 1000);
            }), 50, SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.3f, 3.2f, ModTags.Items.TOXENIUM_REPAIRABLE, TOXENIUM);
}
