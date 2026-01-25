package de.niclasl.herobrines_world.network.manager;

import de.niclasl.herobrines_world.network.data.WirelessSenderData;
import net.minecraft.core.BlockPos;

import java.util.Collection;
import java.util.HashMap;
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

    public static WirelessSenderData getSender(BlockPos sender) {
        return SENDERS.get(sender);
    }

    public static Collection<WirelessSenderData> getAllSenders() {
        return SENDERS.values();
    }
}
