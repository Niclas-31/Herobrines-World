package de.niclasl.herobrines_world.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.entity.custom.HerobrineBoss;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.potion.ModPotions;
import de.niclasl.herobrines_world.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = HerobrinesWorld.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.RED_DYE, ModPotions.GOOD_HEROBRINE_OMEN);
        builder.addMix(Potions.AWKWARD, Items.BLACK_DYE, ModPotions.BAD_HEROBRINE_OMEN);
    }

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

    private static final int SOUL_PER_LEVEL = 100;

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (attacker.level().isClientSide()) return;

        ModVariables.PlayerVariables vars = attacker.getData(ModVariables.PLAYER_VARIABLES);

        ItemStack stack = attacker.getMainHandItem();
        int level = stack.getEnchantmentLevel(
                attacker.level().registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(ResourceKey.create(
                                Registries.ENCHANTMENT,
                                Identifier.parse("herobrines_world:more_souls")
                        ))
        );

        int soulsGain = 1;
        if (level == 1) soulsGain = 2;
        else if (level == 2) soulsGain = 4;

        vars.Soul_Current += soulsGain;

        while (vars.Soul_Current >= SOUL_PER_LEVEL) {
            vars.Soul_Current -= SOUL_PER_LEVEL;
            vars.Soul_Level += 1;
        }

        vars.markSyncDirty();
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            HerobrineBoss.onOwnerDeath(player);
        }
    }
}