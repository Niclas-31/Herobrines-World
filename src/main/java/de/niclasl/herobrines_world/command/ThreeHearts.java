package de.niclasl.herobrines_world.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.niclasl.herobrines_world.init.ModGameRules;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@EventBusSubscriber
public class ThreeHearts {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(
				Commands.literal("threehearts")
						.requires(src -> src.hasPermission(4))
						.then(Commands.literal("query")
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												queryHearts(player);
											}
											return 1;
										})
								)
						)
						.then(Commands.literal("set")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("hearts", DoubleArgumentType.doubleArg(0, 3))
												.executes(ctx -> {
													int value = IntegerArgumentType.getInteger(ctx, "hearts");
													for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
														setHearts(player, value);
													}
													return 1;
												})
										)
								)
						)
						.then(Commands.literal("add")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("hearts", DoubleArgumentType.doubleArg(0, 3))
												.executes(ctx -> {
													int value = IntegerArgumentType.getInteger(ctx, "hearts");
													for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
														addHearts(player, value);
													}
													return 1;
												})
										)
								)
						)
						.then(Commands.literal("reset")
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												setHearts(player, 3);
											}
											return 1;
										})
								)
						)
						.then(Commands.literal("revive")
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												revivePlayer(player);
											}
											return 1;
										})
								)
						)
		);
	}

	private static void queryHearts(ServerPlayer player) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		if (!player.level().getGameRules().getBoolean(ModGameRules.THREE_HEARTS)) {
			player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
			return;
		}
		player.displayClientMessage(Component.literal(player.getName().getString() + " Hearts: " + vars.Hearts), true);
	}

	private static void setHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		if (!player.level().getGameRules().getBoolean(ModGameRules.THREE_HEARTS)) {
			player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
			return;
		}

		vars.Hearts = value;
		vars.markSyncDirty();
	}

	private static void addHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		if (!player.level().getGameRules().getBoolean(ModGameRules.THREE_HEARTS)) {
			player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
			return;
		}

		if (vars.Hearts < 3) {
			vars.Hearts = vars.Hearts + value;
		}
		vars.markSyncDirty();
	}

	private static void revivePlayer(ServerPlayer player) {
		player.setHealth(1.0f);

		Level level = player.level();

		if (!player.level().getGameRules().getBoolean(ModGameRules.THREE_HEARTS)) {
			player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
			return;
		}

		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		vars.Hearts = 3;
		vars.markSyncDirty();

		ServerPlayer.RespawnConfig config = new ServerPlayer.RespawnConfig(level.getLevelData().getRespawnData(), true);

		player.setRespawnPosition(config, true);
	}
}
