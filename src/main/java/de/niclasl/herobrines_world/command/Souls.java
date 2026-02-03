package de.niclasl.herobrines_world.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.niclasl.herobrines_world.procedures.*;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

@EventBusSubscriber
public class Souls {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("souls").requires(s -> s.hasPermission(4))
				.then(Commands.literal("add").then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("souls", IntegerArgumentType.integer(0)).then(Commands.literal("levels").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					AddLevels.execute(arguments, entity);
					return 0;
				})).then(Commands.literal("points").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					AddPoints.execute(arguments, entity);
					return 0;
				}))))).then(Commands.literal("set").then(Commands.argument("target", EntityArgument.players()).then(Commands.argument("souls", IntegerArgumentType.integer(0)).then(Commands.literal("levels").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					SetLevels.execute(arguments, entity);
					return 0;
				})).then(Commands.literal("points").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					SetPoints.execute(arguments, entity);
					return 0;
				}))))).then(Commands.literal("query").then(Commands.argument("targets", EntityArgument.player()).then(Commands.literal("levels").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					QueryLevels.execute(entity);
					return 0;
				})).then(Commands.literal("points").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					QueryPoints.execute(entity);
					return 0;
				})))));
	}
}
