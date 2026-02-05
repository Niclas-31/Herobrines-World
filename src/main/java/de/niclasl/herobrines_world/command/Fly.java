package de.niclasl.herobrines_world.command;

import com.mojang.brigadier.Command;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import java.util.Collection;

@EventBusSubscriber
public class Fly {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {

		event.getDispatcher().register(
				Commands.literal("fly")
						.requires(source -> source.hasPermission(4))

						.then(Commands.argument("targets", EntityArgument.players())
								.executes(ctx -> {

									Collection<ServerPlayer> targets =
											EntityArgument.getPlayers(ctx, "targets");

									for (ServerPlayer player : targets) {
										toggleFly(player);
									}

									return Command.SINGLE_SUCCESS;
								})
						)
		);
	}

	private static void toggleFly(ServerPlayer player) {

		boolean newState = !player.getAbilities().mayfly;

		player.getAbilities().mayfly = newState;

		if (!newState) {
			player.getAbilities().flying = false;
		}

		player.onUpdateAbilities();
	}
}
