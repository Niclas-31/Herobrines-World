package de.niclasl.herobrines_world.registries;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.registries.block.ModBlocks;
import de.niclasl.herobrines_world.registries.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.registries.effect.ModEffects;
import de.niclasl.herobrines_world.registries.enchantment.ModEnchantmentEffects;
import de.niclasl.herobrines_world.registries.entity.ModEntities;
import de.niclasl.herobrines_world.registries.entity.custom.HerobrineBoss;
import de.niclasl.herobrines_world.registries.item.ModCreativeModeTabs;
import de.niclasl.herobrines_world.registries.item.ModItems;
import de.niclasl.herobrines_world.registries.potion.ModPotions;
import de.niclasl.herobrines_world.registries.recipe.ModRecipes;
import de.niclasl.herobrines_world.registries.screen.ModMenuTypes;
import de.niclasl.herobrines_world.registries.villager.ModVillagers;
import net.neoforged.bus.api.IEventBus;

public class ModRegistries {

    public static void register(IEventBus eventBus) {
        ModCreativeModeTabs.register(eventBus);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModDataComponents.register(eventBus);

        ModEffects.register(eventBus);
        ModPotions.register(eventBus);

        ModEnchantmentEffects.register(eventBus);
        ModEntities.register(eventBus);

        ModVillagers.register(eventBus);

        ModBlockEntities.register(eventBus);

        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);

        ModVariables.ATTACHMENT_TYPES.register(eventBus);
        HerobrineBoss.register();
    }
}