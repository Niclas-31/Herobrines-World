package de.niclasl.herobrines_world.common.registries;

import de.niclasl.herobrines_world.common.leaderboard.RewardTypeImpl;
import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world.common.network.access.AccessModeImpl;
import de.niclasl.herobrines_world.common.network.transfer.TransferModeImpl;
import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import de.niclasl.herobrines_world.common.registries.blocks.entities.ModBlockEntities;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.effects.ModEffects;
import de.niclasl.herobrines_world.common.registries.enchantments.ModEnchantmentEffects;
import de.niclasl.herobrines_world.common.registries.entities.ModEntities;
import de.niclasl.herobrines_world.common.registries.entities.custom.HerobrineBoss;
import de.niclasl.herobrines_world.common.registries.items.ModCreativeModeTabs;
import de.niclasl.herobrines_world.common.registries.items.ModItems;
import de.niclasl.herobrines_world.common.registries.menus.ModMenuTypes;
import de.niclasl.herobrines_world.common.registries.potions.ModPotions;
import de.niclasl.herobrines_world.common.registries.recipes.ModRecipes;
import de.niclasl.herobrines_world.common.registries.villagers.ModVillagers;
import de.niclasl.herobrines_world.common.worldgen.structure.ModStructurePieceType;
import de.niclasl.herobrines_world.common.worldgen.structure.ModStructureType;
import net.neoforged.bus.api.IEventBus;

public class ModRegistries {

    public static void register(IEventBus eventBus) {
        RewardTypeImpl.register();
        TransferModeImpl.register();
        AccessModeImpl.register();

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