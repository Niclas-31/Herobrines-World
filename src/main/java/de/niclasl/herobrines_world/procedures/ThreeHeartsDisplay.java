package de.niclasl.herobrines_world.procedures;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.server.level.ServerLevel;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.init.ModGameRules;

@EventBusSubscriber
public class ThreeHeartsDisplay {

	@SubscribeEvent
	public static void onWorldLoad(net.neoforged.neoforge.event.level.LevelEvent.Load event) {
		if (!(event.getLevel() instanceof ServerLevel level)) return;

		if (level.getLevelData().isHardcore()) {
			level.getGameRules()
					.getRule(ModGameRules.THREE_HEARTS)
					.set(false, level.getServer());

			ModVariables.MapVariables.get(level).ThreeHearts = false;
			ModVariables.MapVariables.get(level).markSyncDirty();
			return;
		}

        ModVariables.MapVariables.get(level).ThreeHearts = level.getGameRules().getBoolean(ModGameRules.THREE_HEARTS);
		ModVariables.MapVariables.get(level).markSyncDirty();
	}
}