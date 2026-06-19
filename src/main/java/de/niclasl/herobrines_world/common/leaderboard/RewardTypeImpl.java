package de.niclasl.herobrines_world.common.leaderboard;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderboard.season.type.SoulsRewardType;
import de.niclasl.herobrines_world.common.leaderboard.season.type.XpRewardType;
import de.niclasl.herobrines_world_api.api.leaderboard.RewardType;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.resources.Identifier;

public class RewardTypeImpl {

    public static final RewardType SOULS = new SoulsRewardType();
    public static final RewardType XP = new XpRewardType();

    public static void register() {
        registerRewardType(id("souls"), SOULS);
        registerRewardType(id("xp"), XP);
    }

    public static void registerRewardType(Identifier id, RewardType mode) {
        for (RewardType existing : HWRegistries.REWARD_TYPES.values()) {

            if (existing.priority() == mode.priority()) {
                throw new IllegalStateException(
                        "Duplicate AccessMode priority "
                                + mode.priority()
                                + " between "
                                + existing.id()
                                + " and "
                                + mode.id()
                );
            }
        }

        HWRegistries.REWARD_TYPES.put(id, mode);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path);
    }
}