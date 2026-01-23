package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import de.niclasl.herobrines_world.network.ModVariables;

import java.util.Objects;

public class Flying {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
        if (getEntityGameType(entity) == GameType.CREATIVE || getEntityGameType(entity) == GameType.SPECTATOR) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("You can already fly!"), true);
		} else if (getEntityGameType(entity) == GameType.SURVIVAL && !entity.getData(ModVariables.PLAYER_VARIABLES).canFly
				|| getEntityGameType(entity) == GameType.ADVENTURE && !entity.getData(ModVariables.PLAYER_VARIABLES).canFly) {
			if (entity instanceof Player _player) {
				_player.getAbilities().mayfly = true;
				_player.onUpdateAbilities();
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Airplane mode activated!"), true);
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.canFly = true;
				_vars.markSyncDirty();
			}
		} else if (getEntityGameType(entity) == GameType.SURVIVAL && entity.getData(ModVariables.PLAYER_VARIABLES).canFly
				|| getEntityGameType(entity) == GameType.ADVENTURE && entity.getData(ModVariables.PLAYER_VARIABLES).canFly) {
			if (entity instanceof Player _player) {
				_player.getAbilities().mayfly = false;
				_player.onUpdateAbilities();
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Airplane mode disabled!"), true);
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.canFly = false;
				_vars.markSyncDirty();
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
}