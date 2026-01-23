package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.CommandSourceStack;

import de.niclasl.herobrines_world.network.ModVariables;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class AddLevels {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.Soul_Level = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Level + DoubleArgumentType.getDouble(arguments, "souls");
			_vars.markSyncDirty();
		}
	}
}