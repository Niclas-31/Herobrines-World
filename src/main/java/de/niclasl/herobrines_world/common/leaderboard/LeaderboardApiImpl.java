package de.niclasl.herobrines_world.common.leaderboard;

import de.niclasl.herobrines_world.common.leaderboard.season.SeasonManager;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardAPI;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardEntry;

import java.util.List;
import java.util.UUID;

public class LeaderboardApiImpl implements LeaderboardAPI {

  @Override
  public List<LeaderboardEntry> getTop(int limit) {
    List<LeaderboardEntry> leaderboard = SeasonManager.getFrozenLeaderboard();

    return leaderboard.stream()
            .limit(Math.max(0, limit))
            .toList();
  }

  @Override
  public int getRank(UUID uuid) {
    List<LeaderboardEntry> leaderboard = SeasonManager.getFrozenLeaderboard();

    for (int i = 0; i < leaderboard.size(); i++) {
      if (leaderboard.get(i).uuid().equals(uuid)) {
        return i + 1;
      }
    }

    return 0;
  }
}