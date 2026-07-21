package de.niclasl.herobrines_world.common.registries.villagers;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Optional;

public class ModTradeSets {
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_1 = resourceKey("lumberjack/level_1");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_2 = resourceKey("lumberjack/level_2");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_3 = resourceKey("lumberjack/level_3");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_4 = resourceKey("lumberjack/level_4");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_5 = resourceKey("lumberjack/level_5");

    public static void bootstrap(BootstrapContext<TradeSet> context) {
        register(context, LUMBERJACK_LEVEL_1, ModTags.Villagers.LUMBERJACK_LEVEL_1);
        register(context, LUMBERJACK_LEVEL_2, ModTags.Villagers.LUMBERJACK_LEVEL_2);
        register(context, LUMBERJACK_LEVEL_3, ModTags.Villagers.LUMBERJACK_LEVEL_3);
        register(context, LUMBERJACK_LEVEL_4, ModTags.Villagers.LUMBERJACK_LEVEL_4);
        register(context, LUMBERJACK_LEVEL_5, ModTags.Villagers.LUMBERJACK_LEVEL_5);
    }

    public static Holder.Reference<TradeSet> register(BootstrapContext<TradeSet> context, ResourceKey<TradeSet> resourceKey, TagKey<VillagerTrade> tradeTag) {
        return register(context, resourceKey, tradeTag, ConstantValue.exactly(2.0F));
    }

    public static Holder.Reference<TradeSet> register(BootstrapContext<TradeSet> context, ResourceKey<TradeSet> resourceKey, TagKey<VillagerTrade> tradeTag, NumberProvider numberProvider) {
        return context.register(resourceKey, new TradeSet(context.lookup(Registries.VILLAGER_TRADE).getOrThrow(tradeTag), numberProvider, false, Optional.of(resourceKey.identifier().withPrefix("trade_set/"))));
    }

    public static ResourceKey<TradeSet> resourceKey(String path) {
        return ResourceKey.create(Registries.TRADE_SET,
                Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path));
    }
}