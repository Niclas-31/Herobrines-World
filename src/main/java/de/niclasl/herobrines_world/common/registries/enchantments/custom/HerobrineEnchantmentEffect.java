package de.niclasl.herobrines_world.common.registries.enchantments.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.database.PlayerData;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public record HerobrineEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<HerobrineEnchantmentEffect> CODEC = MapCodec.unit(HerobrineEnchantmentEffect::new);

    @Override
    public void apply(@NotNull ServerLevel level,
                      int enchantmentLevel,
                      @NotNull EnchantedItemInUse item,
                      @NotNull Entity entity,
                      @NotNull Vec3 hitPos) {

        Entity attacker = item.owner();

        if (!(attacker instanceof ServerPlayer player)) {
            return;
        }

        PlayerData data;

        try {
            data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int cost = switch (enchantmentLevel) {
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            default -> 1;
        };

        try {
            if (!tryConsumeSoul(player, data, cost)) {
                return;
            }

            for (int i = 0; i < enchantmentLevel; i++) {
                EntityType.LIGHTNING_BOLT.spawn(
                        level,
                        entity.getOnPos(),
                        EntitySpawnReason.TRIGGERED
                );
            }
        } catch (SQLException e) {
            HerobrinesWorld.LOGGER.error(
                    "Failed to update souls and soulLevel state for {}",
                    player.getUUID(),
                    e
            );
        }
    }

    private static boolean tryConsumeSoul(ServerPlayer player,
                                          PlayerData data,
                                          int cost) throws SQLException {

        if (data.soulLevel >= SoulMath.HARD_CAP) {
            return false;
        }

        if (data.souls < cost && data.soulLevel == 0) {
            player.sendSystemMessage(
                    Component.literal("§cNot enough Souls!")
            );
            return false;
        }

        data.souls -= cost;

        while (data.soulLevel < SoulMath.HARD_CAP
                && data.souls < 0) {

            data.soulLevel--;
            data.souls += SoulMath.getXPForLevel(data.soulLevel);
        }

        if (data.soulLevel < 0) {
            data.soulLevel = 0;
            data.souls = 0;
        }

        HerobrinesWorld.DATABASE.savePlayerData(data);
        return true;
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}