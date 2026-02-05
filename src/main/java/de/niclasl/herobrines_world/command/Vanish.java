package de.niclasl.herobrines_world.command;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import java.util.Collection;

@EventBusSubscriber
public class Vanish {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		if (event.getCommandSelection() != Commands.CommandSelection.DEDICATED) return;

		event.getDispatcher().register(
				Commands.literal("vanish")
						.requires(source -> source.hasPermission(4))
						.then(Commands.argument("targets", EntityArgument.players())
								.executes(context -> {
									Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
									for (ServerPlayer target : targets) {
										toggleVanish(target);
									}
									return targets.size();
								})
						)
						.executes(context -> {
							Entity executor = context.getSource().getEntity();

							if (executor instanceof ServerPlayer player) {
								toggleVanish(player);
								return 1;
							}
							return 0;
						})
		);
	}

	private static void toggleVanish(ServerPlayer player) {
		boolean currentlyVanished = player.isInvisible();
		player.setInvisible(!currentlyVanished);
	}
}
