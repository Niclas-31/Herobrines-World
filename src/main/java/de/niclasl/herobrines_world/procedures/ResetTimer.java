package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import de.niclasl.herobrines_world.network.ModVariables;

public class ResetTimer {
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
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal("Timer reset"), true);
	}
}