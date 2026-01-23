package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.init.ModGameRules;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class ThreeHeartsSet {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		if ((world instanceof ServerLevel _serverLevelGR0 && _serverLevelGR0.getGameRules().getBoolean(ModGameRules.THREE_HEARTS))) {
			if (DoubleArgumentType.getDouble(arguments, "hearts") == 0) {
				{
					ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
					_vars.Hearts = 0;
					_vars.markSyncDirty();
				}
			} else if (DoubleArgumentType.getDouble(arguments, "hearts") == 1) {
				{
					ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
					_vars.Hearts = 1;
					_vars.markSyncDirty();
				}
			} else if (DoubleArgumentType.getDouble(arguments, "hearts") == 2) {
				{
					ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
					_vars.Hearts = 2;
					_vars.markSyncDirty();
				}
			} else if (DoubleArgumentType.getDouble(arguments, "hearts") == 3) {
				{
					ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
					_vars.Hearts = 3;
					_vars.markSyncDirty();
				}
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("message.gamerule_deactivate").getString())), true);
		}
	}
}