package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.datagen.advancements.ModAdvancementProvider;
import de.niclasl.herobrines_world.datagen.loottables.ModBlockLootTableProvider;
import de.niclasl.herobrines_world.datagen.loottables.ModChestLootTableProvider;
import de.niclasl.herobrines_world.datagen.loottables.ModEntityLootTableProvider;
import de.niclasl.herobrines_world.datagen.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        event.createDatapackRegistryObjects(ModDatapackProvider.BUILDER);

        event.createProvider(ModBiomeTagProvider::new);
        event.createProvider(ModBlockTagProvider::new);
        event.createProvider(ModItemTagProvider::new);
        event.createProvider(ModEnchantmentTagProvider::new);
        event.createProvider(ModPoiTypeTagProvider::new);
        event.createProvider(ModEntityTypeTagProvider::new);

        event.createProvider(ModAdvancementProvider::new);

        event.createProvider(ModDataMapProvider::new);
        event.createProvider(ModModelProvider::new);

        generator.addProvider(true, new LootTableProvider(
                output,
                Collections.emptySet(),
                List.of(
                        new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY),
                        new LootTableProvider.SubProviderEntry(ModChestLootTableProvider::new, LootContextParamSets.CHEST)
                ),
                lookup
        ));
        generator.addProvider(true, new ModRecipeProvider.Runner(output, lookup));
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        event.createDatapackRegistryObjects(ModDatapackProvider.BUILDER);

        event.createProvider(ModBiomeTagProvider::new);
        event.createProvider(ModBlockTagProvider::new);
        event.createProvider(ModItemTagProvider::new);
        event.createProvider(ModDataMapProvider::new);
        event.createProvider(ModModelProvider::new);

        generator.addProvider(true, new LootTableProvider(
                output,
                Collections.emptySet(),
                List.of(
                        new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY),
                        new LootTableProvider.SubProviderEntry(ModChestLootTableProvider::new, LootContextParamSets.CHEST)
                ),
                lookup
        ));
        generator.addProvider(true, new ModRecipeProvider.Runner(output, lookup));
    }
}