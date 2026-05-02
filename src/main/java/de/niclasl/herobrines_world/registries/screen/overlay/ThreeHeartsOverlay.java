package de.niclasl.herobrines_world.registries.screen.overlay;

import de.niclasl.herobrines_world.Config;
import de.niclasl.herobrines_world.network.GameState;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.Objects;

@EventBusSubscriber(Dist.CLIENT)
public class ThreeHeartsOverlay {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Level world = null;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
		}

        assert entity != null;
        ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);

        boolean gamemode = getEntityGameType(entity) == GameType.SURVIVAL || getEntityGameType(entity) == GameType.ADVENTURE;
        boolean heart1 = vars.Hearts >= 1;
		boolean heart2 = vars.Hearts >= 2;
		boolean heart3 = vars.Hearts == 3;

		if (GameState.threeHearts(world) && gamemode) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("minecraft:textures/gui/sprites/hud/heart/container.png"), w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("minecraft:textures/gui/sprites/hud/heart/container.png"), w / 2, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("minecraft:textures/gui/sprites/hud/heart/container.png"), w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);

			if (heart3) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("minecraft:textures/gui/sprites/hud/heart/frozen_full.png"), w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);
			}
			if (heart1) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("minecraft:textures/gui/sprites/hud/heart/frozen_full.png"), w / 2, h - 47, 0, 0, 9, 9, 9, 9);
			}
			if (heart2) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("minecraft:textures/gui/sprites/hud/heart/frozen_full.png"), w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);
			}
		}
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Objects.requireNonNull(Minecraft.getInstance().getConnection()).getPlayerInfo(player.getGameProfile().id());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		execute(event.getEntity());
	}

	private static void execute(LivingEntity entity) {
		if (!(entity instanceof ServerPlayer player)) return;

		if (!GameState.threeHearts(player.level())) return;

		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		vars.Hearts = Math.max(0, vars.Hearts - 1);
		vars.markSyncDirty();

		if (vars.Hearts <= 0) {
			player.setGameMode(GameType.SPECTATOR);
		}
	}

	@SubscribeEvent
	public static void onConfigChanged(ModConfigEvent.Reloading event) {
		GameState.setThreeHearts(Minecraft.getInstance().level, Config.THREE_HEARTS.getAsBoolean());
	}

	@SubscribeEvent
	public static void onWorldLoad(LevelEvent.Load event) {
		if (!(event.getLevel() instanceof ServerLevel level)) return;

		if (level.getLevelData().isHardcore()) {
			GameState.setThreeHearts(level, false);
			ModVariables.MapVariables.get(level).markSyncDirty();
			return;
		}

		GameState.setThreeHearts(level, Config.THREE_HEARTS.getAsBoolean());
		ModVariables.MapVariables.get(level).markSyncDirty();
	}
}