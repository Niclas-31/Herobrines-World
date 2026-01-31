package de.niclasl.herobrines_world.block;

import de.niclasl.herobrines_world.block.custom.*;
import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import de.niclasl.herobrines_world.HerobrinesWorld;

import java.util.function.Function;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS =
			DeferredRegister.createBlocks(HerobrinesWorld.MODID);

	public static final DeferredBlock<Block> RED_ENCHANTMENT_TABLE = registerBlock("red_enchantment_table",
			RedEnchantmentTable::new);

	public static final DeferredBlock<Block> LUMBERJACK_TABLE = registerBlock("lumberjack_table",
			LumberjackTable::new);

	public static final DeferredBlock<Block> SIGNAL = registerBlock("signal", Signal::new);

	public static final DeferredBlock<Block> ASH_BLOCK = registerBlock("ash_block", AshBlock::new);

	public static final DeferredBlock<Block> HEROBRINES_REALM_PORTAL = registerBlock("herobrines_realm_portal",
			HerobrinesRealmPortal::new);

	public static final DeferredBlock<Block> UNDERWORLD_PORTAL = registerBlock("underworld_portal",
			UnderworldPortal::new);

	public static final DeferredBlock<Block> BLUE_SANDSTONE = registerBlock("blue_sandstone", BlueSandstone::new);

	public static final DeferredBlock<Block> BLUE_SANDSTONE_STAIRS = registerBlock("blue_sandstone_stairs",
			BlueSandstoneStairs::new);

	public static final DeferredBlock<Block> BLUE_SANDSTONE_SLAB = registerBlock("blue_sandstone_slab",
			BlueSandstoneSlab::new);

	public static final DeferredBlock<Block> BLUE_SANDSTONE_WALL = registerBlock("blue_sandstone_wall",
			BlueSandstoneWall::new);

	public static final DeferredBlock<Block> BLUE_CHISELED_SANDSTONE = registerBlock("blue_chiseled_sandstone",
			BlueChiseledSandstone::new);

	public static final DeferredBlock<Block> BLUE_SMOOTH_SANDSTONE = registerBlock("blue_smooth_sandstone",
			BlueSmoothSandstone::new);

	public static final DeferredBlock<Block> BLUE_SMOOTH_SANDSTONE_STAIRS = registerBlock("blue_smooth_sandstone_stairs",
			BlueSmoothSandstoneStairs::new);

	public static final DeferredBlock<Block> BLUE_SMOOTH_SANDSTONE_SLAB = registerBlock("blue_smooth_sandstone_slab",
			BlueSmoothSandstoneSlab::new);

	public static final DeferredBlock<Block> BLUE_CUT_SANDSTONE = registerBlock("blue_cut_sandstone",
			BlueCutSandstone::new);

	public static final DeferredBlock<Block> BLUE_CUT_SANDSTONE_SLAB = registerBlock("blue_cut_sandstone_slab",
			BlueCutSandstoneSlab::new);

	public static final DeferredBlock<Block> HEROBRINE_BLOCK = registerBlock("herobrine_block",
			HerobrineBlock::new);

	public static final DeferredBlock<Block> CURSED_STONE = registerBlock("cursed_stone", CursedStone::new);

	public static final DeferredBlock<Block> ABYSSAL_BLOCK = registerBlock("abyssal_block", AbyssalBlock::new);

	public static final DeferredBlock<Block> FROZEN_HEART_ORE = registerBlock("frozen_heart_ore",
			FrozenHeartOre::new);

	public static final DeferredBlock<Block> DEEPSLATE_FROZEN_HEART_ORE = registerBlock("deepslate_frozen_heart_ore",
			DeepslateFrozenHeartOre::new);

	public static final DeferredBlock<Block> GREEN_ORE = registerBlock("green_ore", GreenOre::new);

	public static final DeferredBlock<Block> DEEPSLATE_GREEN_ORE = registerBlock("deepslate_green_ore",
			DeepslateGreenOre::new);

	public static final DeferredBlock<Block> ASH_ORE = registerBlock("ash_ore", AshOre::new);

	public static final DeferredBlock<Block> DEEPSLATE_ASH_ORE = registerBlock("deepslate_ash_ore",
			DeepslateAshOre::new);

	public static final DeferredBlock<Block> HEROBRINE_ORE = registerBlock("herobrine_ore",
			HerobrineOre::new);

	public static final DeferredBlock<Block> DEEPSLATE_HEROBRINE_ORE = registerBlock("deepslate_herobrine_ore",
			DeepslateHerobrineOre::new);

	public static final DeferredBlock<Block> TOXENIUM_ORE = registerBlock("toxenium_ore",
			ToxeniumOre::new);

	public static final DeferredBlock<Block> DELAYER = registerBlock("delayer",
			Delayer::new);

	public static final DeferredBlock<Block> LOGIC_GATE_BLOCK = registerBlock("logic_gate_block",
			LogicGateBlock::new);
	
	private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
		DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
		ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}


	public static boolean always() {
        return true;
    }

}



