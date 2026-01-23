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

import de.niclasl.herobrines_world.procedures.HeartFragmentGiveRight;
import de.niclasl.herobrines_world.procedures.HeartFragmentGiveLeft;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@EventBusSubscriber
public class HeartFragment {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("heartfragment").requires(s -> s.hasPermission(4)).then(
				Commands.literal("give").then(Commands.argument("targets", EntityArgument.players()).then(Commands.literal("frozen_fragment_left").then(Commands.argument("frozen_fragment_left", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
                    if (entity != null) entity.getDirection();

					HeartFragmentGiveLeft.execute(arguments, entity);
					return 0;
				}))).then(Commands.literal("frozen_fragment_right").then(Commands.argument("frozen_fragment_right", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
                    if (entity != null) entity.getDirection();

					HeartFragmentGiveRight.execute(arguments, entity);
					return 0;
				}))))));
	}

}