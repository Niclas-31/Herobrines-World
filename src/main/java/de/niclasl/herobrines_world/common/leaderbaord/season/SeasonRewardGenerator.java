package de.niclasl.herobrines_world.common.leaderbaord.season;

import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world.common.leaderbaord.LeaderboardEntry;
import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeasonRewardGenerator {

    public static void generate(ServerLevel level, SeasonRewardStorage storage) {

        List<ServerPlayer> players = level.getServer().getPlayerList().getPlayers();

        List<LeaderboardEntry> leaderboard = new ArrayList<>();

        for (ServerPlayer p : players) {

            int souls = p.getData(ModVariables.PLAYER_VARIABLES).Souls;

            leaderboard.add(new LeaderboardEntry(p.getGameProfile().name(), souls));
        }

        leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::value).reversed());

        for (int i = 0; i < leaderboard.size(); i++) {

            LeaderboardEntry entry = leaderboard.get(i);

            int rank = i + 1;

            if (rank > 50) break;

            ServerPlayer player = level.getServer().getPlayerList().getPlayerByName(entry.playerName());

            if (player == null) continue;

            List<RewardEntry> rewards = RewardEngine.buildRewardsForRank(rank);

            storage.setRewards(player.getUUID(), rewards);
        }
    }
}