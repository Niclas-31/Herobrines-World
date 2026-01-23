package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.CommandSourceStack;

import de.niclasl.herobrines_world.network.ModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class SetLootBoxPosition {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.X = commandParameterBlockPos(arguments).getX();
			_vars.Y = commandParameterBlockPos(arguments).getY();
			_vars.Z = commandParameterBlockPos(arguments).getZ();
			_vars.markSyncDirty();
		}
	}

	private static BlockPos commandParameterBlockPos(CommandContext<CommandSourceStack> arguments) {
		try {
			return BlockPosArgument.getLoadedBlockPos(arguments, "position");
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return new BlockPos(0, 0, 0);
		}
	}
}