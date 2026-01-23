package de.niclasl.herobrines_world.potion;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.potion.custom.RadioActive;
import de.niclasl.herobrines_world.potion.custom.GoodHerobrine;
import de.niclasl.herobrines_world.potion.custom.BadHerobrine;
import de.niclasl.herobrines_world.HerobrinesWorld;

public class ModMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, HerobrinesWorld.MODID);

	public static final DeferredHolder<MobEffect, MobEffect> GOOD_HEROBRINE_EFFECT = MOB_EFFECTS.register("good_herobrine_effect", GoodHerobrine::new);
	public static final DeferredHolder<MobEffect, MobEffect> BAD_HEROBRINE_EFFECT = MOB_EFFECTS.register("bad_herobrine_effect", BadHerobrine::new);
	public static final DeferredHolder<MobEffect, MobEffect> RADIO_ACTIVE = MOB_EFFECTS.register("radio_active", RadioActive::new);

	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}