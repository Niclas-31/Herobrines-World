package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.init.ModGameRules;

@EventBusSubscriber
public class SetTimer {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event.getEntity().level());
	}

	private static void execute(LevelAccessor world) {
		if (ModVariables.MapVariables.get(world).LootBoxTimer == 0) {
			ModVariables.MapVariables.get(world).LootBoxTimer = (world instanceof ServerLevel _serverLevelGR0 ? _serverLevelGR0.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0);
			ModVariables.MapVariables.get(world).markSyncDirty();
		}
		if ((world instanceof ServerLevel _serverLevelGR1 ? _serverLevelGR1.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0) != ModVariables.MapVariables.get(world).LastSpawnLootBoxTimer) {
			ModVariables.MapVariables.get(world).LootBoxTimer = (world instanceof ServerLevel _serverLevelGR2 ? _serverLevelGR2.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0);
			ModVariables.MapVariables.get(world).LastSpawnLootBoxTimer = (world instanceof ServerLevel _serverLevelGR3 ? _serverLevelGR3.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0);
			ModVariables.MapVariables.get(world).markSyncDirty();
		}
	}
}