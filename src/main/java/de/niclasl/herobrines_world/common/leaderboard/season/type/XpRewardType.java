package de.niclasl.herobrines_world.common.leaderboard.season.type;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardContext;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardEntry;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardType;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class XpRewardType implements RewardType {

    @Override
    public @NonNull Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "xp");
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public void apply(RewardContext context, RewardEntry entry) {
        context.player().giveExperiencePoints(entry.amount());
    }
}