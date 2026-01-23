package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.init.ModGameRules;

public class ThreeHeartsQuery {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if ((world instanceof ServerLevel _serverLevelGR0 && _serverLevelGR0.getGameRules().getBoolean(ModGameRules.THREE_HEARTS))) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("" + entity.getData(ModVariables.PLAYER_VARIABLES).Hearts)), true);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
		}
	}
}