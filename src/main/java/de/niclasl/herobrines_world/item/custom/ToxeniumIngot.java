package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ToxeniumIngot extends Item {
	public ToxeniumIngot(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC).fireResistant());
	}

	@Override
	public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		
		if (!((entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_HELMET.get()
				&& (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_CHESTPLATE.get()
				&& (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_LEGGINGS.get()
				&& (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_BOOTS.get())) {
			if (entity instanceof LivingEntity entity1 && !entity1.level().isClientSide())
				entity1.addEffect(new MobEffectInstance(ModMobEffects.RADIO_ACTIVE, 40, 0, false, false));
		}
	}
}
