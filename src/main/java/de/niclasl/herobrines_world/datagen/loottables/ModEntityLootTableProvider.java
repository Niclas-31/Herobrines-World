package de.niclasl.herobrines_world.datagen.loottables;

import de.niclasl.herobrines_world.common.registries.entities.ModEntities;
import de.niclasl.herobrines_world.common.registries.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

public class ModEntityLootTableProvider extends EntityLootSubProvider {
    public ModEntityLootTableProvider(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        this.add(ModEntities.BAD_HEROBRINE.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 3))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                .setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(ModItems.ASH_PICKAXE)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(ModItems.ASH_INGOT)
                                .setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                ));

        this.add(ModEntities.GOOD_HEROBRINE.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                .setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                ));
    }

    @Override
    protected @NonNull Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
                ModEntities.BAD_HEROBRINE.get(),
                ModEntities.GOOD_HEROBRINE.get()
        );
    }
}