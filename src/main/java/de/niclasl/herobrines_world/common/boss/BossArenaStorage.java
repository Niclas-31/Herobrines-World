package de.niclasl.herobrines_world.common.boss;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.boss.BossFightState;
import de.niclasl.herobrines_world_api.structure.BossArena;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;

public class BossArenaStorage extends SavedData {

    public static final Codec<BossArenaStorage> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.unboundedMap(Identifier.CODEC, BossArena.CODEC).fieldOf("arenas")
                            .forGetter(storage -> storage.arenas),
                    BossFightState.CODEC
                            .optionalFieldOf("fight_state", BossFightState.NOT_STARTED)
                            .forGetter(BossArenaStorage::getState)
            ).apply(instance, BossArenaStorage::new));

    public static final SavedDataType<BossArenaStorage> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "boss_arenas"),
                    BossArenaStorage::new, CODEC);

    private final Map<Identifier, BossArena> arenas = new HashMap<>();
    private BossFightState state = BossFightState.NOT_STARTED;

    private BossArenaStorage() {}

    private BossArenaStorage(Map<Identifier, BossArena> arenas, BossFightState state) {
        this.arenas.putAll(arenas);
        this.state = state;
    }

    public BossFightState getState() {
        return state;
    }

    public void setState(BossFightState state) {
        if (this.state != state) {
            this.state = state;
            setDirty();
        }
    }

    public void setBoxes(Identifier structureId, BossArena arena) {
        arenas.put(structureId, arena);

        setDirty();
    }

    public Map<Identifier, BossArena> getArenas() {
        return Map.copyOf(arenas);
    }
}