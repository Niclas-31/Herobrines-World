package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class EnchantenUnlock {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event.getEntity());
	}

	private static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(ModVariables.PLAYER_VARIABLES).herobrine && entity.getData(ModVariables.PLAYER_VARIABLES).enchantment_level > 0) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Enchanten = true;
				_vars.markSyncDirty();
			}
		} else if (entity.getData(ModVariables.PLAYER_VARIABLES).MoreSouls && entity.getData(ModVariables.PLAYER_VARIABLES).enchantment_level > 0) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Enchanten = true;
				_vars.markSyncDirty();
			}
		}
	}
}