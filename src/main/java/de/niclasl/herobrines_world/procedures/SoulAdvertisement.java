package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

import java.text.DecimalFormat;

public class SoulAdvertisement {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Level);
	}
}
