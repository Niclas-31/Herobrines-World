package de.niclasl.herobrines_world.common.boss;

public class BossManager {

    private static final BossManager instance = new BossManager();

    public static BossManager get() {
        return instance;
    }

    public BossFightManager getFightManager() {
        return new BossFightManager();
    }
}