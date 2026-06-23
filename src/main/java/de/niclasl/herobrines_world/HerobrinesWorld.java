package de.niclasl.herobrines_world;

import de.niclasl.herobrines_world.common.leaderboard.LeaderboardApiImpl;
import de.niclasl.herobrines_world.common.leaderboard.LeaderboardStorage;
import de.niclasl.herobrines_world.common.network.ModMessage;
import de.niclasl.herobrines_world.common.registries.ModRegistries;
import de.niclasl.herobrines_world_api.api.event.RegisterResolverEvent;
import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardAPIHolder;
import de.niclasl.herobrines_world_api.api.transfer.TransferAPI;
import de.niclasl.herobrines_world_api.api.transfer.resolver.InventoryResolver;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(HerobrinesWorld.MOD_ID)
public class HerobrinesWorld {
	public static final String MOD_ID = "herobrines_world";

    public HerobrinesWorld(IEventBus modEventBus, ModContainer modContainer) {
		modEventBus.addListener(ModMessage::register);

		LeaderboardStorage storage = new LeaderboardStorage();
		LeaderboardAPIHolder.init(new LeaderboardApiImpl(storage));

		RegisterResolverEvent event =
				new RegisterResolverEvent();

		NeoForge.EVENT_BUS.post(event);

		for (InventoryResolver resolver : event.getResolvers()) {
			TransferAPI.registerResolver(resolver);
		}

		ModRegistries.register(modEventBus);

		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
}