package de.niclasl.herobrines_world.util;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {
    public static final ResourceKey<Level> HEROBRINE_REALM =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrines_realm"));
    public static final ResourceKey<Level> UNDERWORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "underworld"));
}