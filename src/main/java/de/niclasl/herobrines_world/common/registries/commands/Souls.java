package de.niclasl.herobrines_world.common.registries.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.niclasl.herobrines_world.common.leaderboard.season.SeasonManager;
import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world.common.network.message.SyncLeaderboardPacket;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardEntry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.function.Consumer;

@EventBusSubscriber
public class Souls {

	@SubscribeEvent
	public static void register(RegisterCommandsEvent event) {

		event.getDispatcher().register(
				Commands.literal("souls")
						.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
						.then(Commands.literal("add")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("amount", IntegerArgumentType.integer())
												.then(Commands.literal("points")
														.executes(ctx -> {
															int amount = IntegerArgumentType.getInteger(ctx, "amount");

															forPlayers(ctx, p -> addSouls(p, amount));
															return 1;
														}))
												.then(Commands.literal("levels")
														.executes(ctx -> {
															int amount = IntegerArgumentType.getInteger(ctx, "amount");

															forPlayers(ctx, p -> addLevels(p, amount));
															return 1;
														}))
										)
								)
						)
						.then(Commands.literal("set")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("amount", IntegerArgumentType.integer(0))
												.then(Commands.literal("points")
														.executes(ctx -> {
															int amount = IntegerArgumentType.getInteger(ctx, "amount");
															forPlayers(ctx, p -> setSouls(p, amount));
															return 1;
														}))
												.then(Commands.literal("levels")
														.executes(ctx -> {
															int amount = IntegerArgumentType.getInteger(ctx, "amount");
															forPlayers(ctx, p -> setLevels(p, amount));
															return 1;
														}))
										)
								)
						)
						.then(Commands.literal("prestige")
								.executes(ctx -> {
									ServerPlayer player = ctx.getSource().getPlayerOrException();

									ModVariables.PlayerVariables vars = vars(player);

									if (vars.SoulLevel < SoulMath.HARD_CAP) {

										ctx.getSource().sendFailure(
												Component.literal(
														"You need max level to prestige!"
												)
										);

										return 0;
									}

									vars.Prestige++;
									vars.Souls = 0;

									vars.markSyncDirty(player);

									ctx.getSource().sendSuccess(
											() -> Component.literal(
													"Prestiged to " + vars.Prestige
											),
											false
									);

									return 1;
								})
						)
						.then(Commands.literal("query")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.literal("points")
												.executes(ctx -> {
													forPlayers(ctx, p -> {
														int souls = vars(p).Souls;
														ctx.getSource().sendSuccess(
																() -> Component.translatable("commands.souls.query.points", p.getDisplayName().getString(), souls),
																false
														);
													});
													return 1;
												}))
										.then(Commands.literal("levels")
												.executes(ctx -> {
													forPlayers(ctx, p -> {
														int level = vars(p).SoulLevel;
														ctx.getSource().sendSuccess(
																() -> Component.translatable("commands.souls.query.levels", p.getName().getString(), level),
																false
														);
													});
													return 1;
												}))
								)
						)
						.then(Commands.literal("leaderboard")
								.executes(ctx -> {
									CommandSourceStack source = ctx.getSource();

									if (!(source.getEntity() instanceof ServerPlayer player)) {
										return 0;
									}

									List<LeaderboardEntry> entries = SeasonManager.getLeaderboard(source.getServer().overworld());

									PacketDistributor.sendToPlayer(player, new SyncLeaderboardPacket(entries));

									return 1;
								})
						)
		);
	}

	private static void addSouls(ServerPlayer player, int amount) {

		ModVariables.PlayerVariables vars = vars(player);

		if (vars.SoulLevel >= SoulMath.HARD_CAP) {
			vars.Souls = 0;
			vars.markSyncDirty(player);
			return;
		}

		vars.Souls += amount;

		while (vars.SoulLevel < SoulMath.HARD_CAP &&
				vars.Souls >= SoulMath.getXPForLevel(vars.SoulLevel)) {

			vars.Souls -= SoulMath.getXPForLevel(vars.SoulLevel);
			vars.SoulLevel++;
		}

		if (vars.SoulLevel >= SoulMath.HARD_CAP) {
			vars.SoulLevel = SoulMath.HARD_CAP;
			vars.Souls = 0;
		}

		vars.markSyncDirty(player);
	}

	private static void setSouls(ServerPlayer player, int amount) {

		ModVariables.PlayerVariables vars = vars(player);

		int max = SoulMath.getXPForLevel(vars.SoulLevel);

		vars.Souls = Math.clamp(amount, 0, max - 1);

		vars.markSyncDirty(player);
	}

	private static void addLevels(ServerPlayer player, int amount) {

		ModVariables.PlayerVariables vars = vars(player);

		vars.SoulLevel = Math.clamp(
				vars.SoulLevel + amount,
				0,
				SoulMath.HARD_CAP
		);

		vars.markSyncDirty(player);
	}

	private static void setLevels(ServerPlayer player, int level) {

		ModVariables.PlayerVariables vars = vars(player);

		vars.SoulLevel = Math.clamp(
				level,
				0,
				SoulMath.HARD_CAP
		);

		vars.markSyncDirty(player);
	}

	private static ModVariables.PlayerVariables vars(ServerPlayer player) {
		return player.getData(ModVariables.PLAYER_VARIABLES);
	}

	private static void forPlayers(CommandContext<CommandSourceStack> ctx, Consumer<ServerPlayer> action)
			throws CommandSyntaxException {
		EntityArgument.getPlayers(ctx, "targets").forEach(action);
	}
}