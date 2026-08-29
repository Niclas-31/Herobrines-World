package de.niclasl.herobrines_world.common.util.variables;

import com.mojang.serialization.Codec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MapVariables extends SavedData {
    public static final Codec<MapVariables> CODEC = CompoundTag.CODEC.xmap(
            tag -> {
                MapVariables instance = new MapVariables();
                instance.read(tag);
                return instance;
            }, instance -> instance.save(new CompoundTag())
    );
    public static final SavedDataType<MapVariables> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "map_variables"),
                    MapVariables::new, CODEC);
    boolean syncDirty = false;

    public boolean isHerobrineDead = false;
    public long seasonStart = 0;
    public long seasonEnd = 0;
    public long nextSeasonStart = 0;
    public long nextSeasonEnd = 0;
    public boolean seasonEndedHandled = false;
    public List<LeaderboardEntry> frozenLeaderboard = new ArrayList<>();
    public boolean hasSpawnHerobrineTemple = false;

    public void read(CompoundTag nbt) {
        isHerobrineDead = nbt.getBooleanOr("HerobrineDead", false);
        seasonStart = nbt.getLongOr("seasonStart", 0);
        seasonEnd = nbt.getLongOr("seasonEnd", 0);
        nextSeasonStart = nbt.getLongOr("nextSeasonStart", 0);
        nextSeasonEnd = nbt.getLongOr("nextSeasonEnd", 0);
        seasonEndedHandled = nbt.getBooleanOr("seasonEndedHandled", false);
        frozenLeaderboard = new ArrayList<>();

        ListTag list = nbt.getListOrEmpty("frozenLeaderboard");

        for (int i = 0; i < list.size(); i++) {

            CompoundTag e = list.getCompoundOrEmpty(i);

            frozenLeaderboard.add(
                    new LeaderboardEntry(
                            UUID.fromString(e.getStringOr("uuid", "")),
                            e.getStringOr("name", ""),
                            e.getIntOr("value", 0),
                            e.getIntOr("level", 0)
                    )
            );
        }
        hasSpawnHerobrineTemple = nbt.getBooleanOr("hasSpawnHerobrineTemple", false);
    }

    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean("HerobrineDead", isHerobrineDead);
        nbt.putLong("seasonStart", seasonStart);
        nbt.putLong("seasonEnd", seasonEnd);
        nbt.putLong("nextSeasonStart", nextSeasonStart);
        nbt.putLong("nextSeasonEnd", nextSeasonEnd);
        nbt.putBoolean("seasonEndedHandled", seasonEndedHandled);
        ListTag list = new ListTag();

        for (LeaderboardEntry e : frozenLeaderboard) {

            CompoundTag tag = new CompoundTag();

            tag.putString("uuid", e.player().toString());
            tag.putString("name", e.playerName());
            tag.putInt("value", e.value());
            tag.putInt("level", e.level());

            list.add(tag);
        }

        nbt.put("frozenLeaderboard", list);
        nbt.putBoolean("hasSpawnHerobrineTemple", hasSpawnHerobrineTemple);
        return nbt;
    }

    public void markSyncDirty() {
        this.setDirty();
        this.syncDirty = true;
    }

    public static MapVariables clientSide = new MapVariables();

    public static MapVariables get(LevelAccessor world) {
        if (world instanceof ServerLevelAccessor serverLevelAccessor) {
            return Objects.requireNonNull(serverLevelAccessor.getLevel().getServer().getLevel(Level.OVERWORLD)).getDataStorage().computeIfAbsent(MapVariables.TYPE);
        } else {
            return clientSide;
        }
    }
}