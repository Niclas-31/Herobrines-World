package de.niclasl.herobrines_world.common.registries.blocks.entities;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, HerobrinesWorld.MOD_ID);

    public static final Supplier<BlockEntityType<DelayerBlockEntity>> DELAYER =
            BLOCK_ENTITIES.register("delayer", () -> new BlockEntityType<>(
                    DelayerBlockEntity::new, ModBlocks.DELAYER.get()));

    public static final Supplier<BlockEntityType<LogicGateBlockEntity>> LOGIC_GATE_BLOCK =
            BLOCK_ENTITIES.register("logic_gate_block", () -> new BlockEntityType<>(
                    LogicGateBlockEntity::new, ModBlocks.LOGIC_GATE_BLOCK.get()));

    public static final Supplier<BlockEntityType<AutoFarmerBlockEntity>> AUTO_FARMER =
            BLOCK_ENTITIES.register("auto_farmer", () -> new BlockEntityType<>(
                    AutoFarmerBlockEntity::new, ModBlocks.AUTO_FARMER.get()));

    public static final Supplier<BlockEntityType<BatteryChargerBlockEntity>> BATTERY_CHARGER =
            BLOCK_ENTITIES.register("battery_charger", () -> new BlockEntityType<>(
                    BatteryChargerBlockEntity::new, ModBlocks.BATTERY_CHARGER.get()));

    public static final Supplier<BlockEntityType<StorageControllerBlockEntity>> STORAGE_CONTROLLER =
            BLOCK_ENTITIES.register("storage_controller",  () -> new BlockEntityType<>(
                    StorageControllerBlockEntity::new, ModBlocks.STORAGE_CONTROLLER.get()));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}