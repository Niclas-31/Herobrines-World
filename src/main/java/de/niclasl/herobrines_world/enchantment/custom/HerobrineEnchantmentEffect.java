package de.niclasl.herobrines_world.enchantment.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record HerobrineEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<HerobrineEnchantmentEffect> CODEC = MapCodec.unit(HerobrineEnchantmentEffect::new);

    private static final int SOUL_PER_LEVEL = 100;

    @Override
    public void apply(@NotNull ServerLevel level,
                      int enchantmentLevel,
                      @NotNull EnchantedItemInUse item,
                      @NotNull Entity entity,
                      @NotNull Vec3 hitPos) {

        Entity attacker = item.owner();

        if (attacker instanceof ServerPlayer player) {
            ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

            if (enchantmentLevel == 1) {
                if (tryConsumeSoul(vars, 5)) {
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                }
            } else if (enchantmentLevel == 2) {
                if (tryConsumeSoul(vars, 10)) {
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                }
            } else if (enchantmentLevel == 3) {
                if (tryConsumeSoul(vars, 15)) {
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                }
            } else if (enchantmentLevel == 4) {
                if (tryConsumeSoul(vars, 20)) {
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                    EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
                }
            }
        }
    }

    private static boolean tryConsumeSoul(ModVariables.PlayerVariables vars, int cost) {
        int totalSoul =
                vars.Soul_Current +
                        vars.Soul_Level * SOUL_PER_LEVEL;

        if (totalSoul < cost) {
            return false;
        }

        totalSoul -= cost;

        vars.Soul_Level = totalSoul / SOUL_PER_LEVEL;
        vars.Soul_Current = totalSoul % SOUL_PER_LEVEL;

        vars.markSyncDirty();
        return true;
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
