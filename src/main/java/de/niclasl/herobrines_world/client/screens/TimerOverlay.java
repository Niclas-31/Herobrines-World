package de.niclasl.herobrines_world.client.screens;

import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import java.text.DecimalFormat;
import java.util.Objects;

@EventBusSubscriber(Dist.CLIENT)
public class TimerOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Player entity = Minecraft.getInstance().player;
        assert entity != null;
        String timer = new DecimalFormat("####").format(entity.getData(ModVariables.PLAYER_VARIABLES).Day) + ":" + new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Hour) + ":"
				+ new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Minute) + ":" + new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Second);

		boolean condition = entity.getData(ModVariables.PLAYER_VARIABLES).TimerActive && (getEntityGameType(entity) == GameType.SURVIVAL || getEntityGameType(entity) == GameType.ADVENTURE);

		if (condition) {
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, timer, w / 2 - 19, h - 63, -65536, false);
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
}
