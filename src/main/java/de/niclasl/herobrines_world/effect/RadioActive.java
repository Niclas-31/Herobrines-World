package de.niclasl.herobrines_world.effect;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import org.jetbrains.annotations.NotNull;

public class RadioActive extends MobEffect {
	public RadioActive(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
		HerobrinesWorld.queueServerWork(20, () -> entity.hurt(new DamageSource(entity.level().holderOrThrow(DamageTypes.MAGIC)), 1));
	}
}