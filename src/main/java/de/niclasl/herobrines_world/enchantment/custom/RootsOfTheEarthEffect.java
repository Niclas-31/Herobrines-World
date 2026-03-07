package de.niclasl.herobrines_world.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record RootsOfTheEarthEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<RootsOfTheEarthEffect> CODEC = MapCodec.unit(RootsOfTheEarthEffect::new);

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

        BlockPos below = BlockPos.containing(
                hitPos.x(),
                hitPos.y() - 1,
                hitPos.z()
        );

        Block blockBelow = level.getBlockState(below).getBlock();

        boolean validGround =
                blockBelow == Blocks.GRASS_BLOCK ||
                        blockBelow == Blocks.DIRT;

        if (!validGround)
            return;

        int amplifier = enchantmentLevel - 1;

        living.addEffect(
                new MobEffectInstance(
                        MobEffects.SPEED,
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
