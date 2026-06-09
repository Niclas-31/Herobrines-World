package de.niclasl.herobrines_world.common.registries.items;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.entities.ModEntities;
import de.niclasl.herobrines_world.common.registries.items.custom.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HerobrinesWorld.MODID);

	public static final DeferredItem<Item> HEROBRINE_DIAMOND = ITEMS.registerItem(
			"herobrine_diamond",
			(properties) -> new Item(properties.rarity(Rarity.EPIC)));

	public static final DeferredItem<Item> ASH_INGOT = ITEMS.registerItem(
			"ash_ingot",
			(properties) -> new Item(properties.rarity(Rarity.UNCOMMON).fireResistant()));

	public static final DeferredItem<Item> ASH_PICKAXE = ITEMS.registerItem(
			"ash_pickaxe",
			(properties) -> new AshPickaxe(properties.pickaxe(ModToolTiers.ASH, 3f, -3f).fireResistant()));

	public static final DeferredItem<Item> FROZEN_HEART = ITEMS.registerItem(
			"frozen_heart",
			(properties) -> new FrozenHeart(properties.rarity(Rarity.RARE)));

	public static final DeferredItem<Item> HEROBRINE_BOSS_SPAWN_EGG = ITEMS.registerItem(
			"herobrine_boss_spawn_egg",
			properties ->  new SpawnEggItem(properties.spawnEgg(ModEntities.HEROBRINE_BOSS.get())));

	public static final DeferredItem<Item> NICLASL_SPAWN_EGG = ITEMS.registerItem(
			"niclasl_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.NICLASL.get())));

	public static final DeferredItem<Item> ENTITY_303_SPAWN_EGG = ITEMS.registerItem(
			"entity_303_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.ENTITY_303.get())));

	public static final DeferredItem<Item> GOOD_HEROBRINE_SPAWN_EGG = ITEMS.registerItem(
			"good_herobrine_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.GOOD_HEROBRINE.get())));

	public static final DeferredItem<Item> BAD_HEROBRINE_SPAWN_EGG = ITEMS.registerItem(
			"bad_herobrine_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.BAD_HEROBRINE.get())));

	public static final DeferredItem<Item> CHRISTMAS_NICLASL_SPAWN_EGG = ITEMS.registerItem(
			"christmas_niclasl_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.CHRISTMAS_NICLASL.get())));

	public static final DeferredItem<Item> ORE_DETECTOR = ITEMS.registerItem(
			"ore_detector",
			(properties) -> new OreDetector(properties.fireResistant()));

	public static final DeferredItem<Item> NATURE_SWORD = ITEMS.registerItem(
			"nature_sword",
			(properties) -> new Item(
					properties.sword(ModToolTiers.NATURE, 5.4f, -2.6f)));

	public static final DeferredItem<Item> NATURE_SHOVEL = ITEMS.registerItem(
			"nature_shovel",
			(properties) -> new ShovelItem(
					ModToolTiers.NATURE, 1.4f, -3.2f, properties));

	public static final DeferredItem<Item> NATURE_PICKAXE = ITEMS.registerItem(
			"nature_pickaxe",
			(properties) -> new Item(
					properties.pickaxe(ModToolTiers.NATURE, 1.4f, -3.0f)));

	public static final DeferredItem<Item> NATURE_AXE = ITEMS.registerItem(
			"nature_axe",
			(properties) -> new AxeItem(
					ModToolTiers.NATURE, 16f, -3.2f, properties));

	public static final DeferredItem<Item> NATURE_HOE = ITEMS.registerItem(
			"nature_hoe",
			(properties) -> new HoeItem(
					ModToolTiers.NATURE, 5.4f, -3.0f, properties));

	public static final DeferredItem<Item> FIRE_SWORD = ITEMS.registerItem(
			"fire_sword",
			(properties) -> new Item(
					properties.sword(ModToolTiers.FIRE, 5.6f, -2.4f).fireResistant()));

	public static final DeferredItem<Item> FIRE_SHOVEL = ITEMS.registerItem(
			"fire_shovel",
			(properties) -> new ShovelItem(
					ModToolTiers.FIRE, 1.6f, -3.0f, properties.fireResistant()));

	public static final DeferredItem<Item> FIRE_PICKAXE = ITEMS.registerItem(
			"fire_pickaxe",
			(properties) -> new Item(
					properties.pickaxe(ModToolTiers.FIRE, 1.6f, -2.8f).fireResistant()));

	public static final DeferredItem<Item> FIRE_AXE = ITEMS.registerItem(
			"fire_axe",
			(properties) -> new AxeItem(
					ModToolTiers.FIRE, 18f, -3.0f, properties.fireResistant()));

	public static final DeferredItem<Item> FIRE_HOE = ITEMS.registerItem(
			"fire_hoe",
			(properties) -> new HoeItem(
					ModToolTiers.FIRE, 5.6f, -2.8f, properties.fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_SWORD = ITEMS.registerItem(
			"herobrine_sword",
			(properties) -> new Item(
					properties.sword(ModToolTiers.HEROBRINE, 5.8f, -2.2f)));

	public static final DeferredItem<Item> HEROBRINE_SHOVEL = ITEMS.registerItem(
			"herobrine_shovel",
			(properties) -> new ShovelItem(
					ModToolTiers.HEROBRINE, 1.8f, -2.8f, properties));

	public static final DeferredItem<Item> HEROBRINE_PICKAXE = ITEMS.registerItem(
			"herobrine_pickaxe",
			(properties) -> new Item(
					properties.pickaxe(ModToolTiers.HEROBRINE, 1.8f, -2.6f)));

	public static final DeferredItem<Item> HEROBRINE_AXE = ITEMS.registerItem(
			"herobrine_axe",
			(properties) -> new AxeItem(
					ModToolTiers.HEROBRINE, 20f, -2.8f, properties));

	public static final DeferredItem<Item> HEROBRINE_HOE = ITEMS.registerItem(
			"herobrine_hoe",
			(properties) -> new HoeItem(
					ModToolTiers.HEROBRINE, 5.8f, -2.6f, properties));

	public static final DeferredItem<Item> PLATIN_SWORD = ITEMS.registerItem(
			"platin_sword",
			(properties) -> new Item(
					properties.sword(ModToolTiers.PLATIN, 6f, -2f)));

	public static final DeferredItem<Item> PLATIN_SHOVEL = ITEMS.registerItem(
			"platin_shovel",
			(properties) -> new ShovelItem(
					ModToolTiers.PLATIN, 2f, -2.6f, properties));

	public static final DeferredItem<Item> PLATIN_PICKAXE = ITEMS.registerItem(
			"platin_pickaxe",
			(properties) -> new Item(
					properties.pickaxe(ModToolTiers.PLATIN, 2f, -2.4f)));

	public static final DeferredItem<Item> PLATIN_AXE = ITEMS.registerItem(
			"platin_axe",
			(properties) -> new AxeItem(
					ModToolTiers.PLATIN, 22f, -2.6f, properties));

	public static final DeferredItem<Item> PLATIN_HOE = ITEMS.registerItem(
			"platin_hoe",
			(properties) -> new HoeItem(
					ModToolTiers.PLATIN, 6f, -2.4f, properties));

	public static final DeferredItem<Item> NATURE_HELMET = ITEMS.registerItem(
			"nature_helmet",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.HELMET)));

	public static final DeferredItem<Item> NATURE_CHESTPLATE = ITEMS.registerItem(
			"nature_chestplate",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.CHESTPLATE)));

	public static final DeferredItem<Item> NATURE_LEGGINGS = ITEMS.registerItem(
			"nature_leggings",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.LEGGINGS)));

	public static final DeferredItem<Item> NATURE_BOOTS = ITEMS.registerItem(
			"nature_boots",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.BOOTS)));

	public static final DeferredItem<Item> FIRE_HELMET = ITEMS.registerItem(
			"fire_helmet",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.FIRE_ARMOR_MATERIAL,
					ArmorType.HELMET).fireResistant()));

	public static final DeferredItem<Item> FIRE_CHESTPLATE = ITEMS.registerItem(
			"fire_chestplate",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.FIRE_ARMOR_MATERIAL,
					ArmorType.CHESTPLATE).fireResistant()));

	public static final DeferredItem<Item> FIRE_LEGGINGS = ITEMS.registerItem(
			"fire_leggings",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.FIRE_ARMOR_MATERIAL,
					ArmorType.LEGGINGS).fireResistant()));

	public static final DeferredItem<Item> FIRE_BOOTS = ITEMS.registerItem(
			"fire_boots",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.FIRE_ARMOR_MATERIAL,
					ArmorType.BOOTS).fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_HELMET = ITEMS.registerItem(
			"herobrine_helmet",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.HEROBRINE_ARMOR_MATERIAL,
					ArmorType.HELMET)));

	public static final DeferredItem<Item> HEROBRINE_CHESTPLATE = ITEMS.registerItem(
			"herobrine_chestplate",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.HEROBRINE_ARMOR_MATERIAL,
					ArmorType.CHESTPLATE)));

	public static final DeferredItem<Item> HEROBRINE_LEGGINGS = ITEMS.registerItem(
			"herobrine_leggings",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.HEROBRINE_ARMOR_MATERIAL,
					ArmorType.LEGGINGS)));

	public static final DeferredItem<Item> HEROBRINE_BOOTS = ITEMS.registerItem(
			"herobrine_boots",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.HEROBRINE_ARMOR_MATERIAL,
					ArmorType.BOOTS)));

	public static final DeferredItem<Item> PLATIN_HELMET = ITEMS.registerItem(
			"platin_helmet",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.PLATIN_ARMOR_MATERIAL,
					ArmorType.HELMET)));

	public static final DeferredItem<Item> PLATIN_CHESTPLATE = ITEMS.registerItem(
			"platin_chestplate",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.PLATIN_ARMOR_MATERIAL,
					ArmorType.CHESTPLATE)));

	public static final DeferredItem<Item> PLATIN_LEGGINGS = ITEMS.registerItem(
			"platin_leggings",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.PLATIN_ARMOR_MATERIAL,
					ArmorType.LEGGINGS)));

	public static final DeferredItem<Item> PLATIN_BOOTS = ITEMS.registerItem(
			"platin_boots",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.PLATIN_ARMOR_MATERIAL,
					ArmorType.BOOTS)));

	public static final DeferredItem<Item> HEROBRINES_REALM = ITEMS.registerItem(
			"herobrines_realm",
			(properties) -> new HerobrinesRealm(properties.stacksTo(1).rarity(Rarity.RARE)
					.durability(64)));

	public static final DeferredItem<Item> UNDERWORLD = ITEMS.registerItem(
			"underworld",
			(properties) -> new Underworld(properties.stacksTo(1).rarity(Rarity.EPIC)
					.durability(64)));

	public static final DeferredItem<Item> GREEN_GEMSTONE = ITEMS.registerItem(
			"green_gemstone",
			(properties) -> new Item(properties.rarity(Rarity.RARE)));

	public static final DeferredItem<Item> WAYPOINT_COMPASS = ITEMS.registerItem(
			"waypoint_compass",
			(properties) -> new WaypointCompass(properties.rarity(Rarity.RARE)
					.stacksTo(1)));

	public static final DeferredItem<Item> HEROBRINE_RELIC = ITEMS.registerItem(
			"herobrine_relic",
			(properties) -> new HerobrineRelicItem(properties.fireResistant().stacksTo(1)));

	public static final DeferredItem<Item> BATTERY = ITEMS.registerItem(
			"battery",
			(properties) -> new BatteryItem(properties.rarity(Rarity.EPIC).stacksTo(1)
					.component(ModDataComponents.ENERGY.get(), 0)));

	public static final DeferredItem<Item> SMART_CHIP = ITEMS.registerItem(
			"smart_chip",
			(properties) -> new SmartChip(properties.rarity(Rarity.EPIC).stacksTo(1))
	);

	public static final DeferredItem<Item> SMART_CHIP_CASE = ITEMS.registerItem(
			"smart_chip_case",
			(properties) -> new Item(properties.rarity(Rarity.EPIC).stacksTo(1))
	);

	public static final DeferredItem<Item> PLATIN_INGOT = ITEMS.registerItem(
			"platin_ingot",
			(properties) -> new Item(properties.rarity(Rarity.RARE))
	);

	public static final DeferredItem<Item> KEY_CARD = ITEMS.registerItem(
			"key_card",
			(properties) -> new KeyCard(properties.rarity(Rarity.EPIC).stacksTo(1))
	);

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}