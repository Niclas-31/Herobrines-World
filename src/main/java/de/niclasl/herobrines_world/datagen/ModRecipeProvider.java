package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.block.ModBlocks;
import de.niclasl.herobrines_world.common.registries.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
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
        List<ItemLike> ASH_SMELTABLES = List.of(ModItems.ASH,
                ModBlocks.ASH_ORE, ModBlocks.DEEPSLATE_ASH_ORE);
        List<ItemLike> GREEN_SMELTABLES = List.of(ModBlocks.GREEN_ORE,
                ModBlocks.DEEPSLATE_GREEN_ORE);
        List<ItemLike> HEROBRINE_SMELTABLES = List.of(ModBlocks.HEROBRINE_ORE,
                ModBlocks.DEEPSLATE_HEROBRINE_ORE);
        List<ItemLike> SMOOTH_SMELTABLES = List.of(ModBlocks.BLUE_SANDSTONE);
        List<ItemLike> PLATIN_SMELTABLES = List.of(ModBlocks.PLATINE_ORE,
                ModBlocks.DEEPSLATE_PLATIN_ORE);

        oreSmelting(output, ASH_SMELTABLES, RecipeCategory.MISC, ModItems.ASH_INGOT.get(), 0.25f, "ash");
        oreBlasting(output, ASH_SMELTABLES, ModItems.ASH_INGOT.get(), 0.25f, "ash");

        shaped(RecipeCategory.TOOLS, ModItems.ASH_PICKAXE.get())
                .pattern("aaa")
                .pattern(" s ")
                .pattern(" s ")
                .define('a', ModItems.ASH_INGOT.get())
                .define('s', Items.STICK)
                .unlockedBy("has_ash_ingot", has(ModItems.ASH_INGOT)).save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHISELED_SANDSTONE.get())
                .pattern("B")
                .pattern("B")
                .define('B', ModBlocks.BLUE_SANDSTONE_SLAB.get())
                .unlockedBy("has_blue_sandstone_slab", has(ModBlocks.BLUE_SANDSTONE_SLAB)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHISELED_SANDSTONE.get(),
                ModBlocks.BLUE_SANDSTONE);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE.get(), 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', ModBlocks.BLUE_SANDSTONE.get())
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE.get(),
                ModBlocks.BLUE_SANDSTONE.get(), 1);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get(), 6)
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_CUT_SANDSTONE.get())
                .unlockedBy("has_blue_cut_sandstone", has(ModBlocks.BLUE_CUT_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get(),
                ModBlocks.BLUE_CUT_SANDSTONE.get(), 2);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get(),
                ModBlocks.BLUE_SANDSTONE.get(), 2);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE.get())
                .pattern("SS")
                .pattern("SS")
                .define('S', ModBlocks.ASH_BLOCK.get())
                .unlockedBy("has_ash_block", has(ModBlocks.ASH_BLOCK)).save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_SLAB.get(), 6)
                .pattern("SSS")
                .define('S', Ingredient.of(ModBlocks.BLUE_SANDSTONE.get(),
                        ModBlocks.BLUE_CHISELED_SANDSTONE.get()))
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE))
                .unlockedBy("has_blue_chiseled_sandstone", has(ModBlocks.BLUE_CHISELED_SANDSTONE))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_SLAB.get(),
                ModBlocks.BLUE_SANDSTONE.get(), 2);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_STAIRS.get(), 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', Ingredient.of(ModBlocks.BLUE_SANDSTONE.get(),
                        ModBlocks.BLUE_CHISELED_SANDSTONE.get(), ModBlocks.BLUE_CUT_SANDSTONE.get()))
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE))
                .unlockedBy("has_blue_chiseled_sandstone", has(ModBlocks.BLUE_CHISELED_SANDSTONE))
                .unlockedBy("has_blue_cut_sandstone", has(ModBlocks.BLUE_CUT_SANDSTONE))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_STAIRS.get(),
                ModBlocks.BLUE_SANDSTONE.get());

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_WALL.get(), 6)
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_SANDSTONE.get())
                .unlockedBy("has_blue_sandstone", has(ModBlocks.BLUE_SANDSTONE.get())).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SANDSTONE_WALL.get(),
                ModBlocks.BLUE_SANDSTONE.get());

        oreSmelting(output, SMOOTH_SMELTABLES, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE.get(), 0.5f, "smooth");

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get(), 6)
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_SMOOTH_SANDSTONE.get())
                .unlockedBy("has_blue_smooth_sandstone", has(ModBlocks.BLUE_SMOOTH_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get(),
                ModBlocks.BLUE_SMOOTH_SANDSTONE.get(), 2);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get(), 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', ModBlocks.BLUE_SMOOTH_SANDSTONE.get())
                .unlockedBy("has_blue_smooth_sandstone", has(ModBlocks.BLUE_SMOOTH_SANDSTONE)).save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get(),
                ModBlocks.BLUE_SMOOTH_SANDSTONE.get());

        shaped(RecipeCategory.REDSTONE, ModBlocks.DELAYER.get())
                .pattern(" T ")
                .pattern("RSR")
                .define('T', Items.REDSTONE_TORCH)
                .define('R', Items.REDSTONE)
                .define('S', Items.SMOOTH_STONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_AXE.get())
                .pattern("MM")
                .pattern("MS")
                .pattern(" S")
                .define('M', Items.MAGMA_BLOCK)
                .define('S', Items.STICK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_BOOTS.get())
                .pattern("M M")
                .pattern("M M")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_CHESTPLATE.get())
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_HELMET.get())
                .pattern("MMM")
                .pattern("M M")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_HOE.get())
                .pattern("MM")
                .pattern(" S")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_LEGGINGS.get())
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_PICKAXE.get())
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_SHOVEL.get())
                .pattern("M")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.FIRE_SWORD.get())
                .pattern("M")
                .pattern("M")
                .pattern("S")
                .define('S', Items.STICK)
                .define('M', Items.MAGMA_BLOCK)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK)).save(output);

        oreSmelting(output, GREEN_SMELTABLES, RecipeCategory.MISC, ModItems.GREEN_GEMSTONE.get(), 0.5f, "green");
        oreBlasting(output, GREEN_SMELTABLES, ModItems.GREEN_GEMSTONE.get(), 0.5f, "green");

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_AXE.get())
                .pattern("DD")
                .pattern("DS")
                .pattern(" S")
                .define('D', ModItems.HEROBRINE_DIAMOND)
                .define('S', Items.STICK)
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HEROBRINE_BLOCK.get())
                .pattern("DDD")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_BOOTS.get())
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_CHESTPLATE.get())
                .pattern("D D")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shapeless(RecipeCategory.MISC, ModItems.HEROBRINE_DIAMOND.get(), 9)
                .requires(ModBlocks.HEROBRINE_BLOCK.get())
                .unlockedBy("has_herobrine_block", has(ModBlocks.HEROBRINE_BLOCK)).save(output);

        oreSmelting(output, HEROBRINE_SMELTABLES, RecipeCategory.MISC, ModItems.HEROBRINE_DIAMOND.get(), 0.75f, "herobrine");
        oreBlasting(output, HEROBRINE_SMELTABLES, ModItems.HEROBRINE_DIAMOND.get(), 0.75f, "herobrine");

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_HELMET.get())
                .pattern("DDD")
                .pattern("D D")
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_HOE.get())
                .pattern("DD")
                .pattern(" S")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_LEGGINGS.get())
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_PICKAXE.get())
                .pattern("DDD")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_SHOVEL.get())
                .pattern("D")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINE_SWORD.get())
                .pattern("D")
                .pattern("D")
                .pattern("S")
                .define('S', Items.STICK)
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.HEROBRINES_REALM.get())
                .pattern(" D ")
                .pattern("DFD")
                .pattern(" D ")
                .define('F', Items.FLINT_AND_STEEL)
                .define('D', ModItems.HEROBRINE_DIAMOND.get())
                .unlockedBy("has_herobrine_diamond", has(ModItems.HEROBRINE_DIAMOND)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.LOGIC_GATE_BLOCK.get())
                .pattern(" R ")
                .pattern("QSQ")
                .pattern(" R ")
                .define('R', Items.REDSTONE)
                .define('Q', Items.QUARTZ)
                .define('S', Items.SMOOTH_STONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_AXE.get())
                .pattern("GG")
                .pattern("GS")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_BOOTS.get())
                .pattern("G G")
                .pattern("G G")
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_CHESTPLATE.get())
                .pattern("G G")
                .pattern("GGG")
                .pattern("GGG")
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_HELMET.get())
                .pattern("GGG")
                .pattern("G G")
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_HOE.get())
                .pattern("GG")
                .pattern(" S")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_LEGGINGS.get())
                .pattern("GGG")
                .pattern("G G")
                .pattern("G G")
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_PICKAXE.get())
                .pattern("GGG")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_SHOVEL.get())
                .pattern("G")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.NATURE_SWORD.get())
                .pattern("G")
                .pattern("G")
                .pattern("S")
                .define('S', Items.STICK)
                .define('G', ModItems.GREEN_GEMSTONE.get())
                .unlockedBy("has_green", has(ModItems.GREEN_GEMSTONE)).save(output);

        shaped(RecipeCategory.MISC, ModItems.ORE_DETECTOR.get())
                .pattern(" S ")
                .pattern(" S ")
                .pattern("TTT")
                .define('S', Items.STICK)
                .define('T', Items.STONE_SLAB)
                .unlockedBy("has_stone_slab", has(Items.STONE_SLAB)).save(output);

        oreSmelting(output, PLATIN_SMELTABLES, RecipeCategory.MISC, ModItems.PLATIN_INGOT.get(), 0.8f, "platin");
        oreBlasting(output, PLATIN_SMELTABLES, ModItems.PLATIN_INGOT.get(), 0.8f, "platin");

        shaped(RecipeCategory.TOOLS, ModItems.UNDERWORLD.get())
                .pattern(" A ")
                .pattern("AFA")
                .pattern(" A ")
                .define('A', ModBlocks.ABYSSAL_BLOCK.get())
                .define('F', Items.FLINT_AND_STEEL)
                .unlockedBy("has_abyssal_block", has(ModBlocks.ABYSSAL_BLOCK.get())).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.AUTO_FARMER.get())
                .pattern("IRI")
                .pattern("RBR")
                .pattern("IDI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('B', ModItems.BATTERY.get())
                .define('D', Items.DISPENSER)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModItems.SMART_CHIP_CASE.get())
                .pattern("IRP")
                .pattern("R R")
                .pattern("PRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('P', ModItems.PLATIN_INGOT.get())
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.REDSTONE, ModItems.SMART_CHIP.get())
                .pattern("PGP")
                .pattern("GCG")
                .pattern("PGP")
                .define('P', ModItems.PLATIN_INGOT.get())
                .define('G', Items.GOLD_INGOT)
                .define('C', ModItems.SMART_CHIP_CASE.get())
                .unlockedBy("has_platin", has(ModItems.PLATIN_INGOT)).save(output);

        shaped(RecipeCategory.REDSTONE, ModItems.BATTERY.get())
                .pattern("IRI")
                .pattern("RCR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.STORAGE_CONTROLLER.get())
                .pattern("IRI")
                .pattern("RCR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('C', Items.CHEST)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.CARD_READER.get())
                .pattern("IRI")
                .pattern("QCQ")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('Q', Items.QUARTZ)
                .define('C', ModItems.KEY_CARD.get())
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .unlockedBy("has_key_card", has(ModItems.KEY_CARD)).save(output);

        shapeless(RecipeCategory.REDSTONE, ModItems.KEY_CARD.get())
                .requires(Items.PAPER)
                .requires(Items.REDSTONE)
                .requires(Items.IRON_NUGGET)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.BATTERY_CHARGER.get())
                .pattern("IRI")
                .pattern("ICI")
                .pattern("IRI")
                .define('I', Items.IRON_BLOCK)
                .define('R', Items.REDSTONE_BLOCK)
                .define('C', Items.COPPER_BLOCK)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.SIGNAL.get())
                .pattern(" R ")
                .pattern("RLR")
                .pattern(" Q ")
                .define('R', Items.REDSTONE)
                .define('L', Items.REDSTONE_LAMP)
                .define('Q', Items.QUARTZ)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);
    }

    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, 200, pGroup, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, ItemLike pResult,
                               float pExperience, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, RecipeCategory.MISC, pResult,
                pExperience, 100, pGroup, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, HerobrinesWorld.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}