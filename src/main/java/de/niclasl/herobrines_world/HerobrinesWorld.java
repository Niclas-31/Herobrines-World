package de.niclasl.herobrines_world;

import com.mojang.logging.LogUtils;
import de.niclasl.herobrines_world.common.leaderboard.LeaderboardApiImpl;
import de.niclasl.herobrines_world.common.leaderboard.LeaderboardStorage;
import de.niclasl.herobrines_world.common.network.ModMessage;
import de.niclasl.herobrines_world.common.registries.ModRegistries;
import de.niclasl.herobrines_world.common.registries.ModResolverRegistries;
import de.niclasl.herobrines_world.config.Config;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardAPIHolder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(HerobrinesWorld.MOD_ID)
public class HerobrinesWorld {
	public static final String MOD_ID = "herobrines_world";

	public static final Logger LOGGER = LogUtils.getLogger();

    public HerobrinesWorld(IEventBus modEventBus, ModContainer modContainer) {
		modEventBus.addListener(ModMessage::register);

		LeaderboardStorage storage = new LeaderboardStorage();
		LeaderboardAPIHolder.init(new LeaderboardApiImpl(storage));

		ModResolverRegistries.register();

		ModRegistries.register(modEventBus);

		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
}