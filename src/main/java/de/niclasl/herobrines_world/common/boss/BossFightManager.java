package de.niclasl.herobrines_world.common.boss;

import de.niclasl.herobrines_world_api.boss.BossFightState;
import de.niclasl.herobrines_world_api.structure.BossArena;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class BossFightManager {

    private static final Map<ServerLevel, BossFightManager> MANAGERS = new WeakHashMap<>();

    private final BossArenaStorage storage;

    private final Map<Identifier, BossArena> arenas = new HashMap<>();

    private Identifier activeArenaId;
    private BossFightState state;

    private BossFightManager(BossArenaStorage storage) {
        this.storage = storage;
        this.state = storage.getState();
    }

    public static BossFightManager get(ServerLevel level) {
        return MANAGERS.computeIfAbsent(level, ignored -> {
            BossArenaStorage storage = level.getDataStorage()
                    .computeIfAbsent(BossArenaStorage.TYPE);

            return new BossFightManager(storage);
        });
    }

    public Map<Identifier, BossArena> getArenas() {
        return arenas;
    }

    public void setArenas(Map<Identifier, BossArena> arenas) {
        this.arenas.clear();
        this.arenas.putAll(arenas);
    }

    public void setArena(Identifier structureId, BossArena arena) {
        arenas.put(structureId, arena);
    }

    public BossArena getActiveArena() {
        if (activeArenaId == null) {
            return null;
        }

        return arenas.get(activeArenaId);
    }

    public BossFightState getState() {
        return state;
    }

    public void setState(BossFightState state) {
        this.state = state;
        storage.setState(state);
    }

    public boolean isActive() {
        return state == BossFightState.ACTIVE;
    }

    public void startFight(Identifier structureId) {
        if (!arenas.containsKey(structureId)) {
            return;
        }

        activeArenaId = structureId;
        state = BossFightState.ACTIVE;
    }

    public void endFight() {
        state = BossFightState.NOT_STARTED;
        activeArenaId = null;
    }
}