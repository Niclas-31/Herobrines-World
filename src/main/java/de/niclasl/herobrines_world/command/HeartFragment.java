package de.niclasl.herobrines_world.command;

import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;

import java.util.Collection;

@EventBusSubscriber
public class HeartFragment {

	@SubscribeEvent
	public static void register(RegisterCommandsEvent event) {

		event.getDispatcher().register(
				Commands.literal("heartfragment")
						.requires(s -> s.hasPermission(4))
						.then(Commands.literal("give")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.literal("frozen_fragment_left")
												.then(Commands.argument("amount",
																DoubleArgumentType.doubleArg(0))
														.executes(ctx -> {

															Collection<ServerPlayer> targets =
																	EntityArgument.getPlayers(ctx, "targets");

															double amount =
																	DoubleArgumentType.getDouble(ctx, "amount");

															for (ServerPlayer player : targets) {
																giveLeft(player, amount);
															}

															return 1;
														})
												)
										)
										.then(Commands.literal("frozen_fragment_right")
												.then(Commands.argument("amount",
																DoubleArgumentType.doubleArg(0))
														.executes(ctx -> {

															Collection<ServerPlayer> targets =
																	EntityArgument.getPlayers(ctx, "targets");

															double amount =
																	DoubleArgumentType.getDouble(ctx, "amount");

															for (ServerPlayer player : targets) {
																giveRight(player, amount);
															}

															return 1;
														})
												)
										)
								)
						)
		);
	}

	private static void giveLeft(ServerPlayer player, double amount) {

		if (amount <= 0) return;

		ItemStack stack = new ItemStack(ModItems.FROZEN_FRAGMENT_LEFT.get());
		stack.setCount((int) amount);

		player.getInventory().placeItemBackInInventory(stack);
	}

	private static void giveRight(ServerPlayer player, double amount) {

		if (amount <= 0) return;

		ItemStack stack = new ItemStack(ModItems.FROZEN_FRAGMENT_RIGHT.get());
		stack.setCount((int) amount);

		player.getInventory().placeItemBackInInventory(stack);
	}
}
