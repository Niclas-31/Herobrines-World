package de.niclasl.herobrines_world.common.boss;

import net.minecraft.server.level.ServerLevel;

public class BossManager {

    private static final BossManager instance = new BossManager();

    public static BossManager get() {
        return instance;
    }

    public BossFightManager getFightManager(ServerLevel level) {
        return BossFightManager.get(level);
    }
}