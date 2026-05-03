package de.niclasl.herobrines_world.datagen.advancements;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.registries.block.ModBlocks;
import de.niclasl.herobrines_world.registries.item.ModItems;
import de.niclasl.herobrines_world.worldgen.dimension.ModDimensions;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class HerobrinesWorldAdvancements implements AdvancementSubProvider {
    public HerobrinesWorldAdvancements() { super(); }

    @Override
    public void generate(HolderLookup.@NonNull Provider provider, @NonNull Consumer<AdvancementHolder> consumer) {
        AdvancementHolder advancementHolder = Builder.advancement()
                .display(
                        ModBlocks.CURSED_STONE,
                        Component.translatable("advancements.herobrines_world.root.title"),
                        Component.translatable("advancements.herobrines_world.root.descr"),
                        Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "gui/advancements/backgrounds/herobrines_world"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("entered_herobrines_world", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ModDimensions.HEROBRINE_REALM))
                .save(consumer, "herobrines_world:herobrines_world/root");

        AdvancementHolder advancementHolder1 = Builder.advancement()
                .parent(advancementHolder)
                .display(
                        Items.STONE_PICKAXE,
                        Component.translatable("advancements.herobrines_world.mine_green_ore.title"),
                        Component.translatable("advancements.herobrines_world.mine_green_ore.descr"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "get_gemstone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GREEN_GEMSTONE)
                )
                .save(consumer, "herobrines_world:herobrines_world/mine_green_ore");

        AdvancementHolder advancementHolder2 = Builder.advancement()
                .parent(advancementHolder1)
                .display(
                        ModItems.NATURE_PICKAXE,
                        Component.translatable("advancements.herobrines_world.upgrade_tools.title"),
                        Component.translatable("advancements.herobrines_world.upgrade_tools.descr"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "nature_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NATURE_PICKAXE)
                )
                .save(consumer, "herobrines_world:herobrines_world/upgrade_tools");

        AdvancementHolder advancementHolder3 = Builder.advancement()
                .parent(advancementHolder2)
                .display(
                        Items.MAGMA_BLOCK,
                        Component.translatable("advancements.herobrines_world.lava_time.title"),
                        Component.translatable("advancements.herobrines_world.lava_time.descr"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "get_magma", InventoryChangeTrigger.TriggerInstance.hasItems(Items.MAGMA_BLOCK)
                )
                .save(consumer, "herobrines_world:herobrines_world/lava_time");

        AdvancementHolder advancementHolder4 = Builder.advancement()
                .parent(advancementHolder3)
                .display(
                        ModItems.FIRE_PICKAXE,
                        Component.translatable("advancements.herobrines_world.magma_tools.title"),
                        Component.translatable("advancements.herobrines_world.magma_tools.descr"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "fire_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FIRE_PICKAXE)
                )
                .save(consumer, "herobrines_world:herobrines_world/magma_tools");

        Builder.advancement()
                .parent(advancementHolder3)
                .display(
                        ModItems.FIRE_CHESTPLATE,
                        Component.translatable("advancements.herobrines_world.make_a_new_armor.title"),
                        Component.translatable("advancements.herobrines_world.make_a_new_armor.descr"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("fire_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FIRE_HELMET))
                .addCriterion("fire_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FIRE_CHESTPLATE))
                .addCriterion("fire_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FIRE_LEGGINGS))
                .addCriterion("fire_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FIRE_BOOTS))
                .save(consumer, "herobrines_world:herobrines_world/make_a_new_armor");

        Builder.advancement()
                .parent(advancementHolder4)
                .display(
                        ModItems.HEROBRINE_DIAMOND,
                        Component.translatable("advancements.herobrines_world.mine_herobrine_diamond.title"),
                        Component.translatable("advancements.herobrines_world.mine_herobrine_diamond.descr"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "herobrine_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HEROBRINE_DIAMOND)
                )
                .save(consumer, "herobrines_world:herobrines_world/mine_herobrine_diamond");
    }
}