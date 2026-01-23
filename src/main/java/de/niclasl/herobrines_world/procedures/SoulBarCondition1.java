package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

public class SoulBarCondition1 {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
        return entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 1 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 10;
    }
}