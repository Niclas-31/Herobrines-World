package de.niclasl.herobrines_world.common.leaderboard.season.type;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.database.PlayerData;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import de.niclasl.herobrines_world_api.leaderboard.RewardContext;
import de.niclasl.herobrines_world_api.leaderboard.RewardEntry;
import de.niclasl.herobrines_world_api.leaderboard.RewardType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.NonNull;

import java.sql.SQLException;

public class SoulsRewardType implements RewardType {

    @Override
    public @NonNull Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "souls");
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void apply(RewardContext context, RewardEntry entry) throws SQLException {

        ServerPlayer player = context.player();
        PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

        int amount = entry.amount();

        if (data.soulLevel >= SoulMath.HARD_CAP) {
            return;
        }

        data.souls += amount;

        while (data.soulLevel < SoulMath.HARD_CAP
                && data.souls >= SoulMath.getXPForLevel(data.soulLevel)) {

            data.souls -= SoulMath.getXPForLevel(data.soulLevel);
            data.soulLevel++;
        }

        if (data.soulLevel >= SoulMath.HARD_CAP) {
            data.soulLevel = SoulMath.HARD_CAP;
            data.souls = 0;
        }

        HerobrinesWorld.DATABASE.savePlayerData(data);
    }
}