package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import de.niclasl.herobrines_world.common.registries.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return "My Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        List<ItemLike> ASH_SMELTABLES = List.of(ModBlocks.ASH_ORE,
                ModBlocks.DEEPSLATE_ASH_ORE);
        List<ItemLike> GREEN_SMELTABLES = List.of(ModBlocks.GREEN_ORE,
                ModBlocks.DEEPSLATE_GREEN_ORE);
        List<ItemLike> HEROBRINE_SMELTABLES = List.of(ModBlocks.HEROBRINE_ORE,
                ModBlocks.DEEPSLATE_HEROBRINE_ORE);
        List<ItemLike> SMOOTH_SMELTABLES = List.of(ModBlocks.BLUE_SANDSTONE);
        List<ItemLike> PLATIN_SMELTABLES = List.of(ModBlocks.PLATINE_ORE,
                ModBlocks.DEEPSLATE_PLATIN_ORE);

        oreSmelting(ASH_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.ASH_INGOT, 0.25f, 200, "ash");
        oreBlasting(ASH_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.ASH_INGOT, 0.25f, 100, "ash");

        shaped(RecipeCategory.TOOLS, ModItems.ASH_PICKAXE)
                .pattern("aaa")
                .pattern(" s ")
                .pattern(" s ")
                .define('a', ModItems.ASH_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_ash_ingot", has(ModItems.ASH_INGOT)).save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHISELED_SANDSTONE)
                .pattern("B")
                .pattern("B")
                .define('B', ModBlocks.BLUE_SANDSTONE_SLAB)
                .unlockedBy("has_blue_sandstone_slab", has(ModBlocks.BLUE_SANDSTONE_SLAB)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHISELED_SANDSTONE,
                ModBlocks.BLUE_SANDSTONE);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE, 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', ModBlocks.BLUE_SANDSTONE)
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE,
                ModBlocks.BLUE_SANDSTONE, 1);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE_SLAB, 6)
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_CUT_SANDSTONE)
                .unlockedBy("has_blue_cut_sandstone", has(ModBlocks.BLUE_CUT_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE_SLAB,
                ModBlocks.BLUE_CUT_SANDSTONE, 2);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE_SLAB,
                ModBlocks.BLUE_SANDSTONE, 2);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE)
                .pattern("SS")
                .pattern("SS")
                .define('S', ModBlocks.ASH_BLOCK)
                .unlockedBy("has_ash_block", has(ModBlocks.ASH_BLOCK)).save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_SLAB, 6)
                .pattern("SSS")
                .define('S', Ingredient.of(ModBlocks.BLUE_SANDSTONE,
                        ModBlocks.BLUE_CHISELED_SANDSTONE))
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE))
                .unlockedBy("has_blue_chiseled_sandstone", has(ModBlocks.BLUE_CHISELED_SANDSTONE))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_SLAB,
                ModBlocks.BLUE_SANDSTONE, 2);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_STAIRS, 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', Ingredient.of(ModBlocks.BLUE_SANDSTONE,
                        ModBlocks.BLUE_CHISELED_SANDSTONE, ModBlocks.BLUE_CUT_SANDSTONE))
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE))
                .unlockedBy("has_blue_chiseled_sandstone", has(ModBlocks.BLUE_CHISELED_SANDSTONE))
                .unlockedBy("has_blue_cut_sandstone", has(ModBlocks.BLUE_CUT_SANDSTONE))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_STAIRS,
                ModBlocks.BLUE_SANDSTONE);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_WALL, 6)
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_SANDSTONE)
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_WALL,
                ModBlocks.BLUE_SANDSTONE);

        oreSmelting(SMOOTH_SMELTABLES, RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE, 0.5f, 200, "smooth");

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB, 6)
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_SMOOTH_SANDSTONE)
                .unlockedBy("has_blue_smooth_sandstone", has(ModBlocks.BLUE_SMOOTH_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB,
                ModBlocks.BLUE_SMOOTH_SANDSTONE, 2);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS, 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_SMOOTH_SANDSTONE)
                .unlockedBy("has_blue_smooth_sandstone", has(ModBlocks.BLUE_SMOOTH_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS,
                ModBlocks.BLUE_SMOOTH_SANDSTONE);

        shaped(RecipeCategory.REDSTONE, ModBlocks.DELAYER)
                .pattern(" T ")
                .pattern("RSR")
                .define('T', Items.REDSTONE_TORCH)
                .define('R', Items.REDSTONE)
                .define('S', Items.SMOOTH_STONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_AXE)
                .pattern("MM")
                .pattern("MS")
                .pattern(" S")
                .define('M', Items.MAGMA_BLOCK)
                .define('S', Items.STICK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_BOOTS)
                .pattern("M M")
                .pattern("M M")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_CHESTPLATE)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_HELMET)
                .pattern("MMM")
                .pattern("M M")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_HOE)
                .pattern("MM")
                .pattern(" S")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_LEGGINGS)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_PICKAXE)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_SHOVEL)
                .pattern("M")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_SWORD)
                .pattern("M")
                .pattern("M")
                .pattern("S")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        oreSmelting(GREEN_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.GREEN_GEMSTONE, 0.5f, 200, "green");
        oreBlasting(GREEN_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.GREEN_GEMSTONE, 0.5f, 100, "green");

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_AXE)
                .pattern("DD")
                .pattern("DS")
                .pattern(" S")
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .define('S', Items.STICK)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HEROBRINE_BLOCK)
                .pattern("DDD")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_BOOTS)
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_CHESTPLATE)
                .pattern("D D")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shapeless(RecipeCategory.MISC, ModItems.HEROBRINE_DIAMOND, 9)
                .requires(ModBlocks.HEROBRINE_BLOCK)
                .unlockedBy("has_herobrine_block", has(ModBlocks.HEROBRINE_BLOCK)).save(output);

        oreSmelting(HEROBRINE_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.HEROBRINE_DIAMOND, 0.75f, 200, "herobrine");
        oreBlasting(HEROBRINE_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.HEROBRINE_DIAMOND, 0.75f, 100, "herobrine");

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_HELMET)
                .pattern("DDD")
                .pattern("D D")
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_HOE)
                .pattern("DD")
                .pattern(" S")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_LEGGINGS)
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_PICKAXE)
                .pattern("DDD")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_SHOVEL)
                .pattern("D")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_SWORD)
                .pattern("D")
                .pattern("D")
                .pattern("S")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINES_REALM)
                .pattern(" D ")
                .pattern("DFD")
                .pattern(" D ")
                .define('F', Items.FLINT_AND_STEEL)
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.LOGIC_GATE_BLOCK)
                .pattern(" R ")
                .pattern("QSQ")
                .pattern(" R ")
                .define('R', Items.REDSTONE)
                .define('Q', Items.QUARTZ)
                .define('S', Items.SMOOTH_STONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_AXE)
                .pattern("GG")
                .pattern("GS")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_BOOTS)
                .pattern("G G")
                .pattern("G G")
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_CHESTPLATE)
                .pattern("G G")
                .pattern("GGG")
                .pattern("GGG")
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_HELMET)
                .pattern("GGG")
                .pattern("G G")
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_HOE)
                .pattern("GG")
                .pattern(" S")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_LEGGINGS)
                .pattern("GGG")
                .pattern("G G")
                .pattern("G G")
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_PICKAXE)
                .pattern("GGG")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_SHOVEL)
                .pattern("G")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_SWORD)
                .pattern("G")
                .pattern("G")
                .pattern("S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE)
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.MISC, ModItems.ORE_DETECTOR)
                .pattern(" S ")
                .pattern(" S ")
                .pattern("TTT")
                .define('S', Items.STICK)
                .define('T', Items.STONE_SLAB)
                .unlockedBy("has_stone_slab", has(Items.STONE_SLAB)).save(output);

        oreSmelting(PLATIN_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.PLATIN_INGOT, 0.8f, 200, "platin");
        oreBlasting(PLATIN_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.PLATIN_INGOT, 0.8f, 100, "platin");

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_AXE)
                .pattern("PP")
                .pattern("PS")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_BOOTS)
                .pattern("P P")
                .pattern("P P")
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_CHESTPLATE)
                .pattern("P P")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_HELMET)
                .pattern("PPP")
                .pattern("P P")
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_HOE)
                .pattern("PP")
                .pattern(" S")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_LEGGINGS)
                .pattern("PPP")
                .pattern("P P")
                .pattern("P P")
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_PICKAXE)
                .pattern("PPP")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_SHOVEL)
                .pattern("P")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.PLATIN_SWORD)
                .pattern("P")
                .pattern("P")
                .pattern("S")
                .define('S', Items.STICK)
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.UNDERWORLD)
                .pattern(" A ")
                .pattern("AFA")
                .pattern(" A ")
                .define('A', ModBlocks.ABYSSAL_BLOCK)
                .define('F', Items.FLINT_AND_STEEL)
                .unlockedBy("has_abyssal_block", has(ModBlocks.ABYSSAL_BLOCK)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.AUTO_FARMER)
                .pattern("IRI")
                .pattern("RBR")
                .pattern("IDI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('B', ModItems.BATTERY)
                .define('D', Items.DISPENSER)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModItems.SMART_CHIP_CASE)
                .pattern("IRP")
                .pattern("R R")
                .pattern("PRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('P', ModItems.PLATIN_INGOT)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.REDSTONE, ModItems.SMART_CHIP)
                .pattern("PGP")
                .pattern("GCG")
                .pattern("PGP")
                .define('P', ModItems.PLATIN_INGOT)
                .define('G', Items.GOLD_INGOT)
                .define('C', ModItems.SMART_CHIP_CASE)
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.REDSTONE, ModItems.BATTERY)
                .pattern("IRI")
                .pattern("RCR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.STORAGE_CONTROLLER)
                .pattern("IRI")
                .pattern("RCR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('C', Items.CHEST)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.BATTERY_CHARGER)
                .pattern("IRI")
                .pattern("ICI")
                .pattern("IRI")
                .define('I', Items.IRON_BLOCK)
                .define('R', Items.REDSTONE_BLOCK)
                .define('C', Items.COPPER_BLOCK.weathering().unaffected())
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.SIGNAL)
                .pattern(" R ")
                .pattern("RLR")
                .pattern(" Q ")
                .define('R', Items.REDSTONE)
                .define('L', Items.REDSTONE_LAMP)
                .define('Q', Items.QUARTZ)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.MISC, ModBlocks.LUMBERJACK_TABLE)
                .pattern(" L ")
                .pattern("LCL")
                .pattern(" L ")
                .define('L', Items.OAK_LOG)
                .define('C', Items.CRAFTING_TABLE)
                .unlockedBy("has_crafting_table", has(Items.CRAFTING_TABLE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.WAYPOINT_COMPASS)
                .pattern(" G ")
                .pattern("GCG")
                .pattern(" G ")
                .define('G', Items.GOLD_INGOT)
                .define('C', Items.COMPASS)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.CARD_READER)
                .pattern("IRI")
                .pattern("QCQ")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('Q', Items.QUARTZ)
                .define('C', ModItems.KEY_CARD)
                .unlockedBy("has_key_card", has(ModItems.KEY_CARD)).save(output);

        shapeless(RecipeCategory.REDSTONE, ModItems.KEY_CARD)
                .requires(Items.PAPER)
                .requires(Items.REDSTONE)
                .requires(Items.IRON_NUGGET)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);
    }
}