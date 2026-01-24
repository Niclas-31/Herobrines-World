package de.niclasl.herobrines_world.network.manager;

import de.niclasl.herobrines_world.network.data.WirelessSenderData;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WirelessNetworkManager {

    private static final Map<BlockPos, WirelessSenderData> SENDERS = new HashMap<>();

    private WirelessNetworkManager() {}

    /* ================= REGISTRATION ================= */

    public static void registerSender(BlockPos pos, WirelessSenderData data) {
        SENDERS.put(pos, data);
    }

    public static void removeSender(BlockPos pos) {
        SENDERS.remove(pos);
    }

    /* ================= QUERY ================= */

    public static List<WirelessSenderData> getNetworksInRange(
            BlockPos origin,
            int maxDistance
    ) {
        return SENDERS.values().stream()
                .filter(s -> s.pos().closerThan(origin, maxDistance))
                .toList();
    }

    public static WirelessSenderData getSender(BlockPos sender) {
        return SENDERS.get(sender);
    }
}