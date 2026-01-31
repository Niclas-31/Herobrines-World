package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.CommandSourceStack;

import de.niclasl.herobrines_world.item.ModItems;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class HeartFragmentGiveLeft {

	public static void execute(CommandContext<CommandSourceStack> context, Entity entity) {
		if (!(entity instanceof Player player)) return;

		double amount = DoubleArgumentType.getDouble(context, "frozen_fragment_left");
		if (amount <= 0) return;

		ItemStack stack = new ItemStack(ModItems.FROZEN_FRAGMENT_LEFT.get());
		stack.setCount((int) amount);

		player.getInventory().placeItemBackInInventory(stack);
	}

}
