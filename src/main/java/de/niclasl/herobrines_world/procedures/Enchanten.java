package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import de.niclasl.herobrines_world.network.ModVariables;

public class Enchanten {

	private static final double MAX_POINTS_PER_LEVEL = 100;

	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null) return;

		ItemStack slotStack = getSlot0(entity);
		if (slotStack.isEmpty()) return;

		ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);

		if (vars.herobrine && vars.enchantment_level > 0) {
			if (tryEnchant(world, entity, slotStack, ModItems.HEROBRINE_SWORD.get(),
					"herobrines_world:herobrine", 15)) return;

			if (tryEnchant(world, entity, slotStack, ModItems.FIRE_SWORD.get(),
					"herobrines_world:herobrine", 15)) return;

			if (tryEnchant(world, entity, slotStack, ModItems.NATURE_SWORD.get(),
					"herobrines_world:herobrine", 15)) return;

			if (tryEnchant(world, entity, slotStack, ModItems.TOXENIUM_SWORD.get(),
					"herobrines_world:herobrine", 15)) return;
		}

		if (vars.MoreSouls && vars.enchantment_level > 0) {
			if (tryEnchant(world, entity, slotStack, ModItems.HEROBRINE_SWORD.get(),
					"herobrines_world:more_souls", 10)) return;

			if (tryEnchant(world, entity, slotStack, ModItems.FIRE_SWORD.get(),
					"herobrines_world:more_souls", 10)) return;

			if (tryEnchant(world, entity, slotStack, ModItems.NATURE_SWORD.get(),
					"herobrines_world:more_souls", 10)) return;

			if (tryEnchant(world, entity, slotStack, ModItems.TOXENIUM_SWORD.get(),
					"herobrines_world:more_souls", 10)) return;
		}

		if (vars.herobrine && vars.Soul_Level == 0 && vars.Soul_Current < 15) {
			warnNoSouls(entity);
		}

		if (vars.MoreSouls && vars.Soul_Level == 0 && vars.Soul_Current < 10) {
			warnNoSouls(entity);
		}
	}

	private static boolean tryEnchant(
			LevelAccessor world,
			Entity entity,
			ItemStack stack,
			Item item,
			String enchantId,
			double soulCost
	) {
		ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);

		if (stack.getItem() != item) return false;
		if (vars.Soul_Level == 0 && vars.Soul_Current < soulCost) return false;

		stack.enchant(
				world.registryAccess()
						.lookupOrThrow(Registries.ENCHANTMENT)
						.getOrThrow(ResourceKey.create(
								Registries.ENCHANTMENT,
								ResourceLocation.parse(enchantId)
						)),
				(int) vars.enchantment_level
		);

		consumeSouls(vars, soulCost);
		clearState(vars);
		return true;
	}

	private static ItemStack getSlot0(Entity entity) {
		if (entity instanceof Player player
				&& player.containerMenu instanceof ModMenus.MenuAccessor menu) {
			return menu.getSlots().get(0).getItem();
		}
		return ItemStack.EMPTY;
	}

	private static void consumeSouls(ModVariables.PlayerVariables vars, double cost) {
		vars.Soul_Current -= cost;

		if (vars.Soul_Current < 0 && vars.Soul_Level > 0) {
			vars.Soul_Current += MAX_POINTS_PER_LEVEL;
			vars.Soul_Level--;
		}

		if (vars.Soul_Current < 0) vars.Soul_Current = 0;
		if (vars.Soul_Level < 0) vars.Soul_Level = 0;

		vars.markSyncDirty();
	}

	private static void clearState(ModVariables.PlayerVariables vars) {
		vars.Button1 = false;
		vars.Button2 = false;
		vars.Button3 = false;
		vars.Button4 = false;
		vars.herobrine = false;
		vars.MoreSouls = false;
		vars.Enchanten = false;
		vars.enchantment_level = 0;
		vars.markSyncDirty();
	}

	private static void warnNoSouls(Entity entity) {
		if (entity instanceof Player player && !player.level().isClientSide()) {
			player.displayClientMessage(
					Component.literal("Refill your souls. You can no longer enchant."),
					true
			);
		}
		clearState(entity.getData(ModVariables.PLAYER_VARIABLES));
	}
}
