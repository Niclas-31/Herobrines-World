package de.niclasl.herobrines_world.item;

import de.niclasl.herobrines_world.entity.ModEntities;
import de.niclasl.herobrines_world.item.custom.*;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.Item;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber
public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HerobrinesWorld.MODID);

	public static final DeferredItem<Item> HEROBRINE_BOSS_SPAWN_EGG = ITEMS.registerItem(
			"herobrine_boss_spawn_egg",
            properties ->  new SpawnEggItem(properties.spawnEgg(ModEntities.HEROBRINE_BOSS.get())));

	public static final DeferredItem<Item> HEROBRINE_DIAMOND = ITEMS.registerItem(
			"herobrine_diamond",
			HerobrineDiamond::new
	);
	public static final DeferredItem<Item> ASH = ITEMS.registerItem(
			"ash",
			Ash::new
	);
	public static final DeferredItem<Item> ASH_INGOT = ITEMS.registerItem(
			"ash_ingot",
			AshIngot::new
	);
	public static final DeferredItem<Item> ASH_PICKAXE = ITEMS.registerItem(
			"ash_pickaxe",
			AshPickaxe::new
	);
	public static final DeferredItem<Item> FROZEN_HEART = ITEMS.registerItem(
			"frozen_heart",
			FrozenHeart::new
	);
	public static final DeferredItem<Item> FROZEN_FRAGMENT_LEFT = ITEMS.registerItem(
			"frozen_fragment_left",
			FrozenFragmentLeft::new
	);
	public static final DeferredItem<Item> FROZEN_FRAGMENT_RIGHT = ITEMS.registerItem(
			"frozen_fragment_right",
			FrozenFragmentRight::new
	);
	public static final DeferredItem<Item> NICLASL_SPAWN_EGG = ITEMS.registerItem(
			"niclasl_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.NICLASL.get()))
	);
	public static final DeferredItem<Item> ENTITY_303_SPAWN_EGG = ITEMS.registerItem(
			"entity_303_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.ENTITY_303.get()))
	);
	public static final DeferredItem<Item> TOXENIUM_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerItem(
			"toxenium_upgrade_smithing_template",
			ToxeniumUpgradeSmithingTemplate::new
	);
	public static final DeferredItem<Item> ORE_DETECTOR = ITEMS.registerItem(
			"ore_detector",
			OreDetector::new
	);
	public static final DeferredItem<Item> NATURE_SWORD = ITEMS.registerItem(
			"nature_sword",
			NatureSword::new
	);
	public static final DeferredItem<Item> FIRE_SWORD = ITEMS.registerItem(
			"fire_sword",
			FireSword::new
	);
	public static final DeferredItem<Item> HEROBRINE_SWORD = ITEMS.registerItem(
			"herobrine_sword",
			HerobrineSword::new
	);
	public static final DeferredItem<Item> TOXENIUM_SWORD = ITEMS.registerItem(
			"toxenium_sword",
			ToxeniumSword::new
	);
	public static final DeferredItem<Item> NATURE_SHOVEL = ITEMS.registerItem(
			"nature_shovel",
			NatureShovel::new
	);
	public static final DeferredItem<Item> NATURE_PICKAXE = ITEMS.registerItem(
			"nature_pickaxe",
			NaturePickaxe::new
	);
	public static final DeferredItem<Item> NATURE_AXE = ITEMS.registerItem(
			"nature_axe",
			NatureAxe::new
	);
	public static final DeferredItem<Item> NATURE_HOE = ITEMS.registerItem(
			"nature_hoe",
			NatureHoe::new
	);
	public static final DeferredItem<Item> FIRE_SHOVEL = ITEMS.registerItem(
			"fire_shovel",
			FireShovel::new
	);
	public static final DeferredItem<Item> FIRE_PICKAXE = ITEMS.registerItem(
			"fire_pickaxe",
			FirePickaxe::new
	);
	public static final DeferredItem<Item> FIRE_AXE = ITEMS.registerItem(
			"fire_axe",
			FireAxe::new
	);
	public static final DeferredItem<Item> FIRE_HOE = ITEMS.registerItem(
			"fire_hoe",
			FireHoe::new
	);
	public static final DeferredItem<Item> HEROBRINE_SHOVEL = ITEMS.registerItem(
			"herobrine_shovel",
			HerobrineShovel::new
	);
	public static final DeferredItem<Item> HEROBRINE_PICKAXE = ITEMS.registerItem(
			"herobrine_pickaxe",
			HerobrinePickaxe::new
	);
	public static final DeferredItem<Item> HEROBRINE_AXE = ITEMS.registerItem(
			"herobrine_axe",
			HerobrineAxe::new
	);
	public static final DeferredItem<Item> HEROBRINE_HOE = ITEMS.registerItem(
			"herobrine_hoe",
			HerobrineHoe::new
	);
	public static final DeferredItem<Item> TOXENIUM_SHOVEL = ITEMS.registerItem(
			"toxenium_shovel",
			ToxeniumShovel::new
	);
	public static final DeferredItem<Item> TOXENIUM_PICKAXE = ITEMS.registerItem(
			"toxenium_pickaxe",
			ToxeniumPickaxe::new
	);
	public static final DeferredItem<Item> TOXENIUM_AXE = ITEMS.registerItem(
			"toxenium_axe",
			ToxeniumAxe::new
	);
	public static final DeferredItem<Item> TOXENIUM_HOE = ITEMS.registerItem(
			"toxenium_hoe",
			ToxeniumHoe::new
	);
	public static final DeferredItem<Item> NATURE_HELMET = ITEMS.registerItem(
			"nature_helmet",
			Nature.Helmet::new
	);
	public static final DeferredItem<Item> NATURE_CHESTPLATE = ITEMS.registerItem(
			"nature_chestplate",
			Nature.Chestplate::new
	);
	public static final DeferredItem<Item> NATURE_LEGGINGS = ITEMS.registerItem(
			"nature_leggings",
			Nature.Leggings::new
	);
	public static final DeferredItem<Item> NATURE_BOOTS = ITEMS.registerItem(
			"nature_boots",
			Nature.Boots::new
	);
	public static final DeferredItem<Item> FIRE_HELMET = ITEMS.registerItem(
			"fire_helmet",
			Fire.Helmet::new
	);
	public static final DeferredItem<Item> FIRE_CHESTPLATE = ITEMS.registerItem(
			"fire_chestplate",
			Fire.Chestplate::new
	);
	public static final DeferredItem<Item> FIRE_LEGGINGS = ITEMS.registerItem(
			"fire_leggings",
			Fire.Leggings::new
	);
	public static final DeferredItem<Item> FIRE_BOOTS = ITEMS.registerItem(
			"fire_boots",
			Fire.Boots::new
	);
	public static final DeferredItem<Item> GOOD_HEROBRINE_SPAWN_EGG = ITEMS.registerItem(
			"good_herobrine_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.GOOD_HEROBRINE.get()))
	);
	public static final DeferredItem<Item> BAD_HEROBRINE_SPAWN_EGG = ITEMS.registerItem(
			"bad_herobrine_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.BAD_HEROBRINE.get()))
	);
	public static final DeferredItem<Item> CHRISTMAS_NICLASL_SPAWN_EGG = ITEMS.registerItem(
			"christmas_niclasl_spawn_egg",
			properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.CHRISTMAS_NICLASL.get()))
	);
	public static final DeferredItem<Item> HEROBRINE_HELMET = ITEMS.registerItem(
			"herobrine_helmet",
			Herobrine.Helmet::new
	);
	public static final DeferredItem<Item> HEROBRINE_CHESTPLATE = ITEMS.registerItem(
			"herobrine_chestplate",
			Herobrine.Chestplate::new
	);
	public static final DeferredItem<Item> HEROBRINE_LEGGINGS = ITEMS.registerItem(
			"herobrine_leggings",
			Herobrine.Leggings::new
	);
	public static final DeferredItem<Item> HEROBRINE_BOOTS = ITEMS.registerItem(
			"herobrine_boots",
			Herobrine.Boots::new
	);
	public static final DeferredItem<Item> TOXENIUM_INGOT = ITEMS.registerItem(
			"toxenium_ingot",
			ToxeniumIngot::new
	);
	public static final DeferredItem<Item> TOXENIUM_HELMET = ITEMS.registerItem(
			"toxenium_helmet",
			Toxenium.Helmet::new
	);
	public static final DeferredItem<Item> TOXENIUM_CHESTPLATE = ITEMS.registerItem(
			"toxenium_chestplate",
			Toxenium.Chestplate::new
	);
	public static final DeferredItem<Item> TOXENIUM_LEGGINGS = ITEMS.registerItem(
			"toxenium_leggings",
			Toxenium.Leggings::new
	);
	public static final DeferredItem<Item> TOXENIUM_BOOTS = ITEMS.registerItem(
			"toxenium_boots",
			Toxenium.Boots::new
	);
	public static final DeferredItem<Item> TIMER_CLOCK = ITEMS.registerItem(
			"timer_clock",
			TimerClock::new
	);
	public static final DeferredItem<Item> TIME_CLOCK = ITEMS.registerItem(
			"time_clock",
			TimeClock::new
	);
	public static final DeferredItem<Item> HEROBRINES_REALM = ITEMS.registerItem(
			"herobrines_realm",
			HerobrinesRealm::new
	);
	public static final DeferredItem<Item> UNDERWORLD = ITEMS.registerItem(
			"underworld",
			Underworld::new
	);
	public static final DeferredItem<Item> GREEN_GEMSTONE = ITEMS.registerItem(
			"green_gemstone",
			GreenGemstone::new
	);
	public static final DeferredItem<Item> RUNE_STONE = ITEMS.registerItem(
			"rune_stone",
			RuneStone::new
	);

	public static final DeferredItem<Item> HEROBRINE_RELIC = ITEMS.registerItem(
			"herobrine_relic",
			HerobrineRelicItem::new
	);

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
