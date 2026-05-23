package de.niclasl.herobrines_world.network.manager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProgressManager extends SavedData {

    private static final Map<UUID, Integer> progress = new HashMap<>();

    public static final SavedDataType<PlayerProgressManager> TYPE = new SavedDataType<>("player_progress", ctx -> new PlayerProgressManager(), ctx -> CompoundTag.CODEC.xmap(tag -> {
        PlayerProgressManager instance = new PlayerProgressManager();
        instance.read(tag);
        return instance;
    }, instance -> instance.save(new CompoundTag())));

    public static PlayerProgressManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public int getProgress(UUID uuid) {
        return progress.getOrDefault(uuid, 0);
    }

    public void addProgress(UUID uuid, int value) {
        progress.put(uuid, getProgress(uuid) + value);
    }

    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();

        for (Map.Entry<UUID, Integer> entry : progress.entrySet()) {
            CompoundTag playerTag = new CompoundTag();

            playerTag.putString("UUID", entry.getKey().toString());
            playerTag.putInt("Progress", entry.getValue());

            list.add(playerTag);
        }

        nbt.put("Players", list);

        return nbt;
    }

    public void read(CompoundTag nbt) {
        ListTag list = nbt.getListOrEmpty("Players");

        for (Tag t : list) {
            CompoundTag playerTag = (CompoundTag) t;

            String raw = playerTag.getStringOr("UUID", "");

            if (raw.isEmpty()) {
                continue;
            }

            try {
                UUID uuid = UUID.fromString(raw);
                int value = playerTag.getIntOr("Progress", 0);

                progress.put(uuid, value);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID in SavedData: " + raw);
            }
        }
    }
}