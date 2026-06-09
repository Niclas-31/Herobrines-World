package de.niclasl.herobrines_world.common.registries.enchantments.custom;

import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.common.network.ModVariables;
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

        ModVariables.PlayerVariables vars =
                player.getData(ModVariables.PLAYER_VARIABLES);

        int cost = switch (enchantmentLevel) {
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            default -> 1;
        };

        if (!tryConsumeSoul(player, vars, cost)) {
            return;
        }

        for (int i = 0; i < enchantmentLevel; i++) {

            EntityType.LIGHTNING_BOLT.spawn(
                    level,
                    entity.getOnPos(),
                    EntitySpawnReason.TRIGGERED
            );
        }
    }

    private static boolean tryConsumeSoul(ServerPlayer player,
                                          ModVariables.PlayerVariables vars,
                                          int cost) {

        if (vars.Souls < cost) {

            player.sendSystemMessage(
                    Component.literal("§cNot enough Souls!")
            );

            return false;
        }

        vars.Souls -= cost;

        vars.markSyncDirty(player);

        return true;
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}