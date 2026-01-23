package de.niclasl.herobrines_world.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import de.niclasl.herobrines_world.procedures.ThreeHeartsSet;
import de.niclasl.herobrines_world.procedures.ThreeHeartsRevive;
import de.niclasl.herobrines_world.procedures.ThreeHeartsReset;
import de.niclasl.herobrines_world.procedures.ThreeHeartsQuery;
import de.niclasl.herobrines_world.procedures.ThreeHeartsAdd;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@EventBusSubscriber
public class ThreeHearts {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("threehearts").requires(s -> s.hasPermission(4)).then(Commands.literal("query").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsQuery.execute(world, entity);
			return 0;
		}).then(Commands.argument("targets", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsQuery.execute(world, entity);
			return 0;
		}))).then(Commands.literal("set").then(Commands.argument("hearts", DoubleArgumentType.doubleArg(0, 3)).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsSet.execute(world, arguments, entity);
			return 0;
		}).then(Commands.argument("targets", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsSet.execute(world, arguments, entity);
			return 0;
		})))).then(Commands.literal("add").then(Commands.argument("hearts", DoubleArgumentType.doubleArg(0, 3)).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsAdd.execute(world, arguments, entity);
			return 0;
		}).then(Commands.argument("targets", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsAdd.execute(world, arguments, entity);
			return 0;
		})))).then(Commands.literal("reset").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsReset.execute(world, entity);
			return 0;
		}).then(Commands.argument("targets", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsReset.execute(world, entity);
			return 0;
		}))).then(Commands.literal("revive").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsRevive.execute(world, entity);
			return 0;
		}).then(Commands.argument("targets", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			ThreeHeartsRevive.execute(world, entity);
			return 0;
		}))));
	}

}