package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class EnchantenDisplayCondition {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event.getEntity());
	}

	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
        return entity.getData(ModVariables.PLAYER_VARIABLES).Enchanten;
    }
}