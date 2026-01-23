package de.niclasl.herobrines_world.potion.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import de.niclasl.herobrines_world.procedures.RadioActiveEffectStartedApplied;
import org.jetbrains.annotations.NotNull;

public class RadioActive extends MobEffect {
	public RadioActive() {
		super(MobEffectCategory.HARMFUL, -256);
	}

	@Override
	public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
		RadioActiveEffectStartedApplied.execute(entity.level(), entity);
	}
}