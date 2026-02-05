package de.niclasl.herobrines_world.client.screens;

import de.niclasl.herobrines_world.init.ModGameRules;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;
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

		boolean gamemode = getEntityGameType(entity) == GameType.SURVIVAL || getEntityGameType(entity) == GameType.ADVENTURE;
        assert entity != null;
        boolean heart1 = entity.getData(ModVariables.PLAYER_VARIABLES).Hearts >= 1;
		boolean heart2 = entity.getData(ModVariables.PLAYER_VARIABLES).Hearts >= 2;
		boolean heart3 = entity.getData(ModVariables.PLAYER_VARIABLES).Hearts == 3;

		if (ModVariables.MapVariables.get(world).ThreeHearts && gamemode) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/container.png"), w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/container.png"), w / 2, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/container.png"), w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);

			if (heart3) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/frozen_full.png"), w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);
			}
			if (heart1) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/frozen_full.png"), w / 2, h - 47, 0, 0, 9, 9, 9, 9);
			}
			if (heart2) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/frozen_full.png"), w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);
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

	@SubscribeEvent
	public static void onWorldLoad(LevelEvent.Load event) {
		if (!(event.getLevel() instanceof ServerLevel level)) return;

		if (level.getLevelData().isHardcore()) {
			level.getGameRules()
					.getRule(ModGameRules.THREE_HEARTS)
					.set(false, level.getServer());

			ModVariables.MapVariables.get(level).ThreeHearts = false;
			ModVariables.MapVariables.get(level).markSyncDirty();
			return;
		}

		ModVariables.MapVariables.get(level).ThreeHearts = level.getGameRules().getBoolean(ModGameRules.THREE_HEARTS);
		ModVariables.MapVariables.get(level).markSyncDirty();
	}
}
