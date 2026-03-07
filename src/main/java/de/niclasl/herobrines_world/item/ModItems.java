package de.niclasl.herobrines_world.item;

import de.niclasl.herobrines_world.entity.ModEntities;
import de.niclasl.herobrines_world.item.custom.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber
public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HerobrinesWorld.MODID);

	public static final DeferredItem<Item> HEROBRINE_DIAMOND = ITEMS.registerItem(
			"herobrine_diamond",
			(properties) -> new Item(properties.rarity(Rarity.EPIC)));

	public static final DeferredItem<Item> ASH = ITEMS.registerItem(
			"ash",
			(properties) -> new Item(properties.rarity(Rarity.UNCOMMON).fireResistant()));

	public static final DeferredItem<Item> ASH_INGOT = ITEMS.registerItem(
			"ash_ingot",
			(properties) -> new Item(properties.rarity(Rarity.UNCOMMON).fireResistant()));

	public static final DeferredItem<Item> ASH_PICKAXE = ITEMS.registerItem(
			"ash_pickaxe",
			AshPickaxe::new);

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

	public static final DeferredItem<Item> TOXENIUM_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerItem(
			"toxenium_upgrade_smithing_template",
			(properties) -> new Item(properties.rarity(Rarity.EPIC).fireResistant()));

	public static final DeferredItem<Item> ORE_DETECTOR = ITEMS.registerItem(
			"ore_detector",
			(properties) -> new OreDetector(properties.fireResistant()));

	public static final DeferredItem<Item> NATURE_SWORD = ITEMS.registerItem(
			"nature_sword",
			(properties) -> new Item(
					properties.sword(ModToolTiers.NATURE, 5.4f, -2.6f)
							.fireResistant()));

	public static final DeferredItem<Item> NATURE_SHOVEL = ITEMS.registerItem(
			"nature_shovel",
			(properties) -> new ShovelItem(
					ModToolTiers.NATURE, 1.4f, -3.2f, properties.fireResistant()));

	public static final DeferredItem<Item> NATURE_PICKAXE = ITEMS.registerItem(
			"nature_pickaxe",
			(properties) -> new Item(
					properties.pickaxe(ModToolTiers.NATURE, 1.4f, -3.0f)
							.fireResistant()));

	public static final DeferredItem<Item> NATURE_AXE = ITEMS.registerItem(
			"nature_axe",
			(properties) -> new AxeItem(
					ModToolTiers.NATURE, 16f, -3.2f, properties.fireResistant()));

	public static final DeferredItem<Item> NATURE_HOE = ITEMS.registerItem(
			"nature_hoe",
			(properties) -> new HoeItem(
					ModToolTiers.NATURE, 5.4f, -3.0f, properties.fireResistant()));

	public static final DeferredItem<Item> FIRE_SWORD = ITEMS.registerItem(
			"fire_sword",
			(properties) -> new Item(
					properties.sword(ModToolTiers.FIRE, 5.6f, -2.4f)
							.fireResistant()));

	public static final DeferredItem<Item> FIRE_SHOVEL = ITEMS.registerItem(
			"fire_shovel",
			(properties) -> new ShovelItem(
					ModToolTiers.FIRE, 1.6f, -3.0f, properties.fireResistant()));

	public static final DeferredItem<Item> FIRE_PICKAXE = ITEMS.registerItem(
			"fire_pickaxe",
			(properties) -> new Item(
					properties.pickaxe(ModToolTiers.FIRE, 1.6f, -2.8f)
							.fireResistant()));

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
					properties.sword(ModToolTiers.HEROBRINE, 5.8f, -2.2f)
							.fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_SHOVEL = ITEMS.registerItem(
			"herobrine_shovel",
			(properties) -> new ShovelItem(
					ModToolTiers.HEROBRINE, 1.8f, -2.8f, properties.fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_PICKAXE = ITEMS.registerItem(
			"herobrine_pickaxe",
			(properties) -> new Item(
					properties.pickaxe(ModToolTiers.HEROBRINE, 1.8f, -2.6f)
							.fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_AXE = ITEMS.registerItem(
			"herobrine_axe",
			(properties) -> new AxeItem(
					ModToolTiers.HEROBRINE, 20f, -2.8f, properties.fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_HOE = ITEMS.registerItem(
			"herobrine_hoe",
			(properties) -> new HoeItem(
					ModToolTiers.HEROBRINE, 5.8f, -2.6f, properties.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_SWORD = ITEMS.registerItem(
			"toxenium_sword",
			(properties) -> new ToxeniumSword(
					properties.sword(ModToolTiers.TOXENIUM, 6f, -2f)
							.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_SHOVEL = ITEMS.registerItem(
			"toxenium_shovel",
			(properties) -> new ToxeniumShovel(properties.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_PICKAXE = ITEMS.registerItem(
			"toxenium_pickaxe",
			(properties) -> new ToxeniumPickaxe(
					properties.pickaxe(ModToolTiers.TOXENIUM, 2f, -2.4f)
							.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_AXE = ITEMS.registerItem(
			"toxenium_axe",
			(properties) -> new ToxeniumAxe(properties.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_HOE = ITEMS.registerItem(
			"toxenium_hoe",
			(properties) -> new ToxeniumHoe(properties.fireResistant()));

	public static final DeferredItem<Item> NATURE_HELMET = ITEMS.registerItem(
			"nature_helmet",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.HELMET).fireResistant()));

	public static final DeferredItem<Item> NATURE_CHESTPLATE = ITEMS.registerItem(
			"nature_chestplate",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.CHESTPLATE).fireResistant()));

	public static final DeferredItem<Item> NATURE_LEGGINGS = ITEMS.registerItem(
			"nature_leggings",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.LEGGINGS).fireResistant()));

	public static final DeferredItem<Item> NATURE_BOOTS = ITEMS.registerItem(
			"nature_boots",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.NATURE_ARMOR_MATERIAL,
					ArmorType.BOOTS).fireResistant()));

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
					ArmorType.HELMET).fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_CHESTPLATE = ITEMS.registerItem(
			"herobrine_chestplate",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.HEROBRINE_ARMOR_MATERIAL,
					ArmorType.CHESTPLATE).fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_LEGGINGS = ITEMS.registerItem(
			"herobrine_leggings",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.HEROBRINE_ARMOR_MATERIAL,
					ArmorType.LEGGINGS).fireResistant()));

	public static final DeferredItem<Item> HEROBRINE_BOOTS = ITEMS.registerItem(
			"herobrine_boots",
			(properties) -> new Item(properties.humanoidArmor(ModArmorMaterials.HEROBRINE_ARMOR_MATERIAL,
					ArmorType.BOOTS).fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_HELMET = ITEMS.registerItem(
			"toxenium_helmet",
			(properties) -> new Toxenium.Helmet(properties.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_CHESTPLATE = ITEMS.registerItem(
			"toxenium_chestplate",
			(properties) -> new Toxenium.Chestplate(properties.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_LEGGINGS = ITEMS.registerItem(
			"toxenium_leggings",
			(properties) -> new Toxenium.Leggings(properties.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_BOOTS = ITEMS.registerItem(
			"toxenium_boots",
			(properties) -> new Toxenium.Boots(properties.fireResistant()));

	public static final DeferredItem<Item> TOXENIUM_INGOT = ITEMS.registerItem(
			"toxenium_ingot",
			(properties) -> new ToxeniumIngot(properties.rarity(Rarity.EPIC).fireResistant()));

	public static final DeferredItem<Item> TIMER_CLOCK = ITEMS.registerItem(
			"timer_clock",
			(properties) -> new TimerClock(properties.rarity(Rarity.EPIC).stacksTo(1)));

	public static final DeferredItem<Item> TIME_CLOCK = ITEMS.registerItem(
			"time_clock",
			(properties) -> new TimeClock(properties.rarity(Rarity.EPIC).stacksTo(1)));

	public static final DeferredItem<Item> HEROBRINES_REALM = ITEMS.registerItem(
			"herobrines_realm",
			(properties) -> new HerobrinesRealm(properties.rarity(Rarity.RARE).durability(64)));

	public static final DeferredItem<Item> UNDERWORLD = ITEMS.registerItem(
			"underworld",
			(properties) -> new Underworld(properties.rarity(Rarity.EPIC).durability(64)));

	public static final DeferredItem<Item> GREEN_GEMSTONE = ITEMS.registerItem(
			"green_gemstone",
			(properties) -> new Item(properties.rarity(Rarity.RARE)));

	public static final DeferredItem<Item> RUNE_STONE = ITEMS.registerItem(
			"rune_stone",
			(properties) -> new RuneStone(properties.rarity(Rarity.RARE).stacksTo(1)));

	public static final DeferredItem<Item> HEROBRINE_RELIC = ITEMS.registerItem(
			"herobrine_relic",
			(properties) -> new HerobrineRelicItem(properties.fireResistant().stacksTo(1)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {

		event.registerItem(
				Capabilities.Item.ITEM,
				(stack, access) -> new ResourceHandler<>() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public @Nullable ItemResource getResource(int i) {
                        return null;
                    }

                    @Override
                    public long getAmountAsLong(int i) {
                        return 0;
                    }

                    @Override
                    public long getCapacityAsLong(int i, @NotNull ItemResource resource) {
                        return 0;
                    }

                    @Override
                    public boolean isValid(int i, @NotNull ItemResource resource) {
                        return stack.getItem() != ModItems.TIMER_CLOCK.get();
                    }

                    @Override
                    public int insert(int i, @NotNull ItemResource resource, int amount, @NotNull TransactionContext context) {
                        return 0;
                    }

                    @Override
                    public int extract(int i, @NotNull ItemResource resource, int amount, @NotNull TransactionContext context) {
                        return 0;
                    }
                },
				ModItems.TIMER_CLOCK.get()
		);

		event.registerItem(
				Capabilities.Item.ITEM,
				(stack, access) -> new ResourceHandler<>() {
					@Override
					public int size() { return 0; }

					@Override
					public @Nullable ItemResource getResource(int i) { return null; }

					@Override
					public long getAmountAsLong(int i) { return 0; }

					@Override
					public long getCapacityAsLong(int i, @NotNull ItemResource resource) { return 0; }

					@Override
					public boolean isValid(int i, @NotNull ItemResource resource) {
						return stack.getItem() != ModItems.TIME_CLOCK.get();
					}

					@Override
					public int insert(int i, @NotNull ItemResource resource, int amount, @NotNull TransactionContext context) {
						return 0;
					}

					@Override
					public int extract(int i, @NotNull ItemResource resource, int amount, @NotNull TransactionContext context) {
						return 0;
					}
				},
				ModItems.TIME_CLOCK.get()
		);
	}
}