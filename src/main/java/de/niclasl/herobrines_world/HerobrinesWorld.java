package de.niclasl.herobrines_world;

import com.mojang.logging.LogUtils;
import de.niclasl.herobrines_world.common.leaderboard.LeaderboardApiImpl;
import de.niclasl.herobrines_world.common.leaderboard.LeaderboardStorage;
import de.niclasl.herobrines_world.common.network.ModMessage;
import de.niclasl.herobrines_world.common.registries.registry.ModRegistries;
import de.niclasl.herobrines_world.common.registries.registry.ModResolverRegistries;
import de.niclasl.herobrines_world.common.util.database.DatabaseManager;
import de.niclasl.herobrines_world.config.Config;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardAPIHolder;
import de.niclasl.herobrines_world_api.version.ApiVersion;
import de.niclasl.herobrines_world_api.version.ApiVersionChecker;
import de.niclasl.herobrines_world_api.version.ApiVersionHolder;
import de.niclasl.herobrines_world_api.version.HerobrinesApiVersions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.sql.SQLException;

@Mod(HerobrinesWorld.MOD_ID)
public class HerobrinesWorld {
	public static final String MOD_ID = "herobrines_world";

	public static final ApiVersion REQUIRED_VERSION = new ApiVersion(3, 0);

	public static final DatabaseManager DATABASE = new DatabaseManager();

	public static final Logger LOGGER = LogUtils.getLogger();

    public HerobrinesWorld(IEventBus modEventBus, ModContainer modContainer) throws SQLException {
		ApiVersionChecker.check(LOGGER);

		ApiVersionHolder holder = HerobrinesApiVersions.getHolder(REQUIRED_VERSION.major());

		if (holder == null || !holder.exists(REQUIRED_VERSION)) {
			throw new IllegalArgumentException(
					"Voltrix API Version " + REQUIRED_VERSION + " does not exist!"
			);
		}

		DatabaseManager.init();

		modEventBus.addListener(ModMessage::register);

		LeaderboardStorage storage = new LeaderboardStorage();
		LeaderboardAPIHolder.init(new LeaderboardApiImpl(storage));

		ModResolverRegistries.register();

		ModRegistries.register(modEventBus);

		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
}