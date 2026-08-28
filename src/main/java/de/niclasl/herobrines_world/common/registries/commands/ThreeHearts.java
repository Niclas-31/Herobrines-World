package de.niclasl.herobrines_world.common.registries.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.database.PlayerData;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.sql.SQLException;

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
                                                try {
                                                    queryHearts(player);
                                                } catch (SQLException e) {
													HerobrinesWorld.LOGGER.error(
															"Failed to query hearts for player {}",
															player.getUUID(),
															e
													);
                                                }
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
                                                        try {
                                                            setHearts(player, value);
                                                        } catch (SQLException e) {
															HerobrinesWorld.LOGGER.error(
																	"Failed to update hearts state for {}",
																	player.getUUID(),
																	e
															);
                                                        }
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
                                                        try {
                                                            addHearts(player, value);
                                                        } catch (SQLException e) {
															HerobrinesWorld.LOGGER.error(
																	"Failed to update hearts state for {}",
																	player.getUUID(),
																	e
															);
                                                        }
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
                                                        try {
                                                            removeHearts(player, value);
                                                        } catch (SQLException e) {
															HerobrinesWorld.LOGGER.error(
																	"Failed to update hearts state for {}",
																	player.getUUID(),
																	e
															);
                                                        }
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
                                                try {
                                                    setHearts(player, 3);
                                                } catch (SQLException e) {
													HerobrinesWorld.LOGGER.error(
															"Failed to update hearts state for {}",
															player.getUUID(),
															e
													);
                                                }
                                            }
											return 1;
										})
								)
						)
						.then(Commands.literal("revive")
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
                                                try {
                                                    revivePlayer(player);
                                                } catch (SQLException e) {
													HerobrinesWorld.LOGGER.error(
															"Failed to update hearts state for {}",
															player.getUUID(),
															e
													);
                                                }
                                            }
											return 1;
										})
								)
						)
						.then(Commands.literal("on")
								.executes(ctx -> {
									for (ServerPlayer player : ctx.getSource().getServer().getPlayerList().getPlayers()) {
                                        try {
                                            setEnabled(player, true);
                                        } catch (SQLException e) {
											HerobrinesWorld.LOGGER.error(
													"Failed to update threeHearts state for {}",
													player.getUUID(),
													e
											);
                                        }
                                    }
									return 1;
								})
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
                                                try {
                                                    setEnabled(player, true);
                                                } catch (SQLException e) {
													HerobrinesWorld.LOGGER.error(
															"Failed to update threeHearts state for {}",
															player.getUUID(),
															e
													);
                                                }
                                            }
											return 1;
										})
								)
						)
						.then(Commands.literal("off")
								.executes(ctx -> {
									for (ServerPlayer player : ctx.getSource().getServer().getPlayerList().getPlayers()) {
                                        try {
                                            setEnabled(player, false);
                                        } catch (SQLException e) {
											HerobrinesWorld.LOGGER.error(
													"Failed to update threeHearts state for {}",
													player.getUUID(),
													e
											);
                                        }
                                    }
									return 1;
								})
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(ctx -> {
											for (ServerPlayer player : EntityArgument.getPlayers(ctx, "targets")) {
                                                try {
                                                    setEnabled(player, false);
                                                } catch (SQLException e) {
													HerobrinesWorld.LOGGER.error(
															"Failed to update threeHearts state for {}",
															player.getUUID(),
															e
													);
                                                }
                                            }
											return 1;
										})
								)
						)
		);
	}

	private static void queryHearts(ServerPlayer player) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (isThreeHeartsEnabled(player)) {
			player.sendSystemMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}
		player.sendSystemMessage(Component.translatable("commands.three_hearts.query", player.getName().getString(), data.hearts), true);
	}

	private static void setHearts(ServerPlayer player, int value) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (isThreeHeartsEnabled(player)) {
			player.sendSystemMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		data.hearts = value;
		HerobrinesWorld.DATABASE.savePlayerData(data);
	}

	private static void addHearts(ServerPlayer player, int value) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (isThreeHeartsEnabled(player)) {
			player.sendSystemMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		data.hearts += value;
		if (data.hearts > 3) {
			data.hearts = 3;
		}
		HerobrinesWorld.DATABASE.savePlayerData(data);
	}

	private static void removeHearts(ServerPlayer player, int value) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (isThreeHeartsEnabled(player)) {
			player.sendSystemMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		data.hearts -= value;
		if (data.hearts < 0) {
			data.hearts = 0;
		}
		HerobrinesWorld.DATABASE.savePlayerData(data);
	}

	private static void revivePlayer(ServerPlayer player) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (isThreeHeartsEnabled(player)) {
			player.sendSystemMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
			return;
		}

		Level level = player.level();

		player.setHealth(1.0f);

		data.hearts = 3;
		HerobrinesWorld.DATABASE.savePlayerData(data);

		ServerPlayer.RespawnConfig config = new ServerPlayer.RespawnConfig(level.getLevelData().getRespawnData(), true);

		player.setRespawnPosition(config, true);
	}

	private static boolean isThreeHeartsEnabled(ServerPlayer player) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (player.level().getLevelData().isHardcore()) return false;

        return !data.threeHearts;
	}

	private static void setEnabled(ServerPlayer player, boolean enabled) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (player.level().getLevelData().isHardcore()) return;

		data.threeHearts = enabled;
		HerobrinesWorld.DATABASE.savePlayerData(data);

		player.sendSystemMessage(
				Component.translatable("commands.three_hearts.enabled", enabled ? "enabled" : "disabled"),
				true
		);
	}
}