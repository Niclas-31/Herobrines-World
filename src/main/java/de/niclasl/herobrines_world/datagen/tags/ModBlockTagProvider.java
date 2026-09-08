package de.niclasl.herobrines_world.datagen.tags;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import de.niclasl.herobrines_world.common.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, HerobrinesWorld.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModBlocks.CURSED_STONE.get()))
                .add(ModBlocks.getRK(ModBlocks.ABYSSAL_BLOCK.get()))
                .add(ModBlocks.getRK(ModBlocks.FROZEN_HEART_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.GREEN_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_GREEN_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.ASH_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_ASH_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.HEROBRINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_HEROBRINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SANDSTONE.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SANDSTONE_STAIRS.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SANDSTONE_SLAB.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SANDSTONE_WALL.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_CHISELED_SANDSTONE.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SMOOTH_SANDSTONE.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_CUT_SANDSTONE.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get()))
                .add(ModBlocks.getRK(ModBlocks.HEROBRINE_BLOCK.get()))
                .add(ModBlocks.getRK(ModBlocks.AUTO_FARMER.get()))
                .add(ModBlocks.getRK(ModBlocks.BATTERY_CHARGER.get()))
                .add(ModBlocks.getRK(ModBlocks.PLATINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_PLATIN_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.STORAGE_CONTROLLER.get()))
                .add(ModBlocks.getRK(ModBlocks.CARD_READER.get()));

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.getRK(ModBlocks.ASH_BLOCK.get()));

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.getRK(ModBlocks.LUMBERJACK_TABLE.get()));

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.getRK(ModBlocks.FROZEN_HEART_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.GREEN_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_GREEN_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.AUTO_FARMER.get()))
                .add(ModBlocks.getRK(ModBlocks.BATTERY_CHARGER.get()));

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.getRK(ModBlocks.HEROBRINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_HEROBRINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.HEROBRINE_BLOCK.get()))
                .add(ModBlocks.getRK(ModBlocks.ASH_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_ASH_ORE.get()));


        tag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .add(ModBlocks.getRK(ModBlocks.PLATINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_PLATIN_ORE.get()));
        tag(ModTags.Blocks.NEEDS_ASH_TOOLS);
        tag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .add(ModBlocks.getRK(ModBlocks.STORAGE_CONTROLLER.get()))
                .add(ModBlocks.getRK(ModBlocks.CARD_READER.get()));
        tag(ModTags.Blocks.NEEDS_PLATIN_TOOL);
        tag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_NATURE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.NEEDS_ASH_TOOLS)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ASH_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_FIRE_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_PLATIN_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_HEROBRINE_TOOL);

        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL);
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL);
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL);
        tag(BlockTags.INCORRECT_FOR_COPPER_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL);
        tag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_PLATIN_TOOL);
        tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL);

        tag(BlockTags.SLABS)
                .add(ModBlocks.getRK(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SANDSTONE_SLAB.get()));

        tag(BlockTags.STAIRS)
                .add(ModBlocks.getRK(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get()))
                .add(ModBlocks.getRK(ModBlocks.BLUE_SANDSTONE_STAIRS.get()));

        tag(BlockTags.WALLS)
                .add(ModBlocks.getRK(ModBlocks.BLUE_SANDSTONE_WALL.get()));

        tag(ModTags.Blocks.ANCIENT_DEBRIS)
                .add(BlockItemIds.ANCIENT_DEBRIS.block());

        tag(ModTags.Blocks.ASH)
                .add(ModBlocks.getRK(ModBlocks.ASH_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_ASH_ORE.get()));

        tag(ModTags.Blocks.COAL)
                .add(BlockItemIds.COAL_ORE.block())
                .add(BlockItemIds.DEEPSLATE_COAL_ORE.block());

        tag(ModTags.Blocks.COPPER)
                .add(BlockItemIds.COPPER_ORE.block())
                .add(BlockItemIds.DEEPSLATE_COPPER_ORE.block());

        tag(ModTags.Blocks.DIAMOND)
                .add(BlockItemIds.DIAMOND_ORE.block())
                .add(BlockItemIds.DEEPSLATE_DIAMOND_ORE.block());

        tag(ModTags.Blocks.EMERALD)
                .add(BlockItemIds.EMERALD_ORE.block())
                .add(BlockItemIds.DEEPSLATE_EMERALD_ORE.block());

        tag(ModTags.Blocks.FROZEN)
                .add(ModBlocks.getRK(ModBlocks.FROZEN_HEART_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get()));

        tag(ModTags.Blocks.GOLD)
                .add(BlockItemIds.GOLD_ORE.block())
                .add(BlockItemIds.DEEPSLATE_GOLD_ORE.block());

        tag(ModTags.Blocks.GREEN)
                .add(ModBlocks.getRK(ModBlocks.GREEN_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_GREEN_ORE.get()));

        tag(ModTags.Blocks.HEROBRINE)
                .add(ModBlocks.getRK(ModBlocks.HEROBRINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_HEROBRINE_ORE.get()));

        tag(ModTags.Blocks.IRON)
                .add(BlockItemIds.IRON_ORE.block())
                .add(BlockItemIds.DEEPSLATE_IRON_ORE.block());

        tag(ModTags.Blocks.LAPIS)
                .add(BlockItemIds.LAPIS_ORE.block())
                .add(BlockItemIds.DEEPSLATE_LAPIS_ORE.block());

        tag(ModTags.Blocks.PLATIN)
                .add(ModBlocks.getRK(ModBlocks.PLATINE_ORE.get()))
                .add(ModBlocks.getRK(ModBlocks.DEEPSLATE_PLATIN_ORE.get()));

        tag(ModTags.Blocks.QUARTZ)
                .add(BlockItemIds.NETHER_QUARTZ_ORE.block());

        tag(ModTags.Blocks.REDSTONE)
                .add(BlockItemIds.REDSTONE_ORE.block())
                .add(BlockItemIds.DEEPSLATE_REDSTONE_ORE.block());
    }
}