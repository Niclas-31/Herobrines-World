package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.world.inventory.ModMenus;

public class ConfirmButton {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (((entity instanceof Player _entity0 && _entity0.containerMenu instanceof ModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "PinCode", "") : "").equals(entity.getData(ModVariables.PLAYER_VARIABLES).MyPinCode)
				&& ((entity instanceof Player _entity1 && _entity1.containerMenu instanceof ModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(0, "Account", "") : "")
						.equals(entity.getData(ModVariables.PLAYER_VARIABLES).MyAccount)) {
			{
				ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
				_vars.PinUnlocked = true;
				_vars.markSyncDirty();
			}
			if (entity instanceof Player _player)
				_player.closeContainer();
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("message.pin_correct").getString())), true);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("message.pin_false").getString())), true);
		}
	}
}