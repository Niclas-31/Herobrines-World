package de.niclasl.herobrines_world.block;

import de.niclasl.herobrines_world.block.custom.*;
import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import de.niclasl.herobrines_world.HerobrinesWorld;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS =
			DeferredRegister.createBlocks(HerobrinesWorld.MODID);

	public static final DeferredBlock<Block> LUMBERJACK_TABLE = registerBlock("lumberjack_table",
			(properties) -> new Block(properties.sound(SoundType.WOOD).strength(10f, 30f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> SIGNAL = registerBlock("signal",
			(properties) -> new Signal(properties.mapColor(MapColor.TERRACOTTA_ORANGE).lightLevel(litBlockEmission()).strength(0.3F).sound(SoundType.GLASS).isValidSpawn((blockState, blockGetter, blockPos, entityType) -> ModBlocks.always())));

	public static final DeferredBlock<Block> ASH_BLOCK = registerBlock("ash_block",
			(properties) -> new ColoredFallingBlock(new ColorRGBA(14406560), properties.sound(SoundType.SAND).strength(1f, 10f)));

	public static final DeferredBlock<Block> HEROBRINES_REALM_PORTAL = registerBlock("herobrines_realm_portal",
			(properties) -> new HerobrinesRealmPortal(properties.noCollision().randomTicks().pushReaction(PushReaction.BLOCK).strength(-1.0F).sound(SoundType.GLASS).lightLevel(s -> 0).noLootTable()));

	public static final DeferredBlock<Block> UNDERWORLD_PORTAL = registerBlock("underworld_portal",
			(properties) -> new UnderworldPortal(properties.noCollision().randomTicks().pushReaction(PushReaction.BLOCK).strength(-1.0F).sound(SoundType.GLASS).lightLevel(s -> 0).noLootTable()));

	public static final DeferredBlock<Block> BLUE_SANDSTONE = registerBlock("blue_sandstone",
			(properties) -> new Block(properties.strength(0.8f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_SANDSTONE_STAIRS = registerBlock("blue_sandstone_stairs",
			(properties) -> new StairBlock(Blocks.AIR.defaultBlockState(), properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_SANDSTONE_SLAB = registerBlock("blue_sandstone_slab",
			(properties) -> new SlabBlock(properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_SANDSTONE_WALL = registerBlock("blue_sandstone_wall",
			(properties) -> new WallBlock(properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false).forceSolidOn()));

	public static final DeferredBlock<Block> BLUE_CHISELED_SANDSTONE = registerBlock("blue_chiseled_sandstone",
			(properties) -> new Block(properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_SMOOTH_SANDSTONE = registerBlock("blue_smooth_sandstone",
			(properties) -> new Block(properties.strength(2f, 6f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_SMOOTH_SANDSTONE_STAIRS = registerBlock("blue_smooth_sandstone_stairs",
			(properties) -> new StairBlock(Blocks.AIR.defaultBlockState(), properties.strength(2f, 6f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_SMOOTH_SANDSTONE_SLAB = registerBlock("blue_smooth_sandstone_slab",
			(properties) -> new SlabBlock(properties.strength(2f, 6f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_CUT_SANDSTONE = registerBlock("blue_cut_sandstone",
			(properties) -> new Block(properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> BLUE_CUT_SANDSTONE_SLAB = registerBlock("blue_cut_sandstone_slab",
			(properties) -> new SlabBlock(properties.strength(0.8f, 0.8f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> HEROBRINE_BLOCK = registerBlock("herobrine_block",
			(properties) -> new Block(properties.sound(SoundType.METAL).strength(5f, 6f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.CHIME)));

	public static final DeferredBlock<Block> CURSED_STONE = registerBlock("cursed_stone",
			(properties) -> new Block(properties.strength(1f, 10f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> ABYSSAL_BLOCK = registerBlock("abyssal_block",
			(properties) -> new Block(properties.strength(5f, 12f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> FROZEN_HEART_ORE = registerBlock("frozen_heart_ore",
			(properties) -> new Block(properties.strength(3.5f, 13f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> DEEPSLATE_FROZEN_HEART_ORE = registerBlock("deepslate_frozen_heart_ore",
			(properties) -> new Block(properties.sound(SoundType.DEEPSLATE).strength(4f, 16f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> GREEN_ORE = registerBlock("green_ore",
			(properties) -> new Block(properties.strength(3f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> DEEPSLATE_GREEN_ORE = registerBlock("deepslate_green_ore",
			(properties) -> new Block(properties.sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> ASH_ORE = registerBlock("ash_ore",
			(properties) -> new Block(properties.strength(3f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> DEEPSLATE_ASH_ORE = registerBlock("deepslate_ash_ore",
			(properties) -> new Block(properties.sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> HEROBRINE_ORE = registerBlock("herobrine_ore",
			(properties) -> new Block(properties.strength(3f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> DEEPSLATE_HEROBRINE_ORE = registerBlock("deepslate_herobrine_ore",
			(properties) -> new Block(properties.sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> TOXENIUM_ORE = registerBlock("toxenium_ore",
			(properties) -> new Block(properties.sound(SoundType.ANCIENT_DEBRIS).strength(30f, 1200f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<Block> DELAYER = registerBlock("delayer",
			(properties) -> new Delayer(properties.instabreak()));

	public static final DeferredBlock<Block> LOGIC_GATE_BLOCK = registerBlock("logic_gate_block",
			(properties) -> new LogicGateBlock(properties.instabreak()));

	public static final DeferredBlock<Block> SIGNAL_AMPLIFIER = registerBlock("signal_amplifier",
			(properties) -> new SignalAmplifierBlock(properties.instabreak()));

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

	private static ToIntFunction<BlockState> litBlockEmission() {
		return state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0;
	}
}
