package de.niclasl.herobrines_world.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.niclasl.herobrines_world.init.ModGameRules;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@EventBusSubscriber
public class LootBox {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {

		event.getDispatcher().register(
				Commands.literal("loot_box")
						.requires(source -> source.hasPermission(4))
						.then(Commands.literal("set")
								.then(Commands.literal("loot_box")
										.then(Commands.argument("position", BlockPosArgument.blockPos())
												.executes(arguments -> {
													Level world = arguments.getSource().getLevel();
													setLootBoxPosition(world, arguments);
													return 1;
												}))))
						.then(Commands.literal("set")
								.then(Commands.literal("timer")
										.then(Commands.argument("timer", DoubleArgumentType.doubleArg(0))
												.executes(arguments -> {
													Level world = arguments.getSource().getLevel();
													setLootBoxTimer(world, arguments);
													return 1;
												}))))
						.then(Commands.literal("on")
								.executes(ctx -> {
									Level world = ctx.getSource().getLevel();
									turnLootBoxOn(world);
									return 1;
								}))
						.then(Commands.literal("off")
								.executes(ctx -> {
									Level world = ctx.getSource().getLevel();
									turnLootBoxOff(world);
									return 1;
								}))
		);
	}
	private static void setLootBoxPosition(Level world, CommandContext<CommandSourceStack> arguments) {

		ModVariables.PlayerVariables _vars = world.getData(ModVariables.PLAYER_VARIABLES);
		_vars.X = commandParameterBlockPos(arguments).getX();
		_vars.Y = commandParameterBlockPos(arguments).getY();
		_vars.Z = commandParameterBlockPos(arguments).getZ();
		_vars.markSyncDirty();
	}

	private static void setLootBoxTimer(Level world, CommandContext<CommandSourceStack> arguments) {
		if (world instanceof ServerLevel _serverLevel)
			_serverLevel.getGameRules().getRule(ModGameRules.SPAWN_LOOT_BOX_TIMER).set((int) DoubleArgumentType.getDouble(arguments, "timer"), world.getServer());
	}

	private static void turnLootBoxOn(Level world) {
		if (!(world instanceof ServerLevel _serverLevelGR0 && _serverLevelGR0.getGameRules().getBoolean(ModGameRules.CAN_LOOT_BOX_SPAWN))) {
			if (world instanceof ServerLevel _serverLevel)
				_serverLevel.getGameRules().getRule(ModGameRules.CAN_LOOT_BOX_SPAWN).set(true, world.getServer());
		}
	}

	private static void turnLootBoxOff(Level world) {
		if ((world instanceof ServerLevel _serverLevelGR0 && _serverLevelGR0.getGameRules().getBoolean(ModGameRules.CAN_LOOT_BOX_SPAWN))) {
			if (world instanceof ServerLevel _serverLevel)
				_serverLevel.getGameRules().getRule(ModGameRules.CAN_LOOT_BOX_SPAWN).set(false, world.getServer());
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
