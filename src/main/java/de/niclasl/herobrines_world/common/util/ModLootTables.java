package de.niclasl.herobrines_world.common.util;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class ModLootTables {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet<>();
    public static final ResourceKey<LootTable> ASH_DESERT_PYRAMID = register("chests/ash_desert_pyramid");
    public static final ResourceKey<LootTable> COPPER_DUNGEON = register("chests/copper_dungeon");
    public static final ResourceKey<LootTable> THRONE_OF_THE_UNDERWORLD = register("chests/throne_of_the_underworld");

    private static ResourceKey<LootTable> register(String name) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, name)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> name) {
        if (LOCATIONS.add(name)) {
            return name;
        } else {
            throw new IllegalArgumentException(name.identifier() + " is already a registered built-in loot table");
        }
    }
}