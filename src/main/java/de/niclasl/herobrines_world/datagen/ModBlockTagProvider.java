package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.ModBlocks;
import de.niclasl.herobrines_world.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, HerobrinesWorld.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CURSED_STONE.get())
                .add(ModBlocks.ABYSSAL_BLOCK.get())
                .add(ModBlocks.FROZEN_HEART_ORE.get())
                .add(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get())
                .add(ModBlocks.GREEN_ORE.get())
                .add(ModBlocks.DEEPSLATE_GREEN_ORE.get())
                .add(ModBlocks.ASH_ORE.get())
                .add(ModBlocks.DEEPSLATE_ASH_ORE.get())
                .add(ModBlocks.HEROBRINE_ORE.get())
                .add(ModBlocks.DEEPSLATE_HEROBRINE_ORE.get())
                .add(ModBlocks.TOXENIUM_ORE.get())
                .add(ModBlocks.BLUE_SANDSTONE.get())
                .add(ModBlocks.BLUE_SANDSTONE_STAIRS.get())
                .add(ModBlocks.BLUE_SANDSTONE_SLAB.get())
                .add(ModBlocks.BLUE_SANDSTONE_WALL.get())
                .add(ModBlocks.BLUE_CHISELED_SANDSTONE.get())
                .add(ModBlocks.BLUE_SMOOTH_SANDSTONE.get())
                .add(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get())
                .add(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get())
                .add(ModBlocks.BLUE_CUT_SANDSTONE.get())
                .add(ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get())
                .add(ModBlocks.HEROBRINE_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.ASH_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.LUMBERJACK_TABLE.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.FROZEN_HEART_ORE.get())
                .add(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get())
                .add(ModBlocks.GREEN_ORE.get())
                .add(ModBlocks.DEEPSLATE_GREEN_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.HEROBRINE_ORE.get())
                .add(ModBlocks.DEEPSLATE_HEROBRINE_ORE.get())
                .add(ModBlocks.HEROBRINE_BLOCK.get())
                .add(ModBlocks.ASH_ORE.get())
                .add(ModBlocks.DEEPSLATE_ASH_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.TOXENIUM_ORE.get());

        tag(ModTags.Blocks.NEEDS_NATURE_TOOL);
        tag(ModTags.Blocks.NEEDS_FIRE_TOOL);
        tag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL);
        tag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_TOXENIUM_TOOL);

        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);
        tag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_NATURE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_FIRE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_HEROBRINE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_TOXENIUM_TOOL);

        tag(BlockTags.SLABS)
                .add(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get())
                .add(ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get())
                .add(ModBlocks.BLUE_SANDSTONE_SLAB.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get())
                .add(ModBlocks.BLUE_SANDSTONE_STAIRS.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.BLUE_SANDSTONE_WALL.get());
    }
}