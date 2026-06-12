package de.niclasl.herobrines_world.common.leaderboard.season.type;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardContext;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardEntry;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class SoulsRewardType implements RewardType {

    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "souls");
    }

    @Override
    public void apply(RewardContext context, RewardEntry entry) {
        ServerPlayer player = context.player();
        var data = player.getData(ModVariables.PLAYER_VARIABLES);
        data.Souls += entry.amount();
        data.markSyncDirty(player);
    }
}