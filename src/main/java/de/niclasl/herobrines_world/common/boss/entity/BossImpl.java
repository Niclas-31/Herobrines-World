package de.niclasl.herobrines_world.common.boss.entity;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.entities.ModEntities;
import de.niclasl.herobrines_world_api.boss.BossFactory;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.resources.Identifier;

public class BossImpl {
    public static void register() {
        registerBosses(id("herobrine_boss"), level -> new HerobrineBoss(
                ModEntities.HEROBRINE_BOSS.get(), level));
        registerBosses(id("entity_303"), level -> new Entity303(
                ModEntities.ENTITY_303.get(), level));
    }

    public static void registerBosses(Identifier id, BossFactory factory) {
        if (HWRegistries.BOSSES.contains(id)) {
            throw new IllegalStateException("Bosses with id " + id + " already exists");
        }

        HWRegistries.BOSSES.register(id, factory);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path);
    }
}