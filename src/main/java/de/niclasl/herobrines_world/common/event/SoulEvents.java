package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.enchantments.ModEnchantments;
import de.niclasl.herobrines_world.common.util.database.PlayerData;
import de.niclasl.herobrines_world.common.util.math.SoulGain;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.sql.SQLException;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class SoulEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) throws SQLException {
        Entity source = event.getSource().getEntity();

        if (!(source instanceof ServerPlayer player)) return;
        if (player.level().isClientSide()) return;

        PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

        ItemStack stack = player.getMainHandItem();

        int enchantLevel = stack.getEnchantmentLevel(
                player.level().registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(ModEnchantments.MORE_SOULS)
        );

        int baseGain = switch (enchantLevel) {
            case 1 -> 2;
            case 2 -> 4;
            default -> 1;
        };

        int soulsGain = SoulGain.getSoulGain(baseGain, data.soulLevel);

        float prestigeBonus = SoulMath.getSoulBonus(data.prestige);

        soulsGain = Math.max(
                1,
                Math.round(soulsGain * prestigeBonus)
        );

        if (data.soulLevel >= SoulMath.HARD_CAP) {
            return;
        }

        data.souls += soulsGain;

        while (data.soulLevel < SoulMath.HARD_CAP &&
                data.souls >= SoulMath.getXPForLevel(data.soulLevel)) {

            data.souls -= SoulMath.getXPForLevel(data.soulLevel);
            data.soulLevel++;
        }

        if (data.soulLevel >= SoulMath.HARD_CAP) {
            data.soulLevel = SoulMath.HARD_CAP;
            data.souls = 0;
        }

        HerobrinesWorld.DATABASE.savePlayerData(data);
    }
}