package de.niclasl.herobrines_world.common.leaderboard.season.type;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardContext;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardEntry;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardType;
import net.minecraft.resources.Identifier;

public class XpRewardType implements RewardType {

    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "xp");
    }

    @Override
    public void apply(RewardContext context, RewardEntry entry) {
        context.player().giveExperiencePoints(entry.amount());
    }
}