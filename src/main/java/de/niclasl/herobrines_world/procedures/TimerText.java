package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

public class TimerText {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return new java.text.DecimalFormat("####").format(entity.getData(ModVariables.PLAYER_VARIABLES).Day) + ":" + new java.text.DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Hour) + ":"
				+ new java.text.DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Minute) + ":" + new java.text.DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Second);
	}
}