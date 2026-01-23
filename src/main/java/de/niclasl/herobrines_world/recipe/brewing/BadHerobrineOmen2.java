package de.niclasl.herobrines_world.recipe.brewing;

import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.core.component.DataComponents;

import de.niclasl.herobrines_world.potion.ModPotions;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class BadHerobrineOmen2 implements IBrewingRecipe {
	@SubscribeEvent
	public static void init(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addRecipe(new BadHerobrineOmen2());
	}

	@Override
	public boolean isInput(ItemStack input) {
		Item inputItem = input.getItem();
		return (inputItem == Items.POTION || inputItem == Items.SPLASH_POTION || inputItem == Items.LINGERING_POTION) && input.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(ModPotions.BAD_HEROBRINE_OMEN);
	}

	@Override
	public boolean isIngredient(@NotNull ItemStack ingredient) {
		return Ingredient.of(Items.REDSTONE).test(ingredient);
	}

	@Override
	public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
		if (isInput(input) && isIngredient(ingredient)) {
			return PotionContents.createItemStack(input.getItem(), ModPotions.BAD_HEROBRINE_OMEN_2);
		}
		return ItemStack.EMPTY;
	}
}