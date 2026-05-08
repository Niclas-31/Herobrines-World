package de.niclasl.herobrines_world.registries.item;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.registries.block.ModBlocks;
import de.niclasl.herobrines_world.registries.block.custom.AutoFarmerBlock;
import de.niclasl.herobrines_world.registries.block.custom.Signal;
import de.niclasl.herobrines_world.registries.block.properties.ColorProperty;
import de.niclasl.herobrines_world.registries.block.properties.FarmerMode;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HerobrinesWorld.MODID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HEROBRINE_BUILDING_BLOCKS =
			CREATIVE_MODE_TAB.register("herobrine_building_blocks",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModBlocks.BLUE_SANDSTONE.get()))
							.title(Component.translatable("item_group.herobrines_world.herobrine_building_blocks"))
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

	public static final Supplier<CreativeModeTab> HEROBRINE_COLORED_BLOCKS =
			CREATIVE_MODE_TAB.register("herobrine_colored_blocks",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModBlocks.SIGNAL.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_building_blocks"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_colored_blocks"))
							.displayItems((parameters, tabData) -> {
								for (ColorProperty color : ColorProperty.values()) {
									tabData.accept(Signal.setModeOnStack(new ItemStack(ModBlocks.SIGNAL.get()), color));
								}
							}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_NATURAL_BLOCKS =
			CREATIVE_MODE_TAB.register("herobrine_natural_blocks",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModBlocks.ASH_BLOCK.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_colored_blocks"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_natural_blocks"))
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
								tabData.accept(ModBlocks.PLATINE_ORE);
								tabData.accept(ModBlocks.DEEPSLATE_PLATIN_ORE);
							}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_FUNCTIONAL_BLOCKS =
			CREATIVE_MODE_TAB.register("herobrine_functional_blocks",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModBlocks.LUMBERJACK_TABLE.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_natural_blocks"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_functional_blocks"))
							.displayItems((parameters, tabData) -> {
								tabData.accept(ModBlocks.LUMBERJACK_TABLE);
								for (FarmerMode mode : FarmerMode.values()) {
									tabData.accept(AutoFarmerBlock.setModeOnStack(new ItemStack(ModBlocks.AUTO_FARMER), mode));
								}
							}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_REDSTONE_BLOCKS =
			CREATIVE_MODE_TAB.register("herobrine_redstone_blocks",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModBlocks.SIGNAL.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_functional_blocks"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_redstone_blocks"))
							.displayItems((parameters, tabData) -> {
								for (ColorProperty color : ColorProperty.values()) {
									tabData.accept(Signal.setModeOnStack(new ItemStack(ModBlocks.SIGNAL), color));
								}
								tabData.accept(ModBlocks.DELAYER);
								tabData.accept(ModBlocks.LOGIC_GATE_BLOCK);
								for (FarmerMode mode : FarmerMode.values()) {
									tabData.accept(AutoFarmerBlock.setModeOnStack(new ItemStack(ModBlocks.AUTO_FARMER), mode));
								}
								tabData.accept(ModItems.BATTERY);
								tabData.accept(ModBlocks.BATTERY_CHARGER);
								tabData.accept(ModItems.SMART_CHIP_MK1.get());
								tabData.accept(ModItems.SMART_CHIP_MK2.get());
								tabData.accept(ModItems.SMART_CHIP_MK3.get());
								tabData.accept(ModItems.SMART_CHIP_CASE);
							}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_TOOLS_AND_UTILITIES =
			CREATIVE_MODE_TAB.register("herobrine_tools_and_utilities",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModItems.HEROBRINE_PICKAXE.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_redstone_blocks"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_tools_and_utilities"))
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
								tabData.accept(ModItems.PLATIN_SHOVEL);
								tabData.accept(ModItems.PLATIN_PICKAXE);
								tabData.accept(ModItems.PLATIN_AXE);
								tabData.accept(ModItems.PLATIN_HOE);
								tabData.accept(ModItems.TIME_CLOCK);
								tabData.accept(ModItems.HEROBRINES_REALM);
								tabData.accept(ModItems.UNDERWORLD);
							}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_COMBAT =
			CREATIVE_MODE_TAB.register("herobrine_combat",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModItems.PLATIN_SWORD.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_tools_and_utilities"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_combat"))
							.displayItems((parameters, tabData) -> {
								tabData.accept(ModItems.NATURE_SWORD);
								tabData.accept(ModItems.FIRE_SWORD);
								tabData.accept(ModItems.HEROBRINE_SWORD);
								tabData.accept(ModItems.PLATIN_SWORD);
								tabData.accept(ModItems.NATURE_AXE);
								tabData.accept(ModItems.FIRE_AXE);
								tabData.accept(ModItems.HEROBRINE_AXE);
								tabData.accept(ModItems.PLATIN_AXE);
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
								tabData.accept(ModItems.PLATIN_HELMET);
								tabData.accept(ModItems.PLATIN_CHESTPLATE);
								tabData.accept(ModItems.PLATIN_LEGGINGS);
								tabData.accept(ModItems.PLATIN_BOOTS);
							}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_INGREDIENTS =
			CREATIVE_MODE_TAB.register("herobrine_ingredients",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModItems.ASH_INGOT.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_combat"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_ingredients"))
							.displayItems((parameters, tabData) -> {
								tabData.accept(ModItems.HEROBRINE_DIAMOND);
								tabData.accept(ModItems.ASH);
								tabData.accept(ModItems.ASH_INGOT);
								tabData.accept(ModItems.FROZEN_HEART);
								tabData.accept(ModItems.PLATIN_INGOT);
								tabData.accept(ModItems.GREEN_GEMSTONE);
								tabData.accept(ModItems.RUNE_STONE);
							}).build());

	public static final Supplier<CreativeModeTab> HEROBRINE_SPAWN_EGGS =
			CREATIVE_MODE_TAB.register("herobrine_spawn_eggs",
					() -> CreativeModeTab.builder()
							.icon(() -> new ItemStack(ModItems.GOOD_HEROBRINE_SPAWN_EGG.get()))
							.withTabsBefore(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine_ingredients"))
							.title(Component.translatable("item_group.herobrines_world.herobrine_spawn_eggs"))
							.displayItems((parameters, tabData) -> {
								tabData.accept(ModItems.HEROBRINE_BOSS_SPAWN_EGG);
								tabData.accept(ModItems.NICLASL_SPAWN_EGG);
								tabData.accept(ModItems.ENTITY_303_SPAWN_EGG);
								tabData.accept(ModItems.GOOD_HEROBRINE_SPAWN_EGG);
								tabData.accept(ModItems.BAD_HEROBRINE_SPAWN_EGG);
								tabData.accept(ModItems.CHRISTMAS_NICLASL_SPAWN_EGG);
							}).build());

	// -------------------- Registrierung --------------------
	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TAB.register(eventBus);
	}
}