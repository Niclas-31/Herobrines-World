package de.niclasl.herobrines_world.world.inventory.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import de.niclasl.herobrines_world.world.inventory.ModMenus;
import org.jetbrains.annotations.NotNull;

public class RedEnchantmentTableGui3 extends AbstractContainerMenu {

	public final int x;
	public final int y;
	public final int z;
	public final Player entity;
	public final Level level;
	
	public RedEnchantmentTableGui3(int id, Inventory inv, FriendlyByteBuf buf) {
		this(
				id,
				inv,
				inv.player,
				buf.readBlockPos()
		);
	}

	public RedEnchantmentTableGui3(int id, Inventory inv, Player player, BlockPos pos) {
		super(ModMenus.RED_ENCHANTMENT_TABLE_GUI_3.get(), id);

		this.entity = player;
		this.level = player.level();

		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();

		for (int row = 0; row < 3; ++row)
			for (int col = 0; col < 9; ++col)
				this.addSlot(new Slot(
						inv,
						col + row * 9 + 9,
						8 + col * 18,
						84 + row * 18
				));

		for (int col = 0; col < 9; ++col)
			this.addSlot(new Slot(
					inv,
					col,
					8 + col * 18,
					142
			));
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
		return ItemStack.EMPTY;
	}

}
