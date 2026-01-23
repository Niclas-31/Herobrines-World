package de.niclasl.herobrines_world.world.inventory.custom;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.world.inventory.ModMenus;
import org.jetbrains.annotations.NotNull;

public class RedEnchantmentTableGui extends AbstractContainerMenu {

	public final int x, y, z;
	public final Player entity;
	private final Container container;

	// ===== Konstruktoren =====

	public RedEnchantmentTableGui(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, inv, getContainer(inv, buf), buf);
	}

	public RedEnchantmentTableGui(int id, Inventory inv, Container container) {
		this(id, inv, container, null);
	}

	private RedEnchantmentTableGui(int id, Inventory inv, Container container, FriendlyByteBuf buf) {
		super(ModMenus.RED_ENCHANTMENT_TABLE_GUI.get(), id);
		this.entity = inv.player;
		this.container = container;

		if (buf != null) {
			BlockPos pos = buf.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
		} else {
			this.x = this.y = this.z = 0;
		}

		// ===== Slots =====
		// Enchantment Slot
		this.addSlot(new Slot(container, 0, 7, 44));

		// Player Inventory
		for (int row = 0; row < 3; ++row)
			for (int col = 0; col < 9; ++col)
				this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

		// Hotbar
		for (int col = 0; col < 9; ++col)
			this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
	}

	private static Container getContainer(Inventory inv, FriendlyByteBuf buf) {
		if (buf == null) return new SimpleContainer(1);

		BlockPos pos = buf.readBlockPos();
		BlockEntity be = inv.player.level().getBlockEntity(pos);

		if (be instanceof Container container) {
			return container;
		}
		return new SimpleContainer(1);
	}

	// ===== Valid =====
	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}

	// ===== Shift-Click =====
	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
		ItemStack empty = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if (!slot.hasItem()) return empty;

		ItemStack stack = slot.getItem();
		ItemStack copy = stack.copy();

		if (index == 0) {
			if (!moveItemStackTo(stack, 1, slots.size(), true)) return empty;
		} else {
			if (!moveItemStackTo(stack, 0, 1, false)) return empty;
		}

		if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
		else slot.setChanged();

		return copy;
	}

	// ===== Drop beim Schließen =====
	@Override
	public void removed(@NotNull Player player) {
		super.removed(player);

		if (!player.level().isClientSide()) {
			for (int i = 0; i < container.getContainerSize(); i++) {
				ItemStack stack = container.getItem(i);
				if (!stack.isEmpty()) {
					player.drop(stack, false);
					container.setItem(i, ItemStack.EMPTY);
				}
			}
		}
	}
}