package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.ModVariables;
import de.niclasl.herobrines_world.common.registries.enchantments.ModEnchantments;
import de.niclasl.herobrines_world.common.registries.villagers.ModVillagers;
import de.niclasl.herobrines_world.common.util.math.SoulGain;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.LUMBERJACK) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.OAK_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.BIRCH_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.SPRUCE_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.JUNGLE_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.ACACIA_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.DARK_OAK_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(2).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.MANGROVE_LOG, 16),
                    new ItemStack(Items.EMERALD, 2), 15, 10, 0.08f));

            trades.get(2).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.BAMBOO_BLOCK, 16),
                    new ItemStack(Items.EMERALD, 2), 15, 10, 0.08f));

            trades.get(2).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.CHERRY_LOG, 16),
                    new ItemStack(Items.EMERALD, 2), 15, 10, 0.08f));

            trades.get(3).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.PALE_OAK_LOG, 16),
                    new ItemStack(Items.EMERALD, 3), 20, 15, 0.11f));

            trades.get(3).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.WARPED_STEM, 16),
                    new ItemStack(Items.EMERALD, 3), 20, 15, 0.11f));

            trades.get(3).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.CRIMSON_STEM, 16),
                    new ItemStack(Items.EMERALD, 3), 20, 15, 0.11f));

            trades.get(4).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(Items.IRON_AXE), 1, 20, 0.14f));

            trades.get(4).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 10),
                    new ItemStack(Items.DIAMOND_AXE), 1, 20, 0.14f));

            trades.get(5).add((level, entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 15),
                    new ItemStack(Items.NETHERITE_AXE), 1, 25, 0.17f));
        }
    }

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

        int playerLevel = vars.SoulLevel;

        int soulsGain = SoulGain.getSoulGain(baseGain, playerLevel);

        float prestigeBonus = SoulMath.getSoulBonus(vars.Prestige);

        soulsGain = Math.max(
                1,
                Math.round(soulsGain * prestigeBonus)
        );

        if (vars.SoulLevel >= SoulMath.HARD_CAP) {
            vars.markSyncDirty(player);
            return;
        }

        vars.Souls += soulsGain;

        while (vars.SoulLevel < SoulMath.HARD_CAP &&
                vars.Souls >= SoulMath.getXPForLevel(vars.SoulLevel)) {

            vars.Souls -= SoulMath.getXPForLevel(vars.SoulLevel);
            vars.SoulLevel++;
        }

        if (vars.SoulLevel >= SoulMath.HARD_CAP) {
            vars.SoulLevel = SoulMath.HARD_CAP;
            vars.Souls = 0;
        }

        vars.markSyncDirty(player);
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

        if (player.level().getLevelData().isHardcore()) return;

        if (!vars.ThreeHearts) return;

        vars.Hearts = Math.max(0, vars.Hearts - 1);
        vars.markSyncDirty(player);

        if (vars.Hearts <= 0) {
            player.setGameMode(GameType.SPECTATOR);
        }
    }
}