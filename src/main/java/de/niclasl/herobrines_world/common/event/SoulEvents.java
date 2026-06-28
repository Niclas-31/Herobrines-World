package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world.common.registries.enchantments.ModEnchantments;
import de.niclasl.herobrines_world.common.util.math.SoulGain;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class SoulEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {

        Entity source = event.getSource().getEntity();

        if (!(source instanceof ServerPlayer player)) return;
        if (player.level().isClientSide()) return;

        ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

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

        int playerLevel = vars.soulLevel;

        int soulsGain = SoulGain.getSoulGain(baseGain, playerLevel);

        float prestigeBonus = SoulMath.getSoulBonus(vars.prestige);

        soulsGain = Math.max(
                1,
                Math.round(soulsGain * prestigeBonus)
        );

        if (vars.soulLevel >= SoulMath.HARD_CAP) {
            vars.markSyncDirty(player);
            return;
        }

        vars.souls += soulsGain;

        while (vars.soulLevel < SoulMath.HARD_CAP &&
                vars.souls >= SoulMath.getXPForLevel(vars.soulLevel)) {

            vars.souls -= SoulMath.getXPForLevel(vars.soulLevel);
            vars.soulLevel++;
        }

        if (vars.soulLevel >= SoulMath.HARD_CAP) {
            vars.soulLevel = SoulMath.HARD_CAP;
            vars.souls = 0;
        }

        vars.markSyncDirty(player);
    }
}