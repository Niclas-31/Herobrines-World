package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

public class HideButton {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.getData(ModVariables.PLAYER_VARIABLES).Hide) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Hide = true;
				_vars.markSyncDirty();
			}
		} else if (entity.getData(ModVariables.PLAYER_VARIABLES).Hide) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Hide = false;
				_vars.markSyncDirty();
			}
		}
	}
}