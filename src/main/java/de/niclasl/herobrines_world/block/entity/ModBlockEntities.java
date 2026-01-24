package de.niclasl.herobrines_world.block.entity;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.ModBlocks;
import de.niclasl.herobrines_world.block.entity.custom.DelayerEntity;
import de.niclasl.herobrines_world.block.entity.custom.RedEnchantmentTableBlockEntity;
import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import de.niclasl.herobrines_world.block.entity.custom.WirelessSenderBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, HerobrinesWorld.MODID);

    public static final Supplier<BlockEntityType<RedEnchantmentTableBlockEntity>> RED_ENCHANTMENT_TABLE =
            BLOCK_ENTITIES.register("red_enchantment_table", () -> new BlockEntityType<>(
                    RedEnchantmentTableBlockEntity::new, ModBlocks.RED_ENCHANTMENT_TABLE.get()));

    public static final Supplier<BlockEntityType<DelayerEntity>> DELAYER =
            BLOCK_ENTITIES.register("delayer", () -> new BlockEntityType<>(
                    DelayerEntity::new, ModBlocks.DELAYER.get()));

    public static final Supplier<BlockEntityType<WirelessSenderBlockEntity>> WIRELESS_SENDER =
            BLOCK_ENTITIES.register("wireless_sender_block", () -> new BlockEntityType<>(
                    WirelessSenderBlockEntity::new, ModBlocks.WIRELESS_SENDER_BLOCK.get()));

    public static final Supplier<BlockEntityType<WirelessReceiverBlockEntity>> WIRELESS_RECEIVER =
            BLOCK_ENTITIES.register("wireless_receiver_block", () -> new BlockEntityType<>(
                    WirelessReceiverBlockEntity::new, ModBlocks.WIRELESS_RECEIVER_BLOCK.get()
            ));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
