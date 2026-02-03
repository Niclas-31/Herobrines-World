package de.niclasl.herobrines_world.procedures;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.CommandSourceStack;

import de.niclasl.herobrines_world.network.ModVariables;

import com.mojang.brigadier.context.CommandContext;

public class AddPoints {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		int max_points_per_Level;
		max_points_per_Level = 100;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.Soul_Current = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current + IntegerArgumentType.getInteger(arguments, "souls");
			_vars.markSyncDirty();
		}
		while (entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current > max_points_per_Level) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Soul_Current = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current - max_points_per_Level;
				_vars.Soul_Level = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Level + 1;
				_vars.markSyncDirty();
			}
		}
	}
}
