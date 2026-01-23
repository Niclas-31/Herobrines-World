package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

import de.niclasl.herobrines_world.network.ModVariables;

@EventBusSubscriber
public class ThreeHeartsCondition {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
        execute(event.getEntity().level(), event.getEntity());
    }

	private static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (ModVariables.MapVariables.get(world).ThreeHearts) {
			if (entity.getData(ModVariables.PLAYER_VARIABLES).Hearts > 0) {
				{
					ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
					_vars.Hearts = entity.getData(ModVariables.PLAYER_VARIABLES).Hearts - 1;
					_vars.markSyncDirty();
				}
			}
			if (entity.getData(ModVariables.PLAYER_VARIABLES).Hearts == 0) {
				if (entity instanceof ServerPlayer _player)
					_player.setGameMode(GameType.SPECTATOR);
			}
		}
	}
}