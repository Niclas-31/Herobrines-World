package de.niclasl.herobrines_world.procedures;

import de.niclasl.herobrines_world.entity.custom.HerobrineBoss;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class PlayerDeath {

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            HerobrineBoss.onOwnerDeath(player);
        }
    }
}