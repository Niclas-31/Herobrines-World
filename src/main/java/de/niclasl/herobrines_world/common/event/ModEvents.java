package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.boss.BossArenaStorage;
import de.niclasl.herobrines_world.common.boss.BossFightManager;
import de.niclasl.herobrines_world.common.structure.boss.HWStructureTypes;
import de.niclasl.herobrines_world.common.util.variables.MapVariables;
import de.niclasl.herobrines_world.common.world.ModDimensions;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import de.niclasl.herobrines_world_api.structure.ArenaBox;
import de.niclasl.herobrines_world_api.structure.BossArena;
import de.niclasl.herobrines_world_api.structure.StructureAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.jspecify.annotations.NonNull;

import java.util.List;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BossFightManager manager = BossFightManager.get(level);
        BossArenaStorage storage = BossArenaStorage.get(level);

        manager.setArena(storage.getArena(HWStructureTypes.HEROBRINE_TEMPLE.id()));
    }

    @SubscribeEvent
    public static void onTick(LevelTickEvent.Pre event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        MapVariables map = MapVariables.get(level);

        if (map == null) {
            return;
        }

        int x = 1000;
        int z = 1000;

        int y = Math.max(
                63,
                level.getHeight(
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        x,
                        z
                )
        );

        if (level.dimension() == ModDimensions.HEROBRINE_REALM && !map.hasSpawnHerobrineTemple) {
            StructureAPI api = HWRegistries.structures();

            Identifier structureId = HWStructureTypes.HEROBRINE_TEMPLE.id();

            level.getServer().execute(() -> api.spawnStructure(
                    level,
                    structureId,
                    new BlockPos(
                            x,
                            y,
                            z
                    )
            ));

            BossArenaStorage storage = BossArenaStorage.get(level);
            BossFightManager manager = BossFightManager.get(level);

            BossArena arena = getArena(x, y, z);

            storage.setBoxes(structureId, arena);
            manager.setArena(arena);

            map.hasSpawnHerobrineTemple = true;
        }
    }

    private static @NonNull BossArena getArena(int x, int y, int z) {
        List<ArenaBox> boxes = List.of(
                new ArenaBox(21, 0, 0, 25, 20, 0),
                new ArenaBox(20, 0, 1, 26, 20, 1),
                new ArenaBox(18, 0, 2, 28, 20, 2),
                new ArenaBox(16, 0, 3, 30, 20, 3),
                new ArenaBox(14, 0, 4, 32, 20, 4),
                new ArenaBox(12, 0, 5, 34, 20, 5),
                new ArenaBox(10, 0, 6, 36, 20, 6),
                new ArenaBox(8, 0, 7, 38, 20, 7),
                new ArenaBox(7, 0, 8, 39, 20, 9),
                new ArenaBox(6, 0, 10, 40, 20, 11),
                new ArenaBox(5, 0, 12, 41, 20, 13),
                new ArenaBox(4, 0, 14, 42, 20, 15),
                new ArenaBox(3, 0, 16, 43, 20, 17),
                new ArenaBox(2, 0, 18, 44, 20, 19),
                new ArenaBox(1, 0, 20, 45, 20, 21),
                new ArenaBox(0, 0, 22, 46, 20, 24),
                new ArenaBox(1, 0, 25, 45, 20, 26),
                new ArenaBox(2, 0, 27, 44, 20, 28),
                new ArenaBox(3, 0, 29, 43, 20, 30),
                new ArenaBox(4, 0, 31, 42, 20, 32),
                new ArenaBox(5, 0, 33, 41, 20, 34),
                new ArenaBox(6, 0, 35, 40, 20, 36),
                new ArenaBox(7, 0, 37, 39, 20, 38),
                new ArenaBox(8, 0, 39, 38, 20, 39),
                new ArenaBox(10, 0, 40, 36, 20, 40),
                new ArenaBox(12, 0, 41, 34, 20, 41),
                new ArenaBox(14, 0, 42, 32, 20, 42),
                new ArenaBox(16, 0, 43, 30, 20, 43),
                new ArenaBox(18, 0, 44, 28, 20, 44),
                new ArenaBox(20, 0, 45, 26, 20, 45),
                new ArenaBox(22, 0, 46, 24, 20, 46)
        );

        return new BossArena(new BlockPos(x, y, z), boxes);
    }
}