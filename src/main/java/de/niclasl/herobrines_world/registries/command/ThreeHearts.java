package de.niclasl.herobrines_world.registries.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.niclasl.herobrines_world.Config;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.network.PlayerState;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class ThreeHearts {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(
				Commands.literal("three_hearts")
						.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
										.then(Commands.argument("hearts", IntegerArgumentType.integer(0, 3))
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
										.then(Commands.argument("hearts", IntegerArgumentType.integer(0, 3))
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
						.then(Commands.literal("remove")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("hearts", IntegerArgumentType.integer(0, 3))
												.executes(ctx -> {
													int value = IntegerArgumentType.getInteger(ctx, "hearts");
													for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
														removeHearts(player, value);
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
		if (!Config.THREE_HEARTS.getAsBoolean()) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}
		player.displayClientMessage(Component.translatable("commands.three_hearts.query", player.getName().getString(), PlayerState.hearts(player)), true);
	}

	private static void setHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		if (!Config.THREE_HEARTS.getAsBoolean()) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		PlayerState.setHearts(player, value);
		PacketDistributor.sendToPlayer(
				player,
				new ModVariables.PlayerVariablesSyncMessage(vars)
		);
	}

	private static void addHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		if (!Config.THREE_HEARTS.getAsBoolean()) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		PlayerState.addHearts(player, value);
		PacketDistributor.sendToPlayer(
				player,
				new ModVariables.PlayerVariablesSyncMessage(vars)
		);
	}

	private static void removeHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		if (!Config.THREE_HEARTS.getAsBoolean()) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		PlayerState.removeHearts(player, value);
		PacketDistributor.sendToPlayer(
				player,
				new ModVariables.PlayerVariablesSyncMessage(vars)
		);
	}

	private static void revivePlayer(ServerPlayer player) {
		if (!Config.THREE_HEARTS.getAsBoolean()) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		Level level = player.level();

		player.setHealth(1.0f);

		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
		PlayerState.setHearts(player, 3);
		PacketDistributor.sendToPlayer(
				player,
				new ModVariables.PlayerVariablesSyncMessage(vars)
		);

		ServerPlayer.RespawnConfig config = new ServerPlayer.RespawnConfig(level.getLevelData().getRespawnData(), true);

		player.setRespawnPosition(config, true);
	}
}