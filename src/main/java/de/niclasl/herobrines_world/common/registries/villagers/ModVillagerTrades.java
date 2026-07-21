package de.niclasl.herobrines_world.common.registries.villagers;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {
    public static final ResourceKey<VillagerTrade> LUMBERJACK_1_OAK_LOG_EMERALD = resourceKey("lumberjack/1/oak_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_1_BIRCH_LOG_EMERALD = resourceKey("lumberjack/1/birch_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_1_SPRUCE_LOG_EMERALD = resourceKey("lumberjack/1/spruce_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_1_JUNGLE_LOG_EMERALD = resourceKey("lumberjack/1/jungle_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_1_ACACIA_LOG_EMERALD = resourceKey("lumberjack/1/acacia_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_1_DARK_OAK_LOG_EMERALD = resourceKey("lumberjack/1/dark_oak_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_2_MANGROVE_LOG_EMERALD = resourceKey("lumberjack/2/mangrove_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_2_BAMBOO_BLOCK_EMERALD = resourceKey("lumberjack/2/bamboo_block_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_2_CHERRY_LOG_EMERALD = resourceKey("lumberjack/2/cherry_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_3_PALE_OAK_LOG_EMERALD = resourceKey("lumberjack/3/pale_oak_log_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_3_WARPED_STEM_EMERALD = resourceKey("lumberjack/3/warped_stem_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_3_CRIMSON_STEM_EMERALD = resourceKey("lumberjack/3/crimson_stem_emerald");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_4_EMERALD_IRON_AXE = resourceKey("lumberjack/4/emerald_iron_axe");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_4_EMERALD_DIAMOND_AXE = resourceKey("lumberjack/4/emerald_diamond_axe");
    public static final ResourceKey<VillagerTrade> LUMBERJACK_5_EMERALD_NETHERITE_AXE = resourceKey("lumberjack/5/netherite_axe");

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {
        register(context, LUMBERJACK_1_OAK_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.OAK_LOG, 16), new ItemStackTemplate(Items.EMERALD), 10, 5, 0.05f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_1_BIRCH_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.BIRCH_LOG, 16), new ItemStackTemplate(Items.EMERALD), 10, 5, 0.05f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_1_SPRUCE_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.SPRUCE_LOG, 16), new ItemStackTemplate(Items.EMERALD), 10, 5, 0.05f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_1_JUNGLE_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.JUNGLE_LOG, 16), new ItemStackTemplate(Items.EMERALD), 10, 5, 0.05f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_1_ACACIA_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.ACACIA_LOG, 16), new ItemStackTemplate(Items.EMERALD), 10, 5, 0.05f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_1_DARK_OAK_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.DARK_OAK_LOG, 16), new ItemStackTemplate(Items.EMERALD), 10, 5, 0.05f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_2_MANGROVE_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.MANGROVE_LOG, 16), new ItemStackTemplate(Items.EMERALD, 2), 15, 10, 0.08f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_2_BAMBOO_BLOCK_EMERALD, new VillagerTrade(new TradeCost(Items.BAMBOO_BLOCK, 16), new ItemStackTemplate(Items.EMERALD, 2), 15, 10, 0.08f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_2_CHERRY_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.CHERRY_LOG, 16), new ItemStackTemplate(Items.EMERALD, 2), 15, 10, 0.08f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_3_PALE_OAK_LOG_EMERALD, new VillagerTrade(new TradeCost(Items.PALE_OAK_LOG, 16), new ItemStackTemplate(Items.EMERALD, 3), 20, 15, 0.11f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_3_WARPED_STEM_EMERALD, new VillagerTrade(new TradeCost(Items.WARPED_STEM, 16), new ItemStackTemplate(Items.EMERALD, 3), 20, 15, 0.11f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_3_CRIMSON_STEM_EMERALD, new VillagerTrade(new TradeCost(Items.CRIMSON_STEM, 16), new ItemStackTemplate(Items.EMERALD, 3), 20, 15, 0.11f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_4_EMERALD_IRON_AXE, new VillagerTrade(new TradeCost(Items.EMERALD, 5), new ItemStackTemplate(Items.IRON_AXE), 1, 20, 0.14f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_4_EMERALD_DIAMOND_AXE, new VillagerTrade(new TradeCost(Items.EMERALD, 10), new ItemStackTemplate(Items.DIAMOND_AXE), 1, 20, 0.14f, Optional.empty(), List.of()));
        register(context, LUMBERJACK_5_EMERALD_NETHERITE_AXE, new VillagerTrade(new TradeCost(Items.EMERALD, 15), new ItemStackTemplate(Items.NETHERITE_AXE), 1, 25, 0.17f, Optional.empty(), List.of()));
    }

    public static ResourceKey<VillagerTrade> resourceKey(String path) {
        return ResourceKey.create(Registries.VILLAGER_TRADE,
                Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path));
    }

    public static Holder.Reference<VillagerTrade> register(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> resourceKey, VillagerTrade villagerTrade) {
        return context.register(resourceKey, villagerTrade);
    }
}