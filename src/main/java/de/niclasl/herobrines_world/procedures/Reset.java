package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

public class Reset {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.Ticks = 0;
			_vars.Second = 0;
			_vars.Minute = 0;
			_vars.Hour = 0;
			_vars.Day = 0;
			_vars.markSyncDirty();
		}
	}
}