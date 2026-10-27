package de.niclasl.herobrines_world.common.boss;

import de.niclasl.herobrines_world_api.boss.BossFightState;
import de.niclasl.herobrines_world_api.structure.BossArena;
import net.minecraft.server.level.ServerLevel;

import java.util.Map;
import java.util.WeakHashMap;

public class BossFightManager {

    private static final Map<ServerLevel, BossFightManager> MANAGERS = new WeakHashMap<>();

    private BossArena arena;
    private BossFightState state = BossFightState.NOT_STARTED;

    public static BossFightManager get(ServerLevel level) {
        return MANAGERS.computeIfAbsent(level, ignored -> new BossFightManager());
    }

    public BossArena getArena() {
        return arena;
    }

    public void setArena(BossArena arena) {
        this.arena = arena;
    }

    public BossFightState getState() {
        return state;
    }

    public void setState(BossFightState state) {
        this.state = state;
    }

    public boolean isActive() {
        return state == BossFightState.ACTIVE;
    }
}