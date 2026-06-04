package de.niclasl.herobrines_world.datagen.loottables;

import de.niclasl.herobrines_world.common.registries.block.ModBlocks;
import de.niclasl.herobrines_world.common.registries.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.LUMBERJACK_TABLE.get());
        dropSelf(ModBlocks.SIGNAL.get());
        dropSelf(ModBlocks.ASH_BLOCK.get());
        dropSelf(ModBlocks.BLUE_SANDSTONE.get());
        dropSelf(ModBlocks.BLUE_SANDSTONE_STAIRS.get());
        dropSelf(ModBlocks.BLUE_SANDSTONE_SLAB.get());
        dropSelf(ModBlocks.BLUE_SANDSTONE_WALL.get());
        dropSelf(ModBlocks.BLUE_CHISELED_SANDSTONE.get());
        dropSelf(ModBlocks.BLUE_SMOOTH_SANDSTONE.get());
        dropSelf(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get());
        dropSelf(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get());
        dropSelf(ModBlocks.BLUE_CUT_SANDSTONE.get());
        dropSelf(ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get());
        dropSelf(ModBlocks.HEROBRINE_BLOCK.get());
        dropSelf(ModBlocks.CURSED_STONE.get());
        dropSelf(ModBlocks.ABYSSAL_BLOCK.get());

        add(ModBlocks.FROZEN_HEART_ORE.get(), block -> createOreDrop(block, ModItems.FROZEN_HEART.get()));
        add(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get(), block -> createOreDrop(block, ModItems.FROZEN_HEART.get()));
        add(ModBlocks.GREEN_ORE.get(), block -> createOreDrop(block, ModItems.GREEN_GEMSTONE.get()));
        add(ModBlocks.DEEPSLATE_GREEN_ORE.get(), block -> createOreDrop(block, ModItems.GREEN_GEMSTONE.get()));
        add(ModBlocks.ASH_ORE.get(), block -> createOreDrop(block, ModItems.ASH_INGOT.get()));
        add(ModBlocks.DEEPSLATE_ASH_ORE.get(), block -> createOreDrop(block, ModItems.ASH_INGOT.get()));
        add(ModBlocks.HEROBRINE_ORE.get(), block -> createOreDrop(block, ModItems.HEROBRINE_DIAMOND.get()));
        add(ModBlocks.DEEPSLATE_HEROBRINE_ORE.get(), block -> createOreDrop(block, ModItems.HEROBRINE_DIAMOND.get()));
        add(ModBlocks.PLATINE_ORE.get(), block -> createOreDrop(block, ModItems.PLATIN_INGOT.get()));
        add(ModBlocks.DEEPSLATE_PLATIN_ORE.get(), block -> createOreDrop(block, ModItems.PLATIN_INGOT.get()));

        dropSelf(ModBlocks.DELAYER.get());
        dropSelf(ModBlocks.LOGIC_GATE_BLOCK.get());
        dropSelf(ModBlocks.AUTO_FARMER.get());
        dropSelf(ModBlocks.BATTERY_CHARGER.get());
        dropSelf(ModBlocks.STORAGE_CONTROLLER.get());
        dropSelf(ModBlocks.CARD_READER.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}