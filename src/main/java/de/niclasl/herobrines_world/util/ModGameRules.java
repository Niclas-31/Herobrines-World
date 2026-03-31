package de.niclasl.herobrines_world.util;

import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber
public class ModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> THREE_HEARTS;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		THREE_HEARTS = GameRules.register("threeHearts",
				GameRules.Category.PLAYER,
				GameRules.BooleanValue.create(true));
	}
}