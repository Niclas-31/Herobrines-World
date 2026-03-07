package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.item.ModArmorMaterials;
import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class Toxenium extends Item {
	private Toxenium(Properties properties) {
		super(properties);
	}

	public static class Helmet extends Toxenium {
		public Helmet(Properties properties) {
			super(properties.humanoidArmor(ModArmorMaterials.TOXENIUM_ARMOR_MATERIAL, ArmorType.HELMET));
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player entGetArmor && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				if (!(entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get() && entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get() && entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get() && entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get())) {
					if (!entGetArmor.level().isClientSide())
						entGetArmor.addEffect(new MobEffectInstance(ModEffects.RADIO_ACTIVE, 40, 0));
				} else {
                    if (entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get()) {
                        if (entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get()) {
                            if (entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get()) {
                                if (entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get()) {
                                    if (!entGetArmor.level().isClientSide())
                                        entGetArmor.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 1, false, false));
                                    if (!entGetArmor.level().isClientSide())
                                        entGetArmor.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1, false, false));
                                }
                            }
                        }
                    }
                }
			}
		}
	}

	public static class Chestplate extends Toxenium {
		public Chestplate(Properties properties) {
			super(properties.humanoidArmor(ModArmorMaterials.TOXENIUM_ARMOR_MATERIAL, ArmorType.CHESTPLATE));
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player entGetArmor && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				if (!(entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get() && entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get() && entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get() && entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get())) {
					if (!entGetArmor.level().isClientSide())
						entGetArmor.addEffect(new MobEffectInstance(ModEffects.RADIO_ACTIVE, 40, 0));
				} else {
					if (entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get()) {
						if (entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get()) {
							if (entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get()) {
								if (entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get()) {
									if (!entGetArmor.level().isClientSide())
										entGetArmor.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 1, false, false));
									if (!entGetArmor.level().isClientSide())
										entGetArmor.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1, false, false));
								}
							}
						}
					}
				}
			}
		}
	}

	public static class Leggings extends Toxenium {
		public Leggings(Properties properties) {
			super(properties.humanoidArmor(ModArmorMaterials.TOXENIUM_ARMOR_MATERIAL, ArmorType.LEGGINGS));
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player entGetArmor && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				if (!(entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get() && entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get() && entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get() && entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get())) {
					if (!entGetArmor.level().isClientSide())
						entGetArmor.addEffect(new MobEffectInstance(ModEffects.RADIO_ACTIVE, 40, 0));
				} else {
					if (entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get()) {
						if (entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get()) {
							if (entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get()) {
								if (entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get()) {
									if (!entGetArmor.level().isClientSide())
										entGetArmor.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 1, false, false));
									if (!entGetArmor.level().isClientSide())
										entGetArmor.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1, false, false));
								}
							}
						}
					}
				}
			}
		}
	}

	public static class Boots extends Toxenium {
		public Boots(Properties properties) {
			super(properties.humanoidArmor(ModArmorMaterials.TOXENIUM_ARMOR_MATERIAL, ArmorType.BOOTS));
		}

		@Override
		public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player entGetArmor && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				if (!(entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get() && entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get() && entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get() && entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get())) {
					if (!entGetArmor.level().isClientSide())
						entGetArmor.addEffect(new MobEffectInstance(ModEffects.RADIO_ACTIVE, 40, 0));
				} else {
					if (entGetArmor.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TOXENIUM_HELMET.get()) {
						if (entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TOXENIUM_CHESTPLATE.get()) {
							if (entGetArmor.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TOXENIUM_LEGGINGS.get()) {
								if (entGetArmor.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TOXENIUM_BOOTS.get()) {
									if (!entGetArmor.level().isClientSide())
										entGetArmor.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 1, false, false));
									if (!entGetArmor.level().isClientSide())
										entGetArmor.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1, false, false));
								}
							}
						}
					}
				}
			}
		}
	}
}