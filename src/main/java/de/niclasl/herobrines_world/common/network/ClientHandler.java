package de.niclasl.herobrines_world.common.network;

import de.niclasl.herobrines_world.client.screen.RewardScreen;
import de.niclasl.herobrines_world.client.screen.SeasonBreakScreen;
import de.niclasl.herobrines_world.client.screen.SoulLeaderboardScreen;
import de.niclasl.herobrines_world.common.network.message.OpenRewardScreenPacket;
import de.niclasl.herobrines_world.common.network.message.SyncLeaderboardPacket;
import de.niclasl.herobrines_world.common.season.SeasonManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {

    public static void handleOpenLeaderboard(SyncLeaderboardPacket packet) {
        Minecraft mc = Minecraft.getInstance();

        Level level = mc.level;

        if (level == null || mc.player == null) {
            return;
        }

        ModVariables.WorldVariables world = ModVariables.WorldVariables.get(level);

        if (SeasonManager.isSeasonActive(level)) {
            mc.setScreen(new SoulLeaderboardScreen(packet.entries()));
            return;
        }

        if (!packet.rewardsClaimed()) {
            mc.setScreen(new SoulLeaderboardScreen(world.frozenLeaderboard));
        } else {
            mc.setScreen(new SeasonBreakScreen());
        }
    }

    public static void handleOpenReward(OpenRewardScreenPacket packet) {
        Minecraft mc = Minecraft.getInstance();

        mc.setScreen(new RewardScreen(packet.rewards()));
    }
}