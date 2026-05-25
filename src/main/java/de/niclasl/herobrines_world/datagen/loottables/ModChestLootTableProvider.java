package de.niclasl.herobrines_world.datagen.loottables;

import de.niclasl.herobrines_world.common.registries.item.ModItems;
import de.niclasl.herobrines_world.common.util.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ModChestLootTableProvider(HolderLookup.Provider registries) implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(ModLootTables.ASH_DESERT_PYRAMID, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4, 7))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                .setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(7, 10))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_HELMET)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_CHESTPLATE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_LEGGINGS)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_BOOTS)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_SWORD)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_AXE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_PICKAXE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_SHOVEL)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_HOE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_DIAMOND)
                                .setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 7))))
                )
        );

        biConsumer.accept(ModLootTables.COPPER_DUNGEON, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(6, 7))
                        .add(LootItem.lootTableItem(ModItems.HEROBRINE_DIAMOND)
                                .setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.MAGMA_BLOCK)
                                .setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(ModItems.GREEN_GEMSTONE)
                                .setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(ModItems.FROZEN_HEART)
                                .setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
        );

        biConsumer.accept(ModLootTables.THRONE_OF_THE_UNDERWORLD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 12))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_HELMET)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_CHESTPLATE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_LEGGINGS)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_BOOTS)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_SWORD)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_AXE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_PICKAXE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_SHOVEL)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_HOE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(ModItems.PLATIN_INGOT)
                                .setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 6))))
                )
        );
    }
}