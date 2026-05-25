package de.niclasl.herobrines_world.common.season;

import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;

import java.util.ArrayList;
import java.util.List;

public class RewardEngine {

    public static double multiplier(int rank) {
        return Math.exp(-0.14 * (rank - 1));
    }

    public static List<RewardEntry> buildRewardsForRank(int rank) {

        if (rank > 50) return List.of();

        double m = multiplier(rank);

        List<RewardEntry> rewards = new ArrayList<>();

        rewards.add(new RewardEntry("Souls", (int)(1000 * m)));
        rewards.add(new RewardEntry("XP", (int)(500 * m)));

        return rewards;
    }
}