package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import de.niclasl.herobrines_world.network.ModVariables;

public class ControllVariable {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(ModVariables.PLAYER_VARIABLES).TimerActive) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.TimerActive = false;
				_vars.markSyncDirty();
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Timer stopped"), true);
		} else {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.TimerActive = true;
				_vars.markSyncDirty();
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Timer continued"), true);
		}
	}
}