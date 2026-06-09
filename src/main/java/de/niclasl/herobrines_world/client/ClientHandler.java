package de.niclasl.herobrines_world.client;

import de.niclasl.herobrines_world.client.screen.RewardScreen;
import de.niclasl.herobrines_world.client.screen.SeasonBreakScreen;
import de.niclasl.herobrines_world.client.screen.SoulLeaderboardScreen;
import de.niclasl.herobrines_world.client.screen.WaypointScreen;
import de.niclasl.herobrines_world.common.leaderbaord.season.SeasonManager;
import de.niclasl.herobrines_world.common.leaderbaord.season.cache.ClientCache;
import de.niclasl.herobrines_world.common.network.message.OpenRewardScreenPacket;
import de.niclasl.herobrines_world.common.network.message.OpenWaypointScreenPacket;
import de.niclasl.herobrines_world.common.network.message.SyncLeaderboardPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.UUID;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {

    public static void handleOpenLeaderboard(SyncLeaderboardPacket packet) {

        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;

        if (level == null || mc.player == null) return;

        boolean seasonActive = SeasonManager.isSeasonActive(level);
        UUID uuid = mc.player.getUUID();

        boolean claimed = ClientCache.isClaimed(uuid);

        if (seasonActive) {
            mc.setScreen(new SoulLeaderboardScreen(packet.entries()));
            return;
        }

        if (claimed) {
            mc.setScreen(new SeasonBreakScreen());
        } else {
            mc.setScreen(new SoulLeaderboardScreen(packet.entries()));
        }
    }

    public static void handleOpenReward(OpenRewardScreenPacket packet) {
        Minecraft.getInstance().setScreen(new RewardScreen(packet.rewards()));
    }

    public static void handleOpenWaypointScreen(OpenWaypointScreenPacket packet) {
        Minecraft.getInstance().setScreen(new WaypointScreen(packet.stack()));
    }
}