package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.structure.boss.HWStructureTypes;
import de.niclasl.herobrines_world.common.util.variables.MapVariables;
import de.niclasl.herobrines_world.common.world.ModDimensions;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import de.niclasl.herobrines_world_api.structure.StructureAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onTick(LevelTickEvent.Pre event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        MapVariables map = MapVariables.get(event.getLevel());

        if (map == null) {
            return;
        }

        int x = 1000;
        int z = 1000;

        int y = Math.max(
                63,
                level.getHeight(
                        Heightmap.Types.WORLD_SURFACE_WG,
                        x,
                        z
                )
        );

        if (level.dimension() == ModDimensions.HEROBRINE_REALM && !map.hasSpawnHerobrineTemple) {
            StructureAPI api = HWRegistries.structures();

            level.getServer().execute(() -> api.spawnStructure(
                    level,
                    HWStructureTypes.HEROBRINE_TEMPLE.id(),
                    new BlockPos(
                            x,
                            y,
                            z
                    )
            ));

            map.hasSpawnHerobrineTemple = true;
        }
    }
}