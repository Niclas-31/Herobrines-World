package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

public class EnchantmentLevel2 {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.enchantment_level = 2;
			_vars.markSyncDirty();
		}
	}
}