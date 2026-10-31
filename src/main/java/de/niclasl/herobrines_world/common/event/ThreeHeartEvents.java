package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.gamerules.ModGameRules;
import de.niclasl.herobrines_world.common.util.variables.MapVariables;
import de.niclasl.herobrines_world.common.util.variables.ModVariables;
import de.niclasl.herobrines_world.common.util.variables.PlayerVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class ThreeHeartEvents {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

        if (player.level().getLevelData().isHardcore()) return;

        if (!player.level().getGameRules().get(ModGameRules.THREE_HEARTS)) return;

        vars.hearts = Math.max(0, vars.hearts - 1);

        if (vars.hearts == 0) {
            player.setGameMode(GameType.SPECTATOR);
        }

        vars.markSyncDirty(player);
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        LevelAccessor var2 = event.getLevel();
        if (var2 instanceof ServerLevel level) {
            if (level.getLevelData().isHardcore()) {
                level.getGameRules().set(ModGameRules.THREE_HEARTS, false, level.getServer());
                MapVariables vars = level.getServer().getDataStorage().computeIfAbsent(MapVariables.TYPE);
                vars.threeHearts = false;
                vars.markSyncDirty();
            } else {
                MapVariables vars = level.getServer().getDataStorage().computeIfAbsent(MapVariables.TYPE);
                vars.threeHearts = level.getGameRules().get(ModGameRules.THREE_HEARTS);
                vars.markSyncDirty();
            }
        }
    }
}