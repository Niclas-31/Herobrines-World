package de.niclasl.herobrines_world.recipe;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, HerobrinesWorld.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, HerobrinesWorld.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SmartChipRecipe>> SMART_CHIP_SERIALIZER =
            SERIALIZERS.register("smart_chip", SmartChipRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CraftingRecipe>> SMART_CHIP_TYPE =
            TYPES.register("smart_chip", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "smart_chip";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}