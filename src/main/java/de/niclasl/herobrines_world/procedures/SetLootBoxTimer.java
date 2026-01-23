package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.CommandSourceStack;

import de.niclasl.herobrines_world.init.ModGameRules;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SetLootBoxTimer {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		if (world instanceof ServerLevel _serverLevel)
			_serverLevel.getGameRules().getRule(ModGameRules.SPAWN_LOOT_BOX_TIMER).set((int) DoubleArgumentType.getDouble(arguments, "timer"), world.getServer());
	}
}