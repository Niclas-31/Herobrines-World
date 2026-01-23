package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;

import de.niclasl.herobrines_world.init.ModGameRules;

public class On {
	public static void execute(LevelAccessor world) {
		if (!(world instanceof ServerLevel _serverLevelGR0 && _serverLevelGR0.getGameRules().getBoolean(ModGameRules.CAN_LOOT_BOX_SPAWN))) {
			if (world instanceof ServerLevel _serverLevel)
				_serverLevel.getGameRules().getRule(ModGameRules.CAN_LOOT_BOX_SPAWN).set(true, world.getServer());
		}
	}
}