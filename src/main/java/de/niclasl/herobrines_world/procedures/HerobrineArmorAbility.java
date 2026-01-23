package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.item.ModItems;

import java.util.Objects;

public class HerobrineArmorAbility {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(ModVariables.PLAYER_VARIABLES).AbilityActive
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == ModItems.HEROBRINE_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ModItems.HEROBRINE_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == ModItems.HEROBRINE_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == ModItems.HEROBRINE_BOOTS.get()) {
			if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				Objects.requireNonNull(_livingEntity8.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(1000);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, -1, 255, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, 255, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 255, false, false));
		} else if (!entity.getData(ModVariables.PLAYER_VARIABLES).AbilityActive) {
			if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				Objects.requireNonNull(_livingEntity12.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.REGENERATION);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.SATURATION);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.NIGHT_VISION);
		}
	}
}