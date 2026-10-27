package de.niclasl.herobrines_world.common.boss;

import de.niclasl.herobrines_world_api.boss.*;
import de.niclasl.herobrines_world_api.event.BossSpawnEvent;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;

public class BossSpawner {

    public static void spawn(ServerLevel level, BlockPos pos, Identifier bossId) {
        BossFactory factory = HWRegistries.BOSSES.get(bossId);

        if (factory == null) {
            throw new IllegalStateException(
                    "Unknown boss: " + bossId
            );
        }

        IBoss boss = factory.create(level);

        if (!(boss instanceof Boss actualBoss)) {
            throw new IllegalStateException(
                    "Registered boss is not a Boss: " + bossId
            );
        }

        actualBoss.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        BossContext bossContext = new BossContext(level, actualBoss, BossFightState.STARTING);
        BossFightManager manager = BossFightManager.get(level);

        actualBoss.setBossContext(bossContext);

        actualBoss.setHealth(1.0F);

        level.addFreshEntity(actualBoss);

        manager.setState(BossFightState.STARTING);

        NeoForge.EVENT_BUS.post(new BossSpawnEvent(level, pos, actualBoss.getBossType(), actualBoss));
    }
}