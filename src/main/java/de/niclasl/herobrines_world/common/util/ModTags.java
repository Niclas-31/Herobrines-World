package de.niclasl.herobrines_world.common.util;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_NATURE_TOOL = createTag("needs_nature_tool");
        public static final TagKey<Block> NEEDS_FIRE_TOOL = createTag("needs_fire_tool");
        public static final TagKey<Block> NEEDS_HEROBRINE_TOOL = createTag("needs_herobrine_tool");
        public static final TagKey<Block> NEEDS_PLATIN_TOOL = createTag("needs_platin_tool");
        public static final TagKey<Block> NEEDS_ASH_TOOLS = createTag("needs_ash_tools");

        public static final TagKey<Block> INCORRECT_FOR_NATURE_TOOL = createTag("incorrect_for_nature_tool");
        public static final TagKey<Block> INCORRECT_FOR_FIRE_TOOL = createTag("incorrect_for_fire_tool");
        public static final TagKey<Block> INCORRECT_FOR_HEROBRINE_TOOL = createTag("incorrect_for_herobrine_tool");
        public static final TagKey<Block> INCORRECT_FOR_PLATIN_TOOL = createTag("incorrect_for_platin_tool");
        public static final TagKey<Block> INCORRECT_FOR_ASH_TOOL = createTag("incorrect_for_ash_tool");

        public static final TagKey<Block> ANCIENT_DEBRIS = createTag("ores/ancient_debris");
        public static final TagKey<Block> ASH = createTag("ores/ash");
        public static final TagKey<Block> COAL = createTag("ores/coal");
        public static final TagKey<Block> COPPER = createTag("ores/copper");
        public static final TagKey<Block> DIAMOND = createTag("ores/diamond");
        public static final TagKey<Block> EMERALD = createTag("ores/emerald");
        public static final TagKey<Block> FROZEN = createTag("ores/frozen");
        public static final TagKey<Block> GOLD = createTag("ores/gold");
        public static final TagKey<Block> GREEN = createTag("ores/green");
        public static final TagKey<Block> HEROBRINE = createTag("ores/herobrine");
        public static final TagKey<Block> IRON = createTag("ores/iron");
        public static final TagKey<Block> LAPIS = createTag("ores/lapis");
        public static final TagKey<Block> PLATIN = createTag("ores/platin");
        public static final TagKey<Block> QUARTZ = createTag("ores/quartz");
        public static final TagKey<Block> REDSTONE = createTag("ores/redstone");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> REPAIRS_NATURE_ARMOR = createTag("repairs_nature_armor");
        public static final TagKey<Item> REPAIRS_FIRE_ARMOR = createTag("repairs_fire_armor");
        public static final TagKey<Item> REPAIRS_HEROBRINE_ARMOR = createTag("repairs_herobrine_armor");
        public static final TagKey<Item> REPAIRS_PLATIN_ARMOR = createTag("repairs_platin_armor");

        public static final TagKey<Item> NATURE_TOOL_MATERIALS = createTag("nature_tool_materials");
        public static final TagKey<Item> FIRE_TOOL_MATERIALS = createTag("fire_tool_materials");
        public static final TagKey<Item> HEROBRINE_TOOL_MATERIALS = createTag("herorbine_tool_materials");
        public static final TagKey<Item> PLATIN_TOOL_MATERIALS = createTag("platin_tool_materials");
        public static final TagKey<Item> ASH_TOOL_MATERIALS = createTag("ash_tool_materials");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> IS_ASH_ORE = createTag("is_ash_ore");
        public static final TagKey<Biome> IS_GREEN_ORE = createTag("is_green_ore");
        public static final TagKey<Biome> IS_HEROBRINE_ORE = createTag("is_herobrine_ore");
        public static final TagKey<Biome> IS_PLATIN_ORE = createTag("is_platin_ore");

        public static final TagKey<Biome> IS_HEROBRINE_REALM = createTag("is_herobrine_realm");
        public static final TagKey<Biome> IS_ALL_BIOMES = createTag("is_all_biomes");

        public static final TagKey<Biome> HAS_ASH_DESERT_PYRAMID = createTag("has_structure/ash_desert_pyramid");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name));
        }
    }
}