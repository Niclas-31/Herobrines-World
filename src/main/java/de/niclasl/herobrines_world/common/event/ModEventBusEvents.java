package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.entity.ModEntities;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import java.util.Calendar;

@EventBusSubscriber(modid = HerobrinesWorld.MODID)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.NICLASL.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> {

                    Calendar calendar = Calendar.getInstance();

                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    boolean christmasDays = day >= 24 && day <= 26;

                    return month != Calendar.DECEMBER || !christmasDays;
                },
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(ModEntities.GOOD_HEROBRINE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PathfinderMob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(
                ModEntities.CHRISTMAS_NICLASL.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> {

                    var biome = world.getBiome(pos);

                    if (biome.is(Identifier.parse("herobrines_world:frozen_forest"))) {
                        return true;
                    }

                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    return biome.is(Identifier.parse("herobrines_world:fire_land"))
                            && calendar.get(Calendar.MONTH) == Calendar.DECEMBER
                            && day >= 24
                            && day <= 26;
                },
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(ModEntities.BAD_HEROBRINE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}