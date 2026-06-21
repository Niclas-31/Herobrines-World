package de.niclasl.herobrines_world.common.leaderboard;

import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardAPI;
import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardEntry;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LeaderboardApiImpl implements LeaderboardAPI {

    private final LeaderboardStorage storage;

    public LeaderboardApiImpl(LeaderboardStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<LeaderboardEntry> getTop(String board, int limit) {

        return storage.getBoard(board).entrySet().stream()
                .sorted((a, b) -> {

                    LeaderboardData da = a.getValue();
                    LeaderboardData db = b.getValue();

                    int levelCompare = Integer.compare(db.getLevel(), da.getLevel());
                    if (levelCompare != 0) return levelCompare;

                    return Integer.compare(db.getSouls(), da.getSouls());
                })
                .limit(limit)
                .map(e -> {

                    UUID uuid = e.getKey();
                    LeaderboardData data = e.getValue();

                    String name = getName(uuid);

                    return new LeaderboardEntry(
                            uuid,
                            name,
                            data.getSouls(),
                            data.getLevel()
                    );
                })
                .toList();
    }

    @Override
    public int getRank(UUID player) {
        return storage.getBoard("default").entrySet().stream()
                .sorted((a, b) -> {

                    LeaderboardData da = a.getValue();
                    LeaderboardData db = b.getValue();

                    int levelCompare = Integer.compare(db.getLevel(), da.getLevel());
                    if (levelCompare != 0) return levelCompare;

                    return Integer.compare(db.getSouls(), da.getSouls());
                })
                .map(Map.Entry::getKey)
                .toList()
                .indexOf(player) + 1;
    }

    private String getName(UUID uuid) {
        assert ServerLifecycleHooks.getCurrentServer() != null;
        var player = ServerLifecycleHooks.getCurrentServer()
                .getPlayerList()
                .getPlayer(uuid);

        if (player != null) {
            return player.getName().getString();
        }

        return "Unknown";
    }
}