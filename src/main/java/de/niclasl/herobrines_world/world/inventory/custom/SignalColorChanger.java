package de.niclasl.herobrines_world.world.inventory.custom;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.world.inventory.ModMenus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;

public class SignalColorChanger extends AbstractContainerMenu implements ModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 36)
				return null;
			return super.put(key, value);
		}
	};
	public final Level world;
	public final Player entity;
	public int x, y, z;

	public SignalColorChanger(int id, Inventory inv, BlockPos pos) {
		super(ModMenus.SIGNAL_COLOR_CHANGER.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	public SignalColorChanger(int id, Inventory inv, RegistryFriendlyByteBuf buf) {
		super(ModMenus.SIGNAL_COLOR_CHANGER.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
        BlockPos pos;
		if (buf != null) {
			pos = buf.readBlockPos();
            this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
		}
	}

	@Override
	public boolean stillValid(Player player) {
		return player.distanceToSqr(
				x + 0.5, y + 0.5, z + 0.5
		) <= 64;
	}

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}
}
