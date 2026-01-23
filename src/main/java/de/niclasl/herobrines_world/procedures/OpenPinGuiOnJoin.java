package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.world.inventory.custom.PinCode;
import de.niclasl.herobrines_world.world.inventory.custom.Account;
import de.niclasl.herobrines_world.network.ModVariables;

import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class OpenPinGuiOnJoin {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	private static void execute(double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!entity.getData(ModVariables.PLAYER_VARIABLES).HasSet && !entity.getData(ModVariables.PLAYER_VARIABLES).PinUnlocked) {
			if (entity instanceof ServerPlayer _ent) {
				BlockPos pos = BlockPos.containing(x, y, z);
				_ent.openMenu(new MenuProvider() {
					@Override
					public @NotNull Component getDisplayName() {
						return Component.literal("PinCode");
					}

					@Override
					public boolean shouldTriggerClientSideContainerClosingOnOpen() {
						return false;
					}

					@Override
					public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
						return new PinCode(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
					}
				}, pos);
			}
		} else if (entity.getData(ModVariables.PLAYER_VARIABLES).HasSet && !entity.getData(ModVariables.PLAYER_VARIABLES).PinUnlocked) {
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
}