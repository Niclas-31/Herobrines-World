package de.niclasl.herobrines_world.common.leaderboard.season.type;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import de.niclasl.herobrines_world.common.util.variables.ModVariables;
import de.niclasl.herobrines_world_api.leaderboard.RewardContext;
import de.niclasl.herobrines_world_api.leaderboard.RewardEntry;
import de.niclasl.herobrines_world_api.leaderboard.RewardType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.NonNull;

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
    public void apply(RewardContext context, RewardEntry entry) {

        ServerPlayer player = context.player();
        var vars = player.getData(ModVariables.PLAYER_VARIABLES);

        int amount = entry.amount();

        if (vars.soulLevel >= SoulMath.HARD_CAP) {
            return;
        }

        vars.souls += amount;

        while (vars.soulLevel < SoulMath.HARD_CAP
                && vars.souls >= SoulMath.getXPForLevel(vars.soulLevel)) {

            vars.souls -= SoulMath.getXPForLevel(vars.soulLevel);
            vars.soulLevel++;
        }

        if (vars.soulLevel >= SoulMath.HARD_CAP) {
            vars.soulLevel = SoulMath.HARD_CAP;
            vars.souls = 0;
        }

        vars.markSyncDirty(player);
    }
}