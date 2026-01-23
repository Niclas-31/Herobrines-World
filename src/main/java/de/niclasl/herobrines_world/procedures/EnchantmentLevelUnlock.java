package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class EnchantmentLevelUnlock {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event.getEntity());
	}

	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(ModVariables.PLAYER_VARIABLES).herobrine) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Button1 = true;
				_vars.Button2 = true;
				_vars.Button3 = true;
				_vars.Button4 = true;
				_vars.markSyncDirty();
			}
		} else if (entity.getData(ModVariables.PLAYER_VARIABLES).MoreSouls) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.Button1 = true;
				_vars.Button2 = true;
				_vars.markSyncDirty();
			}
		}
	}
}