package de.niclasl.herobrines_world.world.inventory.custom;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.procedures.AccountThisGuiIsClosed;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;

public class Account extends AbstractContainerMenu implements ModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 6)
				return null;
			return super.put(key, value);
		}
	};
	public final Level world;
	public final Player entity;
	public int x, y, z;

	public Account(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(ModMenus.ACCOUNT.get(), id);
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

	@Override
	public void removed(@NotNull Player playerIn) {
		super.removed(playerIn);
		AccountThisGuiIsClosed.execute(x, y, z, entity);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}
}
