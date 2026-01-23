package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;

import de.niclasl.herobrines_world.HerobrinesWorld;

public class RadioActiveEffectStartedApplied {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		HerobrinesWorld.queueServerWork(20, () -> entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.MAGIC)), 1));
	}
}