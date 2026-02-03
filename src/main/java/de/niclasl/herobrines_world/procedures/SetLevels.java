package de.niclasl.herobrines_world.procedures;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.CommandSourceStack;

import de.niclasl.herobrines_world.network.ModVariables;

import com.mojang.brigadier.context.CommandContext;

public class SetLevels {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.Soul_Level = IntegerArgumentType.getInteger(arguments, "souls");
			_vars.markSyncDirty();
		}
	}
}
