package de.niclasl.herobrines_world.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.entity.custom.HerobrineBoss;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.util.ModGameRules;
import de.niclasl.herobrines_world.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = HerobrinesWorld.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.LUMBERJACK.getKey()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.OAK_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.BIRCH_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.SPRUCE_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.JUNGLE_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.ACACIA_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.DARK_OAK_LOG, 16),
                    new ItemStack(Items.EMERALD), 10, 5, 0.05f));

            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.MANGROVE_LOG, 16),
                    new ItemStack(Items.EMERALD, 2), 15, 10, 0.08f));

            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.BAMBOO_BLOCK, 16),
                    new ItemStack(Items.EMERALD, 2), 15, 10, 0.08f));

            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.CHERRY_LOG, 16),
                    new ItemStack(Items.EMERALD, 2), 15, 10, 0.08f));

            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.PALE_OAK_LOG, 16),
                    new ItemStack(Items.EMERALD, 3), 20, 15, 0.11f));

            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.WARPED_STEM, 16),
                    new ItemStack(Items.EMERALD, 3), 20, 15, 0.11f));

            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.CRIMSON_STEM, 16),
                    new ItemStack(Items.EMERALD, 3), 20, 15, 0.11f));

            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(Items.IRON_AXE), 1, 20, 0.14f));

            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 10),
                    new ItemStack(Items.DIAMOND_AXE), 1, 20, 0.14f));

            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 10),
                    new ItemStack(Items.NETHERITE_AXE), 1, 25, 0.17f));
        }
    }


    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;

        timerUpdate(event.getEntity());
        setTimer(event.getEntity().level());
        lootBoxSpawning(event.getEntity(), event.getEntity().level());
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
                                ResourceLocation.parse("herobrines_world:more_souls")
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

    private static void timerUpdate(Entity entity) {
        if (entity == null)
            return;
        if (entity.getData(ModVariables.PLAYER_VARIABLES).TimerActive) {
            ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);
            vars.Ticks = entity.getData(ModVariables.PLAYER_VARIABLES).Ticks + 1;
            vars.markSyncDirty();

            if (entity.getData(ModVariables.PLAYER_VARIABLES).Ticks == 20) {
                vars.Ticks = 0;
                vars.Second = entity.getData(ModVariables.PLAYER_VARIABLES).Second + 1;
                vars.markSyncDirty();

                if (entity.getData(ModVariables.PLAYER_VARIABLES).Second >= 60) {
                    vars.Second = 0;
                    vars.Minute = entity.getData(ModVariables.PLAYER_VARIABLES).Minute + 1;
                    vars.markSyncDirty();

                    if (entity.getData(ModVariables.PLAYER_VARIABLES).Minute >= 60) {
                        vars.Minute = 0;
                        vars.Hour = entity.getData(ModVariables.PLAYER_VARIABLES).Hour + 1;
                        vars.markSyncDirty();

                        if (entity.getData(ModVariables.PLAYER_VARIABLES).Hour >= 24) {
                            vars.Hour = 0;
                            vars.Day = entity.getData(ModVariables.PLAYER_VARIABLES).Day + 1;
                            vars.markSyncDirty();
                        }
                    }
                }
            }
        }
    }

    private static void setTimer(LevelAccessor world) {
        if (ModVariables.MapVariables.get(world).LootBoxTimer == 0) {
            ModVariables.MapVariables.get(world).LootBoxTimer = (world instanceof ServerLevel serverLevel ? serverLevel.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0);
            ModVariables.MapVariables.get(world).markSyncDirty();
        }
        if ((world instanceof ServerLevel serverLevel ? serverLevel.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0) != ModVariables.MapVariables.get(world).LastSpawnLootBoxTimer) {
            ModVariables.MapVariables.get(world).LootBoxTimer = (world instanceof ServerLevel serverLevel ? serverLevel.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0);
            ModVariables.MapVariables.get(world).LastSpawnLootBoxTimer = (world instanceof ServerLevel serverLevel ? serverLevel.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0);
            ModVariables.MapVariables.get(world).markSyncDirty();
        }
    }

    private static void lootBoxSpawning(Entity entity, LevelAccessor world) {
        if (entity == null)
            return;
        if ((world instanceof ServerLevel serverLevel && serverLevel.getGameRules().getBoolean(ModGameRules.CAN_LOOT_BOX_SPAWN))) {
            if (ModVariables.MapVariables.get(world).LootBoxTimer > 0) {
                ModVariables.MapVariables.get(world).LootBoxTimer = ModVariables.MapVariables.get(world).LootBoxTimer - 1;
                ModVariables.MapVariables.get(world).markSyncDirty();
            }
            if (ModVariables.MapVariables.get(world).LootBoxTimer == 0) {
                if (world instanceof ServerLevel serverWorld) {
                    StructureTemplate template = serverWorld.getStructureManager().getOrCreate(ResourceLocation.fromNamespaceAndPath("herobrines_world", "loot_box/loot_box"));
                    template.placeInWorld(serverWorld,
                            BlockPos.containing(entity.getData(ModVariables.PLAYER_VARIABLES).X, entity.getData(ModVariables.PLAYER_VARIABLES).Y, entity.getData(ModVariables.PLAYER_VARIABLES).Z),
                            BlockPos.containing(entity.getData(ModVariables.PLAYER_VARIABLES).X, entity.getData(ModVariables.PLAYER_VARIABLES).Y, entity.getData(ModVariables.PLAYER_VARIABLES).Z),
                            new StructurePlaceSettings().setRotation(Rotation.getRandom(serverWorld.random)).setMirror(Mirror.values()[serverWorld.random.nextInt(2)]).setIgnoreEntities(false), serverWorld.random, 3);
                }
            }
            if (ModVariables.MapVariables.get(world).LootBoxTimer <= (world instanceof ServerLevel serverWorld ? serverWorld.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0)) {
                if (entity instanceof Player player && !player.level().isClientSide())
                    player.displayClientMessage(Component.literal(("In §2" + new java.text.DecimalFormat("###,###,###").format(ModVariables.MapVariables.get(world).LootBoxTimer) + " §fTicks is coming a new loot box!")), true);
            }
        }
    }
}