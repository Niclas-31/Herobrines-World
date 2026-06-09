package de.niclasl.herobrines_world.common.worldgen.biome;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> ABYSSAL_WASTES = register("abyssal_wastes");
    public static final ResourceKey<Biome> ASH_DESERT = register("ash_desert");
    public static final ResourceKey<Biome> CURSED_FOREST = register("cursed_forest");
    public static final ResourceKey<Biome> FIRE_LAND = register("fire_land");
    public static final ResourceKey<Biome> FROZEN_FOREST = register("frozen_forest");
    public static final ResourceKey<Biome> MISTY_CHASMS = register("misty_chasms");
    public static final ResourceKey<Biome> VOID_DEPTHS = register("void_depths");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name));
    }
}