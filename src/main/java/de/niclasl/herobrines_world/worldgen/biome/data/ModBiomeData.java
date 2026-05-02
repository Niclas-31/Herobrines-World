package de.niclasl.herobrines_world.worldgen.biome.data;

import de.niclasl.herobrines_world.worldgen.biome.HerobrinesRealmBiomes;
import de.niclasl.herobrines_world.worldgen.biome.ModBiomes;
import de.niclasl.herobrines_world.worldgen.biome.UnderworldBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomeData {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> holdergetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(ModBiomes.ASH_DESERT, HerobrinesRealmBiomes.ashDesert(holdergetter, holdergetter1));
        context.register(ModBiomes.FROZEN_FOREST, HerobrinesRealmBiomes.frozenForest(holdergetter, holdergetter1));
        context.register(ModBiomes.CURSED_FOREST, HerobrinesRealmBiomes.cursedForest(holdergetter, holdergetter1));
        context.register(ModBiomes.FIRE_LAND, HerobrinesRealmBiomes.fireLand(holdergetter, holdergetter1));
        context.register(ModBiomes.VOID_DEPTHS, UnderworldBiomes.voidDepths(holdergetter,  holdergetter1));
        context.register(ModBiomes.MISTY_CHASMS, UnderworldBiomes.mistyChasms(holdergetter, holdergetter1));
        context.register(ModBiomes.ABYSSAL_WASTES, UnderworldBiomes.abyssalWastes(holdergetter, holdergetter1));
    }
}