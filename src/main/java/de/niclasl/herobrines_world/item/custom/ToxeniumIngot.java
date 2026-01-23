package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import de.niclasl.herobrines_world.procedures.ToxeniumItemInInventoryTick;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ToxeniumIngot extends Item {
	public ToxeniumIngot(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC).fireResistant());
	}

	@Override
	public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		ToxeniumItemInInventoryTick.execute(entity);
	}
}