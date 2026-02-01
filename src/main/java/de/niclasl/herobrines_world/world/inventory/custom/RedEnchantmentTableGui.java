package de.niclasl.herobrines_world.world.inventory.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RedEnchantmentTableMenu extends AbstractContainerMenu implements ModMenus.MenuAccessor {

	private final Container container;
	public final int x, y, z;
	public final Player entity;

	public RedEnchantmentTableMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		super(ModMenus.RED_ENCHANTMENT_TABLE_GUI.get(), id);

		BlockPos pos = buf.readBlockPos();

		this.entity = inv.player;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();

		BlockEntity be = inv.player.level().getBlockEntity(pos);
		this.container = be instanceof Container c ? c : new SimpleContainer(1);

		addSlots(inv);
	}

	public RedEnchantmentTableMenu(int id, Inventory inv, Container container, BlockPos pos) {
		super(ModMenus.RED_ENCHANTMENT_TABLE_GUI.get(), id);

		this.entity = inv.player;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.container = container;

		addSlots(inv);
	}

	private void addSlots(Inventory inv) {
		this.addSlot(new Slot(container, 0, 7, 44));

		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(inv, col + row * 9 + 9,
						8 + col * 18, 84 + row * 18));
			}
		}

		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
		}
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}

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

	@Override
	public Map<String, Object> getMenuState() {
		return Map.of();
	}

	@Override
	public Map<Integer, Slot> getSlots() {
		return java.util.stream.IntStream.range(0, this.slots.size())
				.boxed()
				.collect(java.util.stream.Collectors.toMap(i -> i, this.slots::get));
	}
}
