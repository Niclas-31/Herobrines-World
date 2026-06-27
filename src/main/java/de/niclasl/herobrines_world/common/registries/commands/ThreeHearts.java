package de.niclasl.herobrines_world.common.registries.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.niclasl.herobrines_world.common.network.ModVariables;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class ThreeHearts {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(
				Commands.literal("three_hearts")
						.then(Commands.literal("query")
								.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
								.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
								.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
								.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
								.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
								.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												revivePlayer(player);
											}
											return 1;
										})
								)
						)
						.then(Commands.literal("on")
								.requires(src -> src.getEntity() instanceof ServerPlayer player
										&& permission(player))
								.executes(ctx -> {
									for (ServerPlayer player : ctx.getSource().getServer().getPlayerList().getPlayers()) {
										setEnabled(player, true);
									}
									return 1;
								})
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												setEnabled(player, true);
											}
											return 1;
										})
								)
						)
						.then(Commands.literal("off")
								.requires(src -> src.getEntity() instanceof ServerPlayer player2
										&& permission(player2))
								.executes(ctx -> {
									for (ServerPlayer player : ctx.getSource().getServer().getPlayerList().getPlayers()) {
										setEnabled(player, false);
									}
									return 1;
								})
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
												setEnabled(player, false);
											}
											return 1;
										})
								)
						)
		);
	}

	private static void queryHearts(ServerPlayer player) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (isThreeHeartsEnabled(player)) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}
		player.displayClientMessage(Component.translatable("commands.three_hearts.query", player.getName().getString(), vars.hearts), true);
	}

	private static void setHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (isThreeHeartsEnabled(player)) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		vars.hearts = value;
		vars.markSyncDirty(player);
	}

	private static void addHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (isThreeHeartsEnabled(player)) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		vars.hearts += value;
		if (vars.hearts > 3) {
			vars.hearts = 3;
		}
		vars.markSyncDirty(player);
	}

	private static void removeHearts(ServerPlayer player, int value) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (isThreeHeartsEnabled(player)) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		vars.hearts -= value;
		if (vars.hearts < 0) {
			vars.hearts = 0;
		}
		vars.markSyncDirty(player);
	}

	private static void revivePlayer(ServerPlayer player) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (isThreeHeartsEnabled(player)) {
			player.displayClientMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		Level level = player.level();

		player.setHealth(1.0f);

		vars.hearts = 3;
		vars.markSyncDirty(player);

		ServerPlayer.RespawnConfig config = new ServerPlayer.RespawnConfig(level.getLevelData().getRespawnData(), true);

		player.setRespawnPosition(config, true);
	}

	private static boolean isThreeHeartsEnabled(ServerPlayer player) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (player.level().getLevelData().isHardcore()) return false;

        if (player.level().getServer().isDedicatedServer()) {
			return !vars.threeHearts;
		}

        return vars.threeHearts;
	}

	private static void setEnabled(ServerPlayer player, boolean enabled) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (player.level().getLevelData().isHardcore()) return;

		vars.threeHearts = enabled;
		vars.markSyncDirty(player);

		player.displayClientMessage(
				Component.translatable("commands.three_hearts.enabled", enabled ? "enabled" : "disabled"),
				true
		);
	}

	private static boolean permission(ServerPlayer player) {
		boolean singleplayer = player.level().getServer().isSingleplayerOwner(new NameAndId(player.getGameProfile()));

		PermissionSet permissionSet = player.createCommandSourceStack().permissions();

		return singleplayer
				? Commands.LEVEL_ALL.check(permissionSet)
				: Commands.LEVEL_GAMEMASTERS.check(permissionSet);
	}
}