package de.niclasl.herobrines_world.util;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_NATURE_TOOL = createTag("needs_nature_tool");
        public static final TagKey<Block> NEEDS_FIRE_TOOL = createTag("needs_fire_tool");
        public static final TagKey<Block> NEEDS_HEROBRINE_TOOL = createTag("needs_herobrine_tool");
        public static final TagKey<Block> NEEDS_TOXENIUM_TOOL = createTag("needs_toxium_tool");

        public static final TagKey<Block> INCORRECT_FOR_NATURE_TOOL = createTag("incorrect_for_nature_tool");
        public static final TagKey<Block> INCORRECT_FOR_FIRE_TOOL = createTag("incorrect_for_fire_tool");
        public static final TagKey<Block> INCORRECT_FOR_HEROBRINE_TOOL = createTag("incorrect_for_herobrine_tool");
        public static final TagKey<Block> INCORRECT_FOR_TOXENIUM_TOOL = createTag("incorrect_for_toxium_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> NATURE_REPAIRABLE = createTag("nature_repairable");
        public static final TagKey<Item> FIRE_REPAIRABLE = createTag("fire_repairable");
        public static final TagKey<Item> HEROBRINE_REPAIRABLE = createTag("herobrine_repairable");
        public static final TagKey<Item> TOXENIUM_REPAIRABLE = createTag("toxenium_repairable");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, name));
        }
    }
}