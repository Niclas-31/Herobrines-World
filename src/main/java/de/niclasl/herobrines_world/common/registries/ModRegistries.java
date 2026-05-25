package de.niclasl.herobrines_world.common.registries;

import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world.common.registries.block.ModBlocks;
import de.niclasl.herobrines_world.common.registries.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.effect.ModEffects;
import de.niclasl.herobrines_world.common.registries.enchantment.ModEnchantmentEffects;
import de.niclasl.herobrines_world.common.registries.entity.ModEntities;
import de.niclasl.herobrines_world.common.registries.entity.custom.HerobrineBoss;
import de.niclasl.herobrines_world.common.registries.item.ModCreativeModeTabs;
import de.niclasl.herobrines_world.common.registries.item.ModItems;
import de.niclasl.herobrines_world.common.registries.potion.ModPotions;
import de.niclasl.herobrines_world.common.registries.recipe.ModRecipes;
import de.niclasl.herobrines_world.common.registries.menu.ModMenuTypes;
import de.niclasl.herobrines_world.common.registries.villager.ModVillagers;
import de.niclasl.herobrines_world.common.worldgen.structure.ModStructurePieceType;
import de.niclasl.herobrines_world.common.worldgen.structure.ModStructureType;
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

        ModStructureType.register(eventBus);
        ModStructurePieceType.register(eventBus);

        ModVillagers.register(eventBus);
        ModBlockEntities.register(eventBus);

        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);

        ModVariables.ATTACHMENT_TYPES.register(eventBus);
        HerobrineBoss.register();
    }
}