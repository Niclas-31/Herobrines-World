package de.niclasl.herobrines_world.common.registries.items;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.EnumMap;

public class ModArmorMaterials {
    static ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static ResourceKey<EquipmentAsset> NATURE = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "nature"));
    public static ResourceKey<EquipmentAsset> FIRE = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "fire"));
    public static ResourceKey<EquipmentAsset> HEROBRINE = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "herobrine"));
    public static ResourceKey<EquipmentAsset> PLATIN = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "platin"));

    public static final ArmorMaterial NATURE_ARMOR_MATERIAL = new ArmorMaterial(150,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 1);
                map.put(ArmorType.LEGGINGS, 3);
                map.put(ArmorType.CHESTPLATE, 4);
                map.put(ArmorType.HELMET, 1);
                map.put(ArmorType.BODY, 4);
            }),
            12,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0f,
            0.0f,
            ModTags.Items.REPAIRS_NATURE_ARMOR,
            NATURE
    );

    public static final ArmorMaterial FIRE_ARMOR_MATERIAL = new ArmorMaterial(250,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 5);
                map.put(ArmorType.CHESTPLATE, 6);
                map.put(ArmorType.HELMET, 2);
                map.put(ArmorType.BODY, 6);
            }),
            14,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0f,
            0.0f,
            ModTags.Items.REPAIRS_FIRE_ARMOR,
            FIRE
    );

    public static final ArmorMaterial HEROBRINE_ARMOR_MATERIAL = new ArmorMaterial(400,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 8);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 8);
            }),
            12,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            2.0f,
            0.0f,
            ModTags.Items.REPAIRS_HEROBRINE_ARMOR,
            HEROBRINE
    );

    public static final ArmorMaterial PLATIN_ARMOR_MATERIAL = new ArmorMaterial(500,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 8);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 8);
            }),
            18,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            3.0f,
            0.1f,
            ModTags.Items.REPAIRS_PLATIN_ARMOR,
            PLATIN
    );
}