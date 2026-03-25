package de.niclasl.herobrines_world.potion;

import de.niclasl.herobrines_world.effect.ModEffects;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.HerobrinesWorld;

public class ModPotions {
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, HerobrinesWorld.MODID);

	public static final DeferredHolder<Potion, Potion> GOOD_HEROBRINE_OMEN = POTIONS.register("good_herobrine_omen",
			() -> new Potion("good_herobrine_omen", new MobEffectInstance(ModEffects.GOOD_HEROBRINE_EFFECT, 900, 0, false, true)));

	public static final DeferredHolder<Potion, Potion> BAD_HEROBRINE_OMEN = POTIONS.register("bad_herobrine_omen",
			() -> new Potion("bad_herobrine_omen", new MobEffectInstance(ModEffects.BAD_HEROBRINE_EFFECT, 900, 0, false, true)));

	public static void register(IEventBus eventBus) {
		POTIONS.register(eventBus);
	}
}