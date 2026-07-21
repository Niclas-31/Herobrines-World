package de.niclasl.herobrines_world.common.registries.villagers;

import com.google.common.collect.ImmutableSet;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, HerobrinesWorld.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, HerobrinesWorld.MOD_ID);

    public static final Holder<PoiType> LUMBER_POI = POI_TYPES.register("lumber_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.LUMBERJACK_TABLE.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> LUMBERJACK = VILLAGER_PROFESSIONS.register("lumberjack",
            () -> new VillagerProfession(
                    Component.literal("lumberjack"),
                    holder -> holder.value() == LUMBER_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == LUMBER_POI.value(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_TOOLSMITH,
                    Int2ObjectMap.ofEntries(
                            Int2ObjectMap.entry(1, ModTradeSets.LUMBERJACK_LEVEL_1),
                            Int2ObjectMap.entry(2, ModTradeSets.LUMBERJACK_LEVEL_2),
                            Int2ObjectMap.entry(3, ModTradeSets.LUMBERJACK_LEVEL_3),
                            Int2ObjectMap.entry(4, ModTradeSets.LUMBERJACK_LEVEL_4),
                            Int2ObjectMap.entry(5, ModTradeSets.LUMBERJACK_LEVEL_5)
                    )
            )
    );

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}