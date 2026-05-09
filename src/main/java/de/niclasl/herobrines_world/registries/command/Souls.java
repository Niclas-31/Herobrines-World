package de.niclasl.herobrines_world.registries.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.niclasl.herobrines_world.math.SoulMath;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class Souls {

	@SubscribeEvent
	public static void register(RegisterCommandsEvent event) {

		event.getDispatcher().register(
				Commands.literal("souls")
						.requires(Commands.hasPermission(Commands.LEVEL_OWNERS))
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
														int level = getLevel(vars(p));
														ctx.getSource().sendSuccess(
																() -> Component.translatable("commands.souls.query.levels", p.getName().getString(), level),
																false
														);
													});
													return 1;
												}))
								)
						)
		);
	}

	private static void addSouls(ServerPlayer player, int amount) {
		ModVariables.PlayerVariables vars = vars(player);

		vars.Souls = SoulMath.addXP(vars.Souls, amount);
		sync(player);
	}

	private static void setSouls(ServerPlayer player, int amount) {
		ModVariables.PlayerVariables vars = vars(player);

		vars.Souls = Math.max(0, amount);
		sync(player);
	}

	private static void addLevels(ServerPlayer player, int amount) {
		ModVariables.PlayerVariables vars = vars(player);

		int level = SoulMath.getLevelFromXP(vars.Souls);
		setLevel(vars, level + amount);

		sync(player);
	}

	private static void setLevels(ServerPlayer player, int level) {
		ModVariables.PlayerVariables vars = vars(player);
		setLevel(vars, level);
		sync(player);
	}

	private static ModVariables.PlayerVariables vars(ServerPlayer player) {
		return player.getData(ModVariables.PLAYER_VARIABLES);
	}

	private static void sync(ServerPlayer player) {
		PacketDistributor.sendToPlayer(
				player,
				new ModVariables.PlayerVariablesSyncMessage(vars(player))
		);
	}

	private static void forPlayers(CommandContext<CommandSourceStack> ctx, java.util.function.Consumer<ServerPlayer> action)
			throws CommandSyntaxException {
		EntityArgument.getPlayers(ctx, "targets").forEach(action);
	}

	private static int getLevel(ModVariables.PlayerVariables vars) {
		return SoulMath.getLevelFromXP(vars.Souls);
	}

	private static void setLevel(ModVariables.PlayerVariables vars, int targetLevel) {
		vars.Souls = SoulMath.getTotalForLevel(targetLevel);
	}
}