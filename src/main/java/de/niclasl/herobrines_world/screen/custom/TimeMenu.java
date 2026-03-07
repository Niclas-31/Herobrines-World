package de.niclasl.herobrines_world.screen.custom;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.screen.ModMenuTypes;
import org.jetbrains.annotations.NotNull;

public class TimeMenu extends AbstractContainerMenu {
	public final Level world;
	public final Player entity;
	public int x, y, z;

	public TimeMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(ModMenuTypes.TIME.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		BlockPos pos;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
		}
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
        return true;
	}

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
		return ItemStack.EMPTY;
	}
}