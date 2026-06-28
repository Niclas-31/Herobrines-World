package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.entities.ModEntities;
import de.niclasl.herobrines_world.common.registries.potions.ModPotions;
import de.niclasl.herobrines_world.common.registries.villagers.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.Calendar;
import java.util.List;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class RegistriesEvents {

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

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.NICLASL.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> {

                    Calendar calendar = Calendar.getInstance();

                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    boolean christmasDays = day >= 24 && day <= 26;

                    return month != Calendar.DECEMBER || !christmasDays;
                },
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(ModEntities.GOOD_HEROBRINE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PathfinderMob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(
                ModEntities.CHRISTMAS_NICLASL.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> {

                    var biome = world.getBiome(pos);

                    if (biome.is(Identifier.parse("herobrines_world:frozen_forest"))) {
                        return true;
                    }

                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    return biome.is(Identifier.parse("herobrines_world:fire_land"))
                            && calendar.get(Calendar.MONTH) == Calendar.DECEMBER
                            && day >= 24
                            && day <= 26;
                },
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(ModEntities.BAD_HEROBRINE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}