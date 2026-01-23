package de.niclasl.herobrines_world.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.Commands;

import de.niclasl.herobrines_world.procedures.SetLootBoxTimer;
import de.niclasl.herobrines_world.procedures.SetLootBoxPosition;
import de.niclasl.herobrines_world.procedures.On;
import de.niclasl.herobrines_world.procedures.Off;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@EventBusSubscriber
public class LootBox {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("loot_box")

				.then(Commands.literal("set").then(Commands.literal("loot_box").then(Commands.argument("position", BlockPosArgument.blockPos()).executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					SetLootBoxPosition.execute(arguments, entity);
					return 0;
				}))).then(Commands.literal("timer").then(Commands.argument("timer", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					SetLootBoxTimer.execute(world, arguments);
					return 0;
				})))).then(Commands.literal("off").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					Off.execute(world);
					return 0;
				})).then(Commands.literal("on").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					if (entity != null) entity.getDirection();

					On.execute(world);
					return 0;
				})));
	}

}