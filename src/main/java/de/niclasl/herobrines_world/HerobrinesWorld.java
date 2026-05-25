package de.niclasl.herobrines_world;

import de.niclasl.herobrines_world.common.network.ModMessage;
import de.niclasl.herobrines_world.common.registries.ModRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(HerobrinesWorld.MODID)
public class HerobrinesWorld {
	public static final String MODID = "herobrines_world";

    public HerobrinesWorld(IEventBus modEventBus, ModContainer modContainer) {
		modEventBus.addListener(ModMessage::register);

		ModRegistries.register(modEventBus);

		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
}