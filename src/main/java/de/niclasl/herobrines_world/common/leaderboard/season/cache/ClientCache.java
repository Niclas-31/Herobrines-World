package de.niclasl.herobrines_world.common.leaderboard.season.cache;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(Dist.CLIENT)
public class ClientCache {

    private static Set<UUID> claimed = new HashSet<>();

    public static void setClaimed(Set<UUID> newData) {
        claimed = new HashSet<>(newData);
    }

    public static boolean isClaimed(UUID uuid) {
        return claimed.contains(uuid);
    }

    public static Set<UUID> getClaimed() {
        return claimed;
    }
}