package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.ModBlocks;
import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, HerobrinesWorld.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        blockModels.createTrivialBlock(
                ModBlocks.LUMBERJACK_TABLE.get(),
                TexturedModel.createDefault(
                        (block) -> new TextureMapping()
                                .put(TextureSlot.DOWN, defaultLoc())
                                .put(TextureSlot.UP, modLoc("block/lumberjack_table_top"))

                                .put(TextureSlot.NORTH, modLoc("block/lumberjack_table_front"))
                                .put(TextureSlot.SOUTH, modLoc("block/lumberjack_table_side"))
                                .put(TextureSlot.EAST, modLoc("block/lumberjack_table_side"))
                                .put(TextureSlot.WEST, modLoc("block/lumberjack_table_front"))

                                .put(TextureSlot.PARTICLE, modLoc("block/lumberjack_table_front")),
                        ModelTemplates.CUBE
                )
        );

        blockModels.createTrivialCube(ModBlocks.ASH_BLOCK.get());

        blockModels.createTrivialBlock(
                ModBlocks.BLUE_SANDSTONE.get(),
                TexturedModel.createDefault(
                        TextureMapping::cubeBottomTop,
                        ModelTemplates.CUBE_BOTTOM_TOP
                )
        );

        blockModels.createTrivialBlock(
                ModBlocks.BLUE_CHISELED_SANDSTONE.get(),
                TexturedModel.createDefault(
                        TextureMapping::column,
                        ModelTemplates.CUBE_COLUMN
                )
        );

        blockModels.family(ModBlocks.BLUE_SMOOTH_SANDSTONE.get())
                .stairs(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get())
                .slab(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get());

        blockModels.createTrivialBlock(
                ModBlocks.BLUE_CUT_SANDSTONE.get(),
                TexturedModel.createDefault(
                        TextureMapping::column,
                        ModelTemplates.CUBE_COLUMN
                )
        );

        blockModels.createTrivialCube(ModBlocks.HEROBRINE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.CURSED_STONE.get());
        blockModels.createTrivialCube(ModBlocks.ABYSSAL_BLOCK.get());

        blockModels.createTrivialCube(ModBlocks.FROZEN_HEART_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_FROZEN_HEART_ORE.get());
        blockModels.createTrivialCube(ModBlocks.GREEN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_GREEN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.ASH_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_ASH_ORE.get());
        blockModels.createTrivialCube(ModBlocks.HEROBRINE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_HEROBRINE_ORE.get());

        blockModels.createTrivialBlock(
                ModBlocks.TOXENIUM_ORE.get(),
                TexturedModel.createDefault(
                        TextureMapping::column,
                        ModelTemplates.CUBE_COLUMN
                )
        );

        /*
         * =========================
         * 🎒 ITEMS
         * =========================
         */

        itemModels.generateFlatItem(ModItems.HEROBRINE_DIAMOND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ASH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ASH_INGOT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.ASH_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(ModItems.FROZEN_HEART.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.HEROBRINE_BOSS_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NICLASL_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ENTITY_303_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GOOD_HEROBRINE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BAD_HEROBRINE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHRISTMAS_NICLASL_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.TOXENIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.NATURE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(ModItems.FIRE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(ModItems.HEROBRINE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(ModItems.TOXENIUM_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(ModItems.NATURE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.FIRE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.HEROBRINE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.TOXENIUM_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.TOXENIUM_SCRAP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TOXENIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.HEROBRINES_REALM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNDERWORLD.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.GREEN_GEMSTONE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RUNE_STONE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_RELIC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PLATIN.get(), ModelTemplates.FLAT_ITEM);
    }

    private Identifier defaultLoc() {
        return Identifier.withDefaultNamespace("block/oak_planks");
    }

    private Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, path);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().filter(x -> !x.is(ModBlocks.HEROBRINES_REALM_PORTAL)
                && !x.is(ModBlocks.UNDERWORLD_PORTAL) && !x.is(ModBlocks.DELAYER) && !x.is(ModBlocks.LOGIC_GATE_BLOCK)
                && !x.is(ModBlocks.BATTERY_CHARGER) && !x.is(ModBlocks.SIGNAL) && !x.is(ModBlocks.AUTO_FARMER)
                && !x.is(ModBlocks.BLUE_SANDSTONE_STAIRS) && !x.is(ModBlocks.BLUE_SANDSTONE_SLAB)
                && !x.is(ModBlocks.BLUE_SANDSTONE_WALL) && !x.is(ModBlocks.BLUE_CUT_SANDSTONE_SLAB));
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream().filter(x -> !x.is(ModItems.BATTERY) &&
                !x.is(ModItems.TIME_CLOCK) && !x.is(ModItems.ORE_DETECTOR)
                && x.get() != ModBlocks.HEROBRINES_REALM_PORTAL.asItem() && x.get() != ModBlocks.UNDERWORLD_PORTAL.asItem()
                && x.get() != ModBlocks.DELAYER.asItem() && x.get() != ModBlocks.LOGIC_GATE_BLOCK.asItem()
                && x.get() != ModBlocks.BATTERY_CHARGER.asItem() && x.get() != ModBlocks.SIGNAL.asItem()
                && x.get() != ModBlocks.AUTO_FARMER.asItem() && x.get() != ModBlocks.BLUE_SANDSTONE_STAIRS.asItem()
                && x.get() != ModBlocks.BLUE_SANDSTONE_SLAB.asItem() && x.get() != ModBlocks.BLUE_SANDSTONE_WALL.asItem()
                && x.get() != ModBlocks.BLUE_CUT_SANDSTONE_SLAB.asItem()
                && !x.is(ModItems.SMART_CHIP) && !x.is(ModItems.SMART_CHIP_CASE));
    }
}