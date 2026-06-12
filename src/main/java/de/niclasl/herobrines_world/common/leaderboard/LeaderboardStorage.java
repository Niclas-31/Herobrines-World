package de.niclasl.herobrines_world.common.leaderboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeaderboardStorage {

    private final Map<String, Map<UUID, Integer>> boards = new HashMap<>();

    public void setScore(String board, UUID player, int score) {
        boards
                .computeIfAbsent(board, b -> new HashMap<>())
                .put(player, score);
    }

    public int getScore(String board, UUID player) {
        return boards
                .getOrDefault(board, Map.of())
                .getOrDefault(player, 0);
    }

    public Map<UUID, Integer> getBoard(String board) {
        return boards.getOrDefault(board, Map.of());
    }
}