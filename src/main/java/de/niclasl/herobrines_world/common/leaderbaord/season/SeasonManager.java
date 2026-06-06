package de.niclasl.herobrines_world.common.leaderbaord.season;

import de.niclasl.herobrines_world.common.leaderbaord.LeaderboardEntry;
import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;
import de.niclasl.herobrines_world.common.network.ModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeasonManager {

    public static void initialize(ServerLevel level) {

        ModVariables.WorldVariables data =
                ModVariables.WorldVariables.get(level);

        if (data.seasonStart != 0 && data.seasonEnd > 0) return;

        long now = System.currentTimeMillis();

        long seasonDuration = 30L * 24 * 60 * 60 * 1000;
        long seasonDuration2 = 2L * 60  * 1000;

        data.seasonStart = 0;
        data.seasonEnd = 0;

        data.nextSeasonStart = now + 10000;
        data.nextSeasonEnd = data.nextSeasonStart + seasonDuration2;

        data.markSyncDirty();
    }

    public static void tick(ServerLevel level) {

        var data = ModVariables.WorldVariables.get(level);

        long now = System.currentTimeMillis();
        long seasonDuration = 30L * 24 * 60 * 60 * 1000;
        long seasonDuration2 = 2L * 60  * 1000;
        long offseason = 2L * 24 * 60 * 60 * 1000;

        if (data.seasonStart == 0 && now >= data.nextSeasonStart) {

            data.seasonStart = now;
            data.seasonEnd = now + seasonDuration2;

            SeasonRewardStorage storage =
                    SeasonRewardStorage.get(level);

            SeasonRewardGenerator.generate(level, storage);

            data.markSyncDirty();
        }

        if (data.seasonStart != 0
                && now >= data.seasonEnd
                && !data.seasonEndedHandled) {

            data.seasonEndedHandled = true;

            List<LeaderboardEntry> snapshot = buildLeaderboard(level);
            data.frozenLeaderboard = List.copyOf(snapshot);

            SeasonRewardStorage storage =
                    SeasonRewardStorage.get(level);

            storage.resetSeason();
            SeasonRewardGenerator.generate(level, storage);

            data.nextSeasonStart = now + offseason;
            data.nextSeasonEnd = data.nextSeasonStart + seasonDuration2;

            data.markSyncDirty();
        }

        if (data.seasonEndedHandled && now >= data.nextSeasonStart) {

            data.seasonStart = now;
            data.seasonEnd = now + seasonDuration2;

            SeasonRewardStorage storage =
                    SeasonRewardStorage.get(level);

            storage.resetSeason();
            SeasonRewardGenerator.generate(level, storage);

            data.seasonEndedHandled = false;

            data.markSyncDirty();
        }
    }

    public static List<LeaderboardEntry> getLeaderboard(ServerLevel level) {
        var data = ModVariables.WorldVariables.get(level);

        if (isSeasonActive(level)) {
            return buildLeaderboard(level);
        }

        return data.frozenLeaderboard;
    }

    public static List<LeaderboardEntry> buildLeaderboard(ServerLevel level) {
        List<ServerPlayer> players = level.getServer().getPlayerList().getPlayers();

        List<LeaderboardEntry> entries = new ArrayList<>();

        for (ServerPlayer p : players) {
            int souls = p.getData(ModVariables.PLAYER_VARIABLES).Souls;

            entries.add(new LeaderboardEntry(
                    p.getUUID(),
                    p.getGameProfile().name(),
                    souls
            ));
        }

        entries.sort(Comparator.comparingInt(LeaderboardEntry::value).reversed());

        return entries;
    }

    public static int getCurrentRank(ServerPlayer target, ServerLevel level) {

        List<LeaderboardEntry> list = buildLeaderboard(level);

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).uuid().equals(target.getUUID())) {
                return i + 1;
            }
        }

        return -1;
    }

    public static List<RewardEntry> getPreviewRewards(ServerPlayer player, ServerLevel level) {

        int rank = getCurrentRank(player, level);

        if (rank <= 0 || rank > 50) return List.of();

        return RewardEngine.buildRewardsForRank(rank);
    }

    public static boolean isSeasonActive(LevelAccessor level) {
        return System.currentTimeMillis() <
                ModVariables.WorldVariables.get(level).seasonEnd;
    }

    public static long getNextSeasonStart(LevelAccessor level) {
        return ModVariables.WorldVariables.get(level).nextSeasonStart;
    }

    public static long getSeasonEnd(LevelAccessor level) {
        return ModVariables.WorldVariables.get(level).seasonEnd;
    }
}