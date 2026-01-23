package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class TimerUpdate {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event.getEntity());
	}

	private static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(ModVariables.PLAYER_VARIABLES).TimerActive) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Ticks = entity.getData(ModVariables.PLAYER_VARIABLES).Ticks + 1;
				_vars.markSyncDirty();
			}
			if (entity.getData(ModVariables.PLAYER_VARIABLES).Ticks == 20) {
				{
					ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
					_vars.Ticks = 0;
					_vars.Second = entity.getData(ModVariables.PLAYER_VARIABLES).Second + 1;
					_vars.markSyncDirty();
				}
				if (entity.getData(ModVariables.PLAYER_VARIABLES).Second >= 60) {
					{
						ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
						_vars.Second = 0;
						_vars.Minute = entity.getData(ModVariables.PLAYER_VARIABLES).Minute + 1;
						_vars.markSyncDirty();
					}
					if (entity.getData(ModVariables.PLAYER_VARIABLES).Minute >= 60) {
						{
							ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
							_vars.Minute = 0;
							_vars.Hour = entity.getData(ModVariables.PLAYER_VARIABLES).Hour + 1;
							_vars.markSyncDirty();
						}
						if (entity.getData(ModVariables.PLAYER_VARIABLES).Hour >= 24) {
							{
								ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
								_vars.Hour = 0;
								_vars.Day = entity.getData(ModVariables.PLAYER_VARIABLES).Day + 1;
								_vars.markSyncDirty();
							}
						}
					}
				}
			}
		}
	}
}