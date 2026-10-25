package de.niclasl.herobrines_world.common.registries.enchantments;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.enchantments.custom.HeartOfTheForestEffect;
import de.niclasl.herobrines_world.common.registries.enchantments.custom.HerobrineEnchantmentEffect;
import de.niclasl.herobrines_world.common.registries.enchantments.custom.RootsOfTheEarthEffect;
import de.niclasl.herobrines_world.common.registries.enchantments.custom.WildernessAcumenEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, HerobrinesWorld.MOD_ID);

    public static void init() {
        ENTITY_ENCHANTMENT_EFFECTS.register("herobrine", () -> HerobrineEnchantmentEffect.CODEC);
        ENTITY_ENCHANTMENT_EFFECTS.register("heart_of_the_forest", () -> HeartOfTheForestEffect.CODEC);
        ENTITY_ENCHANTMENT_EFFECTS.register("roots_of_the_forest", () -> RootsOfTheEarthEffect.CODEC);
        ENTITY_ENCHANTMENT_EFFECTS.register("wilderness", () -> WildernessAcumenEffect.CODEC);
    }

    public static void register(IEventBus eventBus) {
        init();
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}