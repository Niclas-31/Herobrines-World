package de.niclasl.herobrines_world.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;

import java.util.Collection;
import java.util.List;

@EventBusSubscriber
public class Herobrine {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {

		event.getDispatcher().register(
				Commands.literal("herobrine")
						.requires(s -> s.hasPermission(4))
						
						.then(modeCommand("survival", GameType.SURVIVAL))
						.then(modeCommand("s", GameType.SURVIVAL))
						
						.then(modeCommand("creative", GameType.CREATIVE))
						.then(modeCommand("c", GameType.CREATIVE))
						
						.then(modeCommand("adventure", GameType.ADVENTURE))
						.then(modeCommand("a", GameType.ADVENTURE))
						
						.then(modeCommand("spectator", GameType.SPECTATOR))
						
						.then(
								Commands.argument("mode", DoubleArgumentType.doubleArg(0,3))
										.executes(ctx -> {
											setGamemode(
													List.of(ctx.getSource().getPlayerOrException()),
													(int) DoubleArgumentType.getDouble(ctx, "mode"));
											return 1;
										})
										.then(Commands.argument("target", EntityArgument.players())
												.executes(ctx -> {
													setGamemode(
															EntityArgument.getPlayers(ctx, "target"),
															(int) DoubleArgumentType.getDouble(ctx, "mode"));
													return 1;
												})
										)
						)
		);
	}

	private static LiteralArgumentBuilder<CommandSourceStack> modeCommand(String name, GameType type) {
		return Commands.literal(name)
				.executes(ctx -> {
					setGamemode(
							List.of(ctx.getSource().getPlayerOrException()),
							type.getId());
					return 1;
				})
				.then(Commands.argument("target", EntityArgument.players())
						.executes(ctx -> {
							setGamemode(
									EntityArgument.getPlayers(ctx, "target"),
									type.getId());
							return 1;
						})
				);
	}

	private static void setGamemode(Collection<ServerPlayer> players, int mode) {

		GameType gameType = switch (mode) {
            case 1 -> GameType.CREATIVE;
			case 2 -> GameType.ADVENTURE;
			case 3 -> GameType.SPECTATOR;
			default -> GameType.SURVIVAL;
		};

		for (ServerPlayer player : players) {
			player.setGameMode(gameType);
		}
	}
}
