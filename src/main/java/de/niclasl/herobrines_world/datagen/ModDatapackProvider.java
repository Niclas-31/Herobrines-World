package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.registries.enchantment.ModEnchantments;
import de.niclasl.herobrines_world.worldgen.ModBiomeModifiers;
import de.niclasl.herobrines_world.worldgen.ModConfiguredFeatures;
import de.niclasl.herobrines_world.worldgen.ModPlacedFeatures;
import de.niclasl.herobrines_world.worldgen.biome.data.ModBiomeData;
import de.niclasl.herobrines_world.worldgen.dimension.ModDimensions;
import de.niclasl.herobrines_world.worldgen.dimension.noise.data.ModNoiseData;
import de.niclasl.herobrines_world.worldgen.dimension.noise.generator.ModNoiseGeneratorSettings;
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
            .add(Registries.BIOME, ModBiomeData::bootstrap)
            .add(Registries.NOISE, ModNoiseData::bootstrap)
            .add(Registries.NOISE_SETTINGS, ModNoiseGeneratorSettings::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(HerobrinesWorld.MODID));
    }
}