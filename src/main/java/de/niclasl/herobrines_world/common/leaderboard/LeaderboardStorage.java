package de.niclasl.herobrines_world.common.leaderboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeaderboardStorage {

    private final Map<String, Map<UUID, LeaderboardData>> boards = new HashMap<>();

    public void setScore(String board, UUID player, int souls, int level) {
        boards
                .computeIfAbsent(board, b -> new HashMap<>())
                .put(player, new LeaderboardData(souls, level));
    }

    public LeaderboardData get(String board, UUID player) {
        return boards
                .getOrDefault(board, Map.of())
                .get(player);
    }

    public Map<UUID, LeaderboardData> getBoard(String board) {
        return boards.getOrDefault(board, Map.of());
    }
}