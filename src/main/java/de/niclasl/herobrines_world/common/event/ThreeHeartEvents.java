package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.Config;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.ModVariables;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class ThreeHeartEvents {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

        if (player.level().getLevelData().isHardcore()) return;

        if (!vars.threeHearts) return;

        vars.hearts = Math.max(0, vars.hearts - 1);
        vars.markSyncDirty(player);

        if (vars.hearts <= 0) {
            player.setGameMode(GameType.SPECTATOR);
        }
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();

            if (server.isHardcore()) return;
            if (server.isDedicatedServer()) return;

            for (ServerPlayer player : serverLevel.players()) {
                ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

                vars.threeHearts = Config.THREE_HEARTS.getAsBoolean();
                vars.markSyncDirty(player);
            }
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server != null) {

            if (server.isHardcore()) return;
            if (server.isDedicatedServer()) return;

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

                vars.threeHearts = Config.THREE_HEARTS.getAsBoolean();
                vars.markSyncDirty(player);
            }
        }
    }
}