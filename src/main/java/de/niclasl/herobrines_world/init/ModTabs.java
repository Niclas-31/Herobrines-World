package de.niclasl.herobrines_world.init;

import de.niclasl.herobrines_world.block.ModBlocks;
import de.niclasl.herobrines_world.block.custom.Signal;
import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.HerobrinesWorld;

import java.util.function.Supplier;

public class ModTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HerobrinesWorld.MODID);

	public static final Supplier<CreativeModeTab> HEROBRINES_SPAWN_EGGS = CREATIVE_MODE_TAB.register("herobrines_spawn_eggs",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.GOOD_HEROBRINE_SPAWN_EGG.get()))
					.title(Component.translatable("item_group.herobrines_world.herobrines_spawn_eggs"))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModItems.HEROBRINE_BOSS_SPAWN_EGG);
				tabData.accept(ModItems.NICLASL_SPAWN_EGG);
				tabData.accept(ModItems.ENTITY_303_SPAWN_EGG);
				tabData.accept(ModItems.GOOD_HEROBRINE_SPAWN_EGG);
				tabData.accept(ModItems.BAD_HEROBRINE_SPAWN_EGG);
				tabData.accept(ModItems.CHRISTMAS_NICLASL_SPAWN_EGG);
			}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_INGREDIENTS = CREATIVE_MODE_TAB.register("herobrines_ingredients",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ASH_INGOT.get()))
					.withTabsBefore(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_spawn_eggs"))
					.title(Component.translatable("item_group.herobrines_world.herobrines_ingredients"))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModItems.HEROBRINE_DIAMOND);
				tabData.accept(ModItems.ASH);
				tabData.accept(ModItems.ASH_INGOT);
				tabData.accept(ModItems.FROZEN_HEART);
				tabData.accept(ModItems.FROZEN_FRAGMENT_LEFT);
				tabData.accept(ModItems.FROZEN_FRAGMENT_RIGHT);
				tabData.accept(ModItems.TOXENIUM_UPGRADE_SMITHING_TEMPLATE);
				tabData.accept(ModItems.TOXENIUM_INGOT);
				tabData.accept(ModItems.GREEN_GEMSTONE);
				tabData.accept(ModItems.RUNE_STONE);
			}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_FUNCTIONAL_BLOCKS = CREATIVE_MODE_TAB.register("herobrines_functional_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.LUMBERJACK_TABLE.get()))
					.withTabsBefore(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_ingredients"))
					.title(Component.translatable("item_group.herobrines_world.herobrines_functional_blocks"))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModBlocks.RED_ENCHANTMENT_TABLE);
				tabData.accept(ModBlocks.LUMBERJACK_TABLE);
			}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_TOOLS = CREATIVE_MODE_TAB.register("herobrines_tools",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.HEROBRINE_PICKAXE.get()))
					.withTabsBefore(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_functional_blocks"))
					.title(Component.translatable("item_group.herobrines_world.herobrines_tools"))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModItems.ASH_PICKAXE);
				tabData.accept(ModItems.ORE_DETECTOR);
				tabData.accept(ModItems.NATURE_SHOVEL);
				tabData.accept(ModItems.NATURE_PICKAXE);
				tabData.accept(ModItems.NATURE_AXE);
				tabData.accept(ModItems.NATURE_HOE);
				tabData.accept(ModItems.FIRE_SHOVEL);
				tabData.accept(ModItems.FIRE_PICKAXE);
				tabData.accept(ModItems.FIRE_AXE);
				tabData.accept(ModItems.FIRE_HOE);
				tabData.accept(ModItems.HEROBRINE_SHOVEL);
				tabData.accept(ModItems.HEROBRINE_PICKAXE);
				tabData.accept(ModItems.HEROBRINE_AXE);
				tabData.accept(ModItems.HEROBRINE_HOE);
				tabData.accept(ModItems.TOXENIUM_SHOVEL);
				tabData.accept(ModItems.TOXENIUM_PICKAXE);
				tabData.accept(ModItems.TOXENIUM_AXE);
				tabData.accept(ModItems.TOXENIUM_HOE);
				tabData.accept(ModItems.TIMER_CLOCK);
				tabData.accept(ModItems.TIME_CLOCK);
				tabData.accept(ModItems.HEROBRINES_REALM);
				tabData.accept(ModItems.UNDERWORLD);
			}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_REDSTONE_BLOCKS = CREATIVE_MODE_TAB.register("herobrines_redstone_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SIGNAL.get())).withTabsBefore(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_tools"))
					.title(Component.translatable("item_group.herobrines_world.herobrines_redstone_blocks"))
					.displayItems((parameters, tabData) -> {
						for(Signal.ColorProperty color : Signal.ColorProperty.values()) {
							tabData.accept(Signal.setModeOnStack(new ItemStack(ModBlocks.SIGNAL), color));
						}
						tabData.accept(ModBlocks.DELAYER);
						tabData.accept(ModBlocks.WIRELESS_SENDER_BLOCK);
						tabData.accept(ModBlocks.WIRELESS_RECEIVER_BLOCK);
			}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_COMBAT = CREATIVE_MODE_TAB.register("herobrines_combat",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TOXENIUM_SWORD.get()))
					.withTabsBefore(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_redstone_blocks"))
					.title(Component.translatable("item_group.herobrines_world.herobrines_combat"))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModItems.NATURE_SWORD);
				tabData.accept(ModItems.FIRE_SWORD);
				tabData.accept(ModItems.HEROBRINE_SWORD);
				tabData.accept(ModItems.TOXENIUM_SWORD);
				tabData.accept(ModItems.NATURE_AXE);
				tabData.accept(ModItems.FIRE_AXE);
				tabData.accept(ModItems.HEROBRINE_AXE);
				tabData.accept(ModItems.TOXENIUM_AXE);
				tabData.accept(ModItems.NATURE_HELMET);
				tabData.accept(ModItems.NATURE_CHESTPLATE);
				tabData.accept(ModItems.NATURE_LEGGINGS);
				tabData.accept(ModItems.NATURE_BOOTS);
				tabData.accept(ModItems.FIRE_HELMET);
				tabData.accept(ModItems.FIRE_CHESTPLATE);
				tabData.accept(ModItems.FIRE_LEGGINGS);
				tabData.accept(ModItems.FIRE_BOOTS);
				tabData.accept(ModItems.HEROBRINE_HELMET);
				tabData.accept(ModItems.HEROBRINE_CHESTPLATE);
				tabData.accept(ModItems.HEROBRINE_LEGGINGS);
				tabData.accept(ModItems.HEROBRINE_BOOTS);
				tabData.accept(ModItems.TOXENIUM_HELMET);
				tabData.accept(ModItems.TOXENIUM_CHESTPLATE);
				tabData.accept(ModItems.TOXENIUM_LEGGINGS);
				tabData.accept(ModItems.TOXENIUM_BOOTS);
			}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_NATURAL_BLOCKS = CREATIVE_MODE_TAB.register("herobrines_natural_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ASH_BLOCK.get()))
					.withTabsBefore(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_combat"))
					.title(Component.translatable("item_group.herobrines_world.herobrines_natural_blocks"))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModBlocks.ASH_BLOCK);
				tabData.accept(ModBlocks.BLUE_SANDSTONE);
				tabData.accept(ModBlocks.CURSED_STONE);
				tabData.accept(ModBlocks.ABYSSAL_BLOCK);
				tabData.accept(ModBlocks.FROZEN_HEART_ORE);
				tabData.accept(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE);
				tabData.accept(ModBlocks.GREEN_ORE);
				tabData.accept(ModBlocks.DEEPSLATE_GREEN_ORE);
				tabData.accept(ModBlocks.ASH_ORE);
				tabData.accept(ModBlocks.DEEPSLATE_ASH_ORE);
				tabData.accept(ModBlocks.HEROBRINE_ORE);
				tabData.accept(ModBlocks.DEEPSLATE_HEROBRINE_ORE);
				tabData.accept(ModBlocks.TOXENIUM_ORE);
			}).build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HEROBRINE_BUILDING_BLOCKS = CREATIVE_MODE_TAB.register("herobrines_building_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.BLUE_SANDSTONE.get()))
					.withTabsBefore(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_natural_blocks"))
					.title(Component.translatable("item_group.herobrines_world.herobrines_building_blocks"))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModBlocks.BLUE_SANDSTONE);
				tabData.accept(ModBlocks.BLUE_SANDSTONE_STAIRS);
				tabData.accept(ModBlocks.BLUE_SANDSTONE_SLAB);
				tabData.accept(ModBlocks.BLUE_SANDSTONE_WALL);
				tabData.accept(ModBlocks.BLUE_CHISELED_SANDSTONE);
				tabData.accept(ModBlocks.BLUE_SMOOTH_SANDSTONE);
				tabData.accept(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS);
				tabData.accept(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB);
				tabData.accept(ModBlocks.BLUE_CUT_SANDSTONE);
				tabData.accept(ModBlocks.BLUE_CUT_SANDSTONE_SLAB);
				tabData.accept(ModBlocks.HEROBRINE_BLOCK);
			}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TAB.register(eventBus);
	}
}
