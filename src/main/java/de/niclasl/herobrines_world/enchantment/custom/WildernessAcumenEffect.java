package de.niclasl.herobrines_world.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WildernessAcumenEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<WildernessAcumenEffect> CODEC = MapCodec.unit(WildernessAcumenEffect::new);

    @Override
    public void apply(@NotNull ServerLevel level,
                      int enchantmentLevel,
                      @NotNull EnchantedItemInUse item,
                      @NotNull Entity entity,
                      @NotNull Vec3 hitPos) {

        if (!(entity instanceof LivingEntity living))
            return;

        if (level.isClientSide())
            return;

        if (living.tickCount % 20 != 0)
            return;

        int amplifier = Math.min(enchantmentLevel - 1, 2);

        living.addEffect(
                new MobEffectInstance(
                        MobEffects.NIGHT_VISION,
                        240,
                        amplifier,
                        false,
                        false
                )
        );
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
