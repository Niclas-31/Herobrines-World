package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import de.niclasl.herobrines_world.network.ModVariables;

import java.util.Objects;

public class TimerOverlayInGame {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
        return entity.getData(ModVariables.PLAYER_VARIABLES).TimerActive && (getEntityGameType(entity) == GameType.SURVIVAL || getEntityGameType(entity) == GameType.ADVENTURE);
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