package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import de.niclasl.herobrines_world.network.ModVariables;

public class QueryLevels {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((entity.getDisplayName().getString() + " has " + new java.text.DecimalFormat("####").format(entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Level) + " soul levels")), false);
	}
}