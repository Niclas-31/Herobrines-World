package de.niclasl.herobrines_world.datagen.tags;

import de.niclasl.herobrines_world.common.registries.villagers.ModVillagerTrades;
import de.niclasl.herobrines_world.common.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTagsProvider extends VillagerTradesTagsProvider {
    public ModVillagerTradeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(ModTags.Villagers.LUMBERJACK_LEVEL_1)
                .add(ModVillagerTrades.LUMBERJACK_1_OAK_LOG_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_1_BIRCH_LOG_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_1_SPRUCE_LOG_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_1_JUNGLE_LOG_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_1_ACACIA_LOG_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_1_DARK_OAK_LOG_EMERALD);

        tag(ModTags.Villagers.LUMBERJACK_LEVEL_2)
                .add(ModVillagerTrades.LUMBERJACK_2_MANGROVE_LOG_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_2_BAMBOO_BLOCK_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_2_CHERRY_LOG_EMERALD);

        tag(ModTags.Villagers.LUMBERJACK_LEVEL_3)
                .add(ModVillagerTrades.LUMBERJACK_3_PALE_OAK_LOG_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_3_WARPED_STEM_EMERALD)
                .add(ModVillagerTrades.LUMBERJACK_3_CRIMSON_STEM_EMERALD);

        tag(ModTags.Villagers.LUMBERJACK_LEVEL_4)
                .add(ModVillagerTrades.LUMBERJACK_4_EMERALD_IRON_AXE)
                .add(ModVillagerTrades.LUMBERJACK_4_EMERALD_DIAMOND_AXE);

        tag(ModTags.Villagers.LUMBERJACK_LEVEL_5)
                .add(ModVillagerTrades.LUMBERJACK_5_EMERALD_NETHERITE_AXE);
    }
}