package de.niclasl.herobrines_world.registries.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.effect.MobEffect;

import de.niclasl.herobrines_world.HerobrinesWorld;

public class ModEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS =
			DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, HerobrinesWorld.MODID);

	public static final Holder<MobEffect> GOOD_HEROBRINE_EFFECT = MOB_EFFECTS.register("good_herobrine_effect",
			() -> new GoodHerobrine(MobEffectCategory.BENEFICIAL));

	public static final Holder<MobEffect> BAD_HEROBRINE_EFFECT = MOB_EFFECTS.register("bad_herobrine_effect",
			() -> new BadHerobrine(MobEffectCategory.HARMFUL));

	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}