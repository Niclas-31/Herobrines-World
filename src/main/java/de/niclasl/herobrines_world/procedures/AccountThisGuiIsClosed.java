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
import de.niclasl.herobrines_world.HerobrinesWorld;

import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

public class AccountThisGuiIsClosed {
	public static void execute(double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!entity.getData(ModVariables.PLAYER_VARIABLES).PinUnlocked) {
			HerobrinesWorld.queueServerWork(1, () -> {
				if (entity instanceof ServerPlayer player) {
					BlockPos pos = BlockPos.containing(x, y, z);
					player.openMenu(new MenuProvider() {
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
			});
		}
	}
}