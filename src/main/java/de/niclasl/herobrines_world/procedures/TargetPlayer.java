package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class TargetPlayer {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		if ((commandParameterEntity(arguments)) == (commandParameterEntity(arguments)) && !entity.isInvisible()) {
			entity.setInvisible(true);
		} else if ((commandParameterEntity(arguments)) == (commandParameterEntity(arguments)) && entity.isInvisible()) {
			entity.setInvisible(false);
		}
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments) {
		try {
			return EntityArgument.getEntity(arguments, "targets");
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}