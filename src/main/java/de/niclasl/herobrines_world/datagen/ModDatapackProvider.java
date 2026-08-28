package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.enchantments.ModEnchantments;
import de.niclasl.herobrines_world.common.registries.villagers.ModTradeSets;
import de.niclasl.herobrines_world.common.registries.villagers.ModVillagerTrades;
import de.niclasl.herobrines_world.common.util.clock.ModTimelines;
import de.niclasl.herobrines_world.common.util.clock.ModWorldClocks;
import de.niclasl.herobrines_world.common.worldgen.ModBiomeModifiers;
import de.niclasl.herobrines_world.common.worldgen.ModConfiguredFeatures;
import de.niclasl.herobrines_world.common.worldgen.ModPlacedFeatures;
import de.niclasl.herobrines_world.common.world.biome.data.ModBiomeData;
import de.niclasl.herobrines_world.common.world.ModDimensions;
import de.niclasl.herobrines_world.common.world.noise.data.ModNoiseData;
import de.niclasl.herobrines_world.common.world.noise.generator.ModNoiseGeneratorSettings;
import de.niclasl.herobrines_world.common.structure.ModStructureSets;
import de.niclasl.herobrines_world.common.structure.ModStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(Registries.STRUCTURE, ModStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, ModStructureSets::bootstrap)
            .add(Registries.BIOME, ModBiomeData::bootstrap)
            .add(Registries.NOISE, ModNoiseData::bootstrap)
            .add(Registries.NOISE_SETTINGS, ModNoiseGeneratorSettings::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.WORLD_CLOCK, ModWorldClocks::bootstrap)
            .add(Registries.TIMELINE, ModTimelines::bootstrap)
            .add(Registries.VILLAGER_TRADE, ModVillagerTrades::bootstrap)
            .add(Registries.TRADE_SET, ModTradeSets::bootstrap);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(HerobrinesWorld.MOD_ID));
    }
}