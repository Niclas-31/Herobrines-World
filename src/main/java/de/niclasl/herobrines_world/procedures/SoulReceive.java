package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class SoulReceive {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
        execute(event.getEntity().level(), event.getSource().getEntity());
    }

	private static void execute(LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
				.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:more_souls")))) != 0) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
					.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:more_souls")))) == 2) {
				if (sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 100) {
					{
						ModVariables.PlayerVariables _vars = sourceentity.getData(ModVariables.PLAYER_VARIABLES);
						_vars.Soul_Current = sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current + 4;
						_vars.markSyncDirty();
					}
				}
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
					.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:more_souls")))) == 1) {
				if (sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 100) {
					{
						ModVariables.PlayerVariables _vars = sourceentity.getData(ModVariables.PLAYER_VARIABLES);
						_vars.Soul_Current = sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current + 2;
						_vars.markSyncDirty();
					}
				}
			}
		}
		if (sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 100) {
			{
				ModVariables.PlayerVariables _vars = sourceentity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Soul_Current = sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current + 1;
				_vars.markSyncDirty();
			}
		}
		if (sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current > 100) {
			{
				ModVariables.PlayerVariables _vars = sourceentity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Soul_Level = sourceentity.getData(ModVariables.PLAYER_VARIABLES).Soul_Level + 1;
				_vars.Soul_Current = 0;
				_vars.markSyncDirty();
			}
		}
	}
}