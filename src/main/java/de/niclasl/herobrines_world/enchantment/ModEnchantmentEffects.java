package de.niclasl.herobrines_world.enchantment;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.enchantment.custom.HeartOfTheForestEffect;
import de.niclasl.herobrines_world.enchantment.custom.HerobrineEnchantmentEffect;
import de.niclasl.herobrines_world.enchantment.custom.RootsOfTheEarthEffect;
import de.niclasl.herobrines_world.enchantment.custom.WildernessAcumenEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, HerobrinesWorld.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> HEROBRINE =
            ENTITY_ENCHANTMENT_EFFECTS.register("herobrine", () -> HerobrineEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> HEART_OF_THE_FOREST =
            ENTITY_ENCHANTMENT_EFFECTS.register("heart_of_the_forest", () -> HeartOfTheForestEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> ROOTS_OF_THE_FOREST =
            ENTITY_ENCHANTMENT_EFFECTS.register("roots_of_the_forest", () -> RootsOfTheEarthEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> WILDERNESS_ACUMEN =
            ENTITY_ENCHANTMENT_EFFECTS.register("wilderness", () -> WildernessAcumenEffect.CODEC);

    public static void register(IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}