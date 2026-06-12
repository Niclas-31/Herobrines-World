package de.niclasl.herobrines_world.common.leaderboard.season;

import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardEntry;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardEntry;
import de.niclasl.herobrines_world.common.network.ModVariables;
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
            leaderboard.add(new LeaderboardEntry(p.getUUID(), p.getGameProfile().name(), souls));
        }

        leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::value).reversed());

        for (int i = 0; i < leaderboard.size(); i++) {

            if (i >= 50) break;

            LeaderboardEntry entry = leaderboard.get(i);

            ServerPlayer player = level.getServer()
                    .getPlayerList()
                    .getPlayer(entry.player());

            if (player == null) continue;

            int rank = i + 1;

            List<RewardEntry> rewards =
                    RewardEngine.buildRewardsForRank(rank);

            storage.setRewards(entry.player(), rewards);
        }
    }
}