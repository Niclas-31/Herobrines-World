package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class SoulReceive {

	private static final int SOUL_PER_LEVEL = 100;

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
		if (attacker.level().isClientSide()) return;

		ModVariables.PlayerVariables vars = attacker.getData(ModVariables.PLAYER_VARIABLES);

		ItemStack stack = attacker.getMainHandItem();
		int ench = stack.getEnchantmentLevel(
				attacker.level().registryAccess()
						.lookupOrThrow(Registries.ENCHANTMENT)
						.getOrThrow(ResourceKey.create(
								Registries.ENCHANTMENT,
								ResourceLocation.parse("herobrines_world:more_souls")
						))
		);

		int soulGain = 1;

		if (ench == 1) soulGain = 2;
		else if (ench == 2) soulGain = 4;

		addSoul(vars, soulGain);
	}

	private static void addSoul(ModVariables.PlayerVariables vars, int amount) {
		vars.Soul_Current += amount;

		while (vars.Soul_Current >= SOUL_PER_LEVEL) {
			vars.Soul_Current -= SOUL_PER_LEVEL;
			vars.Soul_Level++;
		}

		vars.markSyncDirty();
	}
}
