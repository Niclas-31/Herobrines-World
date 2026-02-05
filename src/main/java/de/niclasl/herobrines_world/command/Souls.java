package de.niclasl.herobrines_world.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.procedures.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

@EventBusSubscriber
public class Souls {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(
				Commands.literal("souls")
						.requires(src -> src.hasPermission(4))
						.then(Commands.literal("add")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("souls", IntegerArgumentType.integer(0))
												.then(Commands.literal("levels").executes(ctx -> {
													int amount = IntegerArgumentType.getInteger(ctx, "souls");
													for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
														addLevels(player, amount);
													}
													return 1;
												}))
												.then(Commands.literal("points").executes(ctx -> {
													int amount = IntegerArgumentType.getInteger(ctx, "souls");
													for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
														addPoints(player, amount);
													}
													return 1;
												}))
										)
								)
						)
						.then(Commands.literal("set")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("souls", IntegerArgumentType.integer(0))
												.then(Commands.literal("levels").executes(ctx -> {
													int amount = IntegerArgumentType.getInteger(ctx, "souls");
													for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
														setLevels(player, amount);
													}
													return 1;
												}))
												.then(Commands.literal("points").executes(ctx -> {
													int amount = IntegerArgumentType.getInteger(ctx, "souls");
													for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
														setPoints(player, amount);
													}
													return 1;
												}))
										)
								)
						)
						.then(Commands.literal("query")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.literal("levels").executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												int level = player.getData(ModVariables.PLAYER_VARIABLES).Soul_Level;
												ctx.getSource().sendSuccess(
														() -> Component.literal(player.getName().getString() + " Levels: " + level),
														false
												);
											}
											return 1;
										}))
										.then(Commands.literal("points").executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												int points = player.getData(ModVariables.PLAYER_VARIABLES).Soul_Current;
												ctx.getSource().sendSuccess(
														() -> Component.literal(player.getName().getString() + " Points: " + points),
														false
												);
											}
											return 1;
										}))
								)
						)
		);
	}
	
	private static void addLevels(ServerPlayer player, int amount) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		vars.Soul_Level += amount;
		vars.markSyncDirty();
	}

	private static void addPoints(ServerPlayer player, int amount) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		vars.Soul_Current += amount;
		while (vars.Soul_Current >= 100) {
			vars.Soul_Current -= 100;
			vars.Soul_Level += 1;
		}
		vars.markSyncDirty();
	}

	private static void setLevels(ServerPlayer player, int amount) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		vars.Soul_Level = amount;
		vars.markSyncDirty();
	}

	private static void setPoints(ServerPlayer player, int amount) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		vars.Soul_Current = amount;
		vars.markSyncDirty();
	}
}
