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

import de.niclasl.herobrines_world.procedures.HerobrineInt;
import de.niclasl.herobrines_world.procedures.Gamemode3;
import de.niclasl.herobrines_world.procedures.Gamemode2;
import de.niclasl.herobrines_world.procedures.Gamemode1;
import de.niclasl.herobrines_world.procedures.Gamemode0;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@EventBusSubscriber
public class Herobrine {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("herobrine").requires(s -> s.hasPermission(4)).then(Commands.literal("survival").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
            if (entity != null) entity.getDirection();

			Gamemode0.execute(entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
            if (entity != null) entity.getDirection();

			Gamemode0.execute(entity);
			return 0;
		}))).then(Commands.literal("creative").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
            if (entity != null) entity.getDirection();

			Gamemode1.execute(entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
            if (entity != null) entity.getDirection();

			Gamemode1.execute(entity);
			return 0;
		}))).then(Commands.literal("adventure").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
            if (entity != null) entity.getDirection();

			Gamemode2.execute(entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode2.execute(entity);
			return 0;
		}))).then(Commands.literal("spectator").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode3.execute(entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode3.execute(entity);
			return 0;
		}))).then(Commands.literal("s").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode0.execute(entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode0.execute(entity);
			return 0;
		}))).then(Commands.literal("c").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode1.execute(entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode1.execute(entity);
			return 0;
		}))).then(Commands.literal("a").executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			Gamemode2.execute(entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);

			if (entity != null) entity.getDirection();

			Gamemode2.execute(entity);
			return 0;
		}))).then(Commands.argument("int", DoubleArgumentType.doubleArg(0, 3)).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);

			if (entity != null) entity.getDirection();

			HerobrineInt.execute(arguments, entity);
			return 0;
		}).then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			if (entity != null) entity.getDirection();

			HerobrineInt.execute(arguments, entity);
			return 0;
		}))));
	}

}