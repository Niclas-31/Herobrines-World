package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.world.inventory.custom.Account;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.world.inventory.ModMenus;

import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

public class CreatePinButton {
	public static void execute(double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.MyPinCode = (entity instanceof Player _entity0 && _entity0.containerMenu instanceof ModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "SetPin", "") : "";
			_vars.MyAccount = (entity instanceof Player _entity1 && _entity1.containerMenu instanceof ModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(0, "SetAccount", "") : "";
			_vars.markSyncDirty();
		}
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((Component.translatable("message.pin_save").getString())), true);
		{
			ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
			_vars.HasSet = true;
			_vars.markSyncDirty();
		}
		if (entity instanceof ServerPlayer _ent) {
			BlockPos pos = BlockPos.containing(x, y, z);
			_ent.openMenu(new MenuProvider() {
				@Override
				public @NotNull Component getDisplayName() {
					return Component.literal("Account");
				}

				@Override
				public boolean shouldTriggerClientSideContainerClosingOnOpen() {
					return false;
				}

				@Override
				public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
					return new Account(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
				}
			}, pos);
		}
	}
}