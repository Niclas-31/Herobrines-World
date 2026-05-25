package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.common.season.SeasonManager;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber
public class SeasonEvents {
    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            SeasonManager.tick(level);
        }
    }
}