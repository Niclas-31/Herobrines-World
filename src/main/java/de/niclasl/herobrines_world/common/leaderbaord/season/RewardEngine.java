package de.niclasl.herobrines_world.common.leaderbaord.season;

import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;
import de.niclasl.herobrines_world.common.leaderbaord.RewardType;

import java.util.ArrayList;
import java.util.List;

public class RewardEngine {

    public static List<RewardEntry> buildRewardsForRank(int rank) {

        if (rank > 50) return List.of();

        double m = Math.exp(-0.14 * (rank - 1));

        List<RewardEntry> rewards = new ArrayList<>();

        rewards.add(new RewardEntry(RewardType.SOULS, (int)(1000 * m)));
        rewards.add(new RewardEntry(RewardType.XP, (int)(500 * m)));

        return rewards;
    }
}