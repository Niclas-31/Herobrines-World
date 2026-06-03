package de.niclasl.herobrines_world.common.registries.components;

import com.mojang.serialization.Codec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, HerobrinesWorld.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = register(
            "energy",
            builder -> builder.persistent(Codec.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SmartChipData.Transfer>> TRANSFER = register(
            "transfer",
            builder -> builder.persistent(SmartChipData.Transfer.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SmartChipData.Access>> ACCESS = register(
            "access",
            builder -> builder.persistent(SmartChipData.Access.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RelicData>> RELIC_DATA = register(
            "relic_data",
            builder -> builder.persistent(RelicData.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ORE_MODE = register(
            "ore_mode",
            builder -> builder.persistent(Codec.STRING));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RuneData>> RUNE_DATA = register(
            "rune_data",
            builder -> builder.persistent(RuneData.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<SavedWaypoint>>> WAYPOINTS = register(
            "waypoint",
            builder -> builder.persistent(Codec.list(SavedWaypoint.CODEC)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> SELECTED_WAYPOINT = register(
            "selected_waypoint",
            builder -> builder.persistent(UUIDUtil.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<KeyCardData>> KEY_CARD_DATA = register(
            "key_card_data",
            builder -> builder.persistent(KeyCardData.CODEC)
    );

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                           UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}