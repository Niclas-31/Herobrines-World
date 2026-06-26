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

    public static void generate(ServerLevel serverLevel, SeasonRewardStorage storage) {
        List<ServerPlayer> players = serverLevel.getServer().getPlayerList().getPlayers();

        List<LeaderboardEntry> leaderboard = new ArrayList<>();

        for (ServerPlayer p : players) {
            var vars = p.getData(ModVariables.PLAYER_VARIABLES);

            int souls = vars.souls;
            int level = vars.soulLevel;

            leaderboard.add(new LeaderboardEntry(p.getUUID(), p.getGameProfile().name(), souls, level));
        }

        leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::value).reversed());

        for (int i = 0; i < leaderboard.size(); i++) {

            if (i >= 50) break;

            LeaderboardEntry entry = leaderboard.get(i);

            ServerPlayer player = serverLevel.getServer()
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