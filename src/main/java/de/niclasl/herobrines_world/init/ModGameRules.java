package de.niclasl.herobrines_world.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber
public class ModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> THREE_HEARTS;
	public static GameRules.Key<GameRules.BooleanValue> CAN_LOOT_BOX_SPAWN;
	public static GameRules.Key<GameRules.IntegerValue> SPAWN_LOOT_BOX_TIMER;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		THREE_HEARTS = GameRules.register("threeHearts", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
		CAN_LOOT_BOX_SPAWN = GameRules.register("canLootBoxSpawn", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
		SPAWN_LOOT_BOX_TIMER = GameRules.register("spawnLootBoxTimer", GameRules.Category.PLAYER, GameRules.IntegerValue.create(6000));
	}
}