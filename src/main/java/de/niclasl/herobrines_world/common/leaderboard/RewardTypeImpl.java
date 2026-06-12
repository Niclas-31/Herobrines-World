package de.niclasl.herobrines_world.common.leaderboard;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderboard.season.type.SoulsRewardType;
import de.niclasl.herobrines_world.common.leaderboard.season.type.XpRewardType;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardType;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.resources.Identifier;

public class RewardTypeImpl {

    public static final RewardType SOULS = HWRegistries.REWARD_TYPES.put(
            Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "souls"),
            new SoulsRewardType()
    );

    public static final RewardType XP = HWRegistries.REWARD_TYPES.put(
            Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "xp"),
            new XpRewardType()
    );
}