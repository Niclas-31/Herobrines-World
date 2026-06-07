package de.niclasl.herobrines_world.datagen;

import com.google.common.collect.ImmutableMap;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.block.ModBlocks;
import de.niclasl.herobrines_world.common.registries.item.ModArmorMaterials;
import de.niclasl.herobrines_world.common.registries.item.ModItems;
import de.niclasl.herobrines_world.datagen.mapping.ModMapping;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public static final Map<Block, TexturedModel> TEXTURED_MODELS = ImmutableMap.<Block, TexturedModel>builder()
            .put(ModBlocks.BLUE_SANDSTONE.get(), TexturedModel.TOP_BOTTOM_WITH_WALL.get(ModBlocks.BLUE_SANDSTONE.get()))
            .put(ModBlocks.BLUE_SMOOTH_SANDSTONE.get(), TexturedModel.createAllSame(TextureMapping.getBlockTexture(ModBlocks.BLUE_SANDSTONE.get(), "_top")))
            .put(
                    ModBlocks.BLUE_CUT_SANDSTONE.get(),
                    TexturedModel.COLUMN
                            .get(ModBlocks.BLUE_SANDSTONE.get())
                            .updateTextures(p_465393_ -> p_465393_.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.BLUE_CUT_SANDSTONE.get())))
            )
            .put(ModBlocks.BLUE_CHISELED_SANDSTONE.get(), TexturedModel.COLUMN.get(ModBlocks.BLUE_CHISELED_SANDSTONE.get()).updateTextures(p_465378_ -> {
                p_465378_.put(TextureSlot.END, TextureMapping.getBlockTexture(ModBlocks.BLUE_SANDSTONE.get(), "_top"));
                p_465378_.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.BLUE_CHISELED_SANDSTONE.get()));
            }))
            .build();

    public ModModelProvider(PackOutput output) {
        super(output, HerobrinesWorld.MODID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ModMapping.createLumberjackTable(blockModels);
        ModMapping.createSignal(blockModels);
        blockModels.createTrivialCube(ModBlocks.ASH_BLOCK.get());
        // Herobrines Realm Portal
        // Underworld Portal
        family(blockModels, ModBlocks.BLUE_SANDSTONE.get()).wall(ModBlocks.BLUE_SANDSTONE_WALL.get()).slab(ModBlocks.BLUE_SANDSTONE_SLAB.get()).stairs(ModBlocks.BLUE_SANDSTONE_STAIRS.get());
        family(blockModels, ModBlocks.BLUE_CHISELED_SANDSTONE.get());
        family(blockModels, ModBlocks.BLUE_SMOOTH_SANDSTONE.get()).stairs(ModBlocks.BLUE_SMOOTH_SANDSTONE_STAIRS.get()).slab(ModBlocks.BLUE_SMOOTH_SANDSTONE_SLAB.get());
        family(blockModels, ModBlocks.BLUE_CUT_SANDSTONE.get()).slab(ModBlocks.BLUE_CUT_SANDSTONE_SLAB.get());
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
        blockModels.createTrivialCube(ModBlocks.PLATINE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_PLATIN_ORE.get());
        // Delayer
        // Logic Gate Block
        ModMapping.createAutoFarmer(blockModels);
        // Battery Charger
        ModMapping.createStorageController(blockModels);
        ModMapping.createCardReader(blockModels);

        itemModels.generateFlatItem(ModItems.HEROBRINE_DIAMOND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ASH_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ASH_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FROZEN_HEART.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_BOSS_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NICLASL_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ENTITY_303_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GOOD_HEROBRINE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BAD_HEROBRINE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHRISTMAS_NICLASL_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        // Ore Detector
        itemModels.generateFlatItem(ModItems.NATURE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NATURE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.HEROBRINE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PLATIN_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PLATIN_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PLATIN_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PLATIN_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PLATIN_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateTrimmableItem(ModItems.NATURE_HELMET.get(), ModArmorMaterials.NATURE, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.NATURE_CHESTPLATE.get(), ModArmorMaterials.NATURE, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.NATURE_LEGGINGS.get(), ModArmorMaterials.NATURE, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.NATURE_BOOTS.get(), ModArmorMaterials.NATURE, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModels.generateTrimmableItem(ModItems.FIRE_HELMET.get(), ModArmorMaterials.FIRE, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.FIRE_CHESTPLATE.get(), ModArmorMaterials.FIRE, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.FIRE_LEGGINGS.get(), ModArmorMaterials.FIRE, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.FIRE_BOOTS.get(), ModArmorMaterials.FIRE, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModels.generateTrimmableItem(ModItems.HEROBRINE_HELMET.get(), ModArmorMaterials.HEROBRINE, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.HEROBRINE_CHESTPLATE.get(), ModArmorMaterials.HEROBRINE, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.HEROBRINE_LEGGINGS.get(), ModArmorMaterials.HEROBRINE, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.HEROBRINE_BOOTS.get(), ModArmorMaterials.HEROBRINE, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModels.generateTrimmableItem(ModItems.PLATIN_HELMET.get(), ModArmorMaterials.PLATIN, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.PLATIN_CHESTPLATE.get(), ModArmorMaterials.PLATIN, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.PLATIN_LEGGINGS.get(), ModArmorMaterials.PLATIN, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.PLATIN_BOOTS.get(), ModArmorMaterials.PLATIN, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModels.generateFlatItem(ModItems.HEROBRINES_REALM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNDERWORLD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GREEN_GEMSTONE.get(), ModelTemplates.FLAT_ITEM);
        ModMapping.generateStandardCompassItem(itemModels, ModItems.WAYPOINT_COMPASS.get());
        itemModels.generateFlatItem(ModItems.HEROBRINE_RELIC.get(), ModelTemplates.FLAT_ITEM);
        // battery
        // Smart Chip & Case
        itemModels.generateFlatItem(ModItems.PLATIN_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KEY_CARD.get(), ModelTemplates.FLAT_ITEM);
    }

    public BlockModelGenerators.BlockFamilyProvider family(BlockModelGenerators generators, Block block) {
        TexturedModel texturedmodel = TEXTURED_MODELS.getOrDefault(block, TexturedModel.CUBE.get(block));
        return generators.new BlockFamilyProvider(texturedmodel.getMapping()).fullBlock(block, texturedmodel.getTemplate());
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().filter(x ->
                x.get() != ModBlocks.HEROBRINES_REALM_PORTAL.get()
                && x.get() != ModBlocks.UNDERWORLD_PORTAL.get() && x.get() != ModBlocks.DELAYER.get()
                && x.get() != ModBlocks.LOGIC_GATE_BLOCK.get() && x.get() != ModBlocks.BATTERY_CHARGER.get());
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream().filter(x ->
                x.get() != ModItems.BATTERY.get() && x.get() != ModItems.ORE_DETECTOR.get()
                && x.get() != ModBlocks.HEROBRINES_REALM_PORTAL.asItem() && x.get() != ModBlocks.UNDERWORLD_PORTAL.asItem()
                && x.get() != ModBlocks.DELAYER.asItem() && x.get() != ModBlocks.LOGIC_GATE_BLOCK.asItem()
                && x.get() != ModBlocks.BATTERY_CHARGER.asItem() && x.get() != ModItems.SMART_CHIP.get()
                && x.get() != ModItems.SMART_CHIP_CASE.get());
    }
}