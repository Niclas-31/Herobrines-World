package de.niclasl.herobrines_world.common.leaderboard.season;

import de.niclasl.herobrines_world.common.leaderboard.RewardTypeImpl;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardEntry;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardType;

import java.util.ArrayList;
import java.util.List;

public class RewardEngine {

    public static List<RewardEntry> buildRewardsForRank(int rank) {

        if (rank > 50) return List.of();

        double m = Math.exp(-0.14 * (rank - 1));

        List<RewardEntry> rewards = new ArrayList<>();

        RewardType souls = RewardTypeImpl.SOULS;

        RewardType xp = RewardTypeImpl.XP;

        rewards.add(new RewardEntry(souls, (int) (1000 * m)));

        rewards.add(new RewardEntry(xp, (int) (500 * m)));

        return rewards;
    }
}