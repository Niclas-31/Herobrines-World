package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

public class HerobrineArmorAbilityControll {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(ModVariables.PLAYER_VARIABLES).AbilityActive) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.AbilityActive = false;
				_vars.markSyncDirty();
			}
			HerobrineArmorAbility.execute(entity);
		} else {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.AbilityActive = true;
				_vars.markSyncDirty();
			}
			HerobrineArmorAbility.execute(entity);
		}
	}
}