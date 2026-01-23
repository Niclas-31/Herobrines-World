package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class OnPlayerLeaveTheWorld {
	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		execute(event.getEntity());
	}

	private static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.PinUnlocked = false;
			_vars.markSyncDirty();
		}
	}
}