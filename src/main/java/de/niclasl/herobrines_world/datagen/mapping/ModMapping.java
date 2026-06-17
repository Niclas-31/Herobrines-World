package de.niclasl.herobrines_world.datagen.mapping;

import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import de.niclasl.herobrines_world.common.registries.blocks.custom.AutoFarmerBlock;
import de.niclasl.herobrines_world.common.registries.blocks.custom.CardReaderBlock;
import de.niclasl.herobrines_world.common.registries.blocks.custom.SignalBlock;
import de.niclasl.herobrines_world.common.registries.blocks.custom.StorageControllerBlock;
import de.niclasl.herobrines_world.common.registries.blocks.properties.ColorProperty;
import de.niclasl.herobrines_world.common.registries.blocks.properties.FarmerMode;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.CompassAngle;
import net.minecraft.client.renderer.item.properties.numeric.CompassAngleState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModMapping {
    private static TextureMapping autoFarmerTextureMapping(Block block, FarmerMode mode, boolean powered) {
        String poweredSuffix = powered ? "_on" : "";
        String suffix = "_" + mode.getSerializedName() + "_side" + poweredSuffix;
        return new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, suffix));
    }

    private static TextureMapping cardReaderTextureMapping(Block block, String frontSuffix) {
        return new TextureMapping()
                .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, frontSuffix))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block));
    }

    private static TextureMapping storageControllerTextureMapping(Block block, String frontSuffix, String sideSuffix) {
        return new TextureMapping()
                .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, frontSuffix))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, sideSuffix))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom"));
    }

    public static void createLumberjackTable(BlockModelGenerators bMG) {
        bMG.createTrivialBlock(
                ModBlocks.LUMBERJACK_TABLE.get(),
                TexturedModel.createDefault(
                        (block) -> new TextureMapping()
                                .put(TextureSlot.DOWN, defaultLoc())
                                .put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_top"))
                                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_front"))
                                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_side"))
                                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_side"))
                                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_front"))
                                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_front")),
                        ModelTemplates.CUBE
                )
        );
    }

    public static void createStorageController(BlockModelGenerators bMG) {
        Block block = ModBlocks.STORAGE_CONTROLLER.get();
        TextureMapping textureMapping = storageControllerTextureMapping(block, "_front", "_side");
        TextureMapping textureMapping1 = storageControllerTextureMapping(block, "_front_on", "_side_on");
        Identifier identifier = ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.create(block, textureMapping, bMG.modelOutput);
        MultiVariant multiVariant = BlockModelGenerators.plainVariant(identifier);
        MultiVariant multiVariant1 = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.createWithSuffix(block, "_on", textureMapping1, bMG.modelOutput));
        bMG.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(StorageControllerBlock.POWERED, multiVariant1, multiVariant)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
                );
    }

    public static void createCardReader(BlockModelGenerators bMG) {
        Block block = ModBlocks.CARD_READER.get();
        TextureMapping textureMapping = cardReaderTextureMapping(block, "_front");
        TextureMapping textureMapping1 = cardReaderTextureMapping(block, "_front_on");
        Identifier identifier = ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.create(block, textureMapping, bMG.modelOutput);
        MultiVariant multivariant = BlockModelGenerators.plainVariant(identifier);
        MultiVariant multivariant1 = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.createWithSuffix(block, "_on", textureMapping1, bMG.modelOutput));
        bMG.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(CardReaderBlock.POWERED, multivariant1, multivariant)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
                );
    }

    public static void createAutoFarmer(BlockModelGenerators bMG) {
        Block block = ModBlocks.AUTO_FARMER.get();
        Map<FarmerMode, Identifier> offModels = new HashMap<>();
        Map<FarmerMode, Identifier> onModels = new HashMap<>();
        Map<FarmerMode, ItemModel.Unbaked> itemModels = new HashMap<>();

        for (FarmerMode mode : FarmerMode.values()) {

            String name = mode.getSerializedName();

            Identifier off = bMG.createSuffixedVariant(
                    block,
                    "_" + name,
                    ModelTemplates.CUBE_BOTTOM_TOP,
                    (identifier) -> autoFarmerTextureMapping(block, mode, false)
            );

            Identifier on = bMG.createSuffixedVariant(
                    block,
                    "_" + name + "_on",
                    ModelTemplates.CUBE_BOTTOM_TOP,
                    (identifier) -> autoFarmerTextureMapping(block, mode, true)
            );

            offModels.put(mode, off);
            onModels.put(mode, on);

            itemModels.put(
                    mode,
                    ItemModelUtils.plainModel(off)
            );
        }

        bMG.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.AUTO_FARMER.get())
                        .with(PropertyDispatch.initial(AutoFarmerBlock.FARMER_MODE, AutoFarmerBlock.POWERED)
                                .generate((color, powered) -> {
                                    Identifier model = powered
                                            ? onModels.get(color)
                                            : offModels.get(color);
                                    return BlockModelGenerators.plainVariant(model);
                                })
                        )
        );

        bMG.itemModelOutput
                .accept(
                        ModBlocks.AUTO_FARMER.asItem(),
                        ItemModelUtils.selectBlockItemProperty(
                                AutoFarmerBlock.FARMER_MODE,
                                ItemModelUtils.plainModel(offModels.get(FarmerMode.BREAKER)),
                                itemModels
                        )
                );
    }

    public static void createSignal(BlockModelGenerators bMG) {
        Map<ColorProperty, ItemModel.Unbaked> itemModels = new HashMap<>();
        Map<ColorProperty, Identifier> offModels = new HashMap<>();
        Map<ColorProperty, Identifier> onModels = new HashMap<>();

        for (ColorProperty color : ColorProperty.values()) {
            offModels.put(
                    color,
                    bMG.createSuffixedVariant(
                            ModBlocks.SIGNAL.get(),
                            "_" + color.getSerializedName(),
                            ModelTemplates.CUBE_ALL,
                            TextureMapping::cube
                    )
            );
            itemModels.put(
                    color,
                    ItemModelUtils.plainModel(offModels.get(color))
            );
            onModels.put(
                    color,
                    bMG.createSuffixedVariant(
                            ModBlocks.SIGNAL.get(),
                            "_" + color.getSerializedName() + "_on",
                            ModelTemplates.CUBE_ALL,
                            TextureMapping::cube
                    )
            );
        }

        bMG.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.SIGNAL.get())
                        .with(PropertyDispatch.initial(SignalBlock.COLOR, SignalBlock.LIT)
                                .generate((color, lit) -> {
                                    Identifier model = lit
                                            ? onModels.get(color)
                                            : offModels.get(color);
                                    return BlockModelGenerators.plainVariant(model);
                                })
                        )
        );

        bMG.itemModelOutput
                .accept(
                        ModBlocks.SIGNAL.asItem(),
                        ItemModelUtils.selectBlockItemProperty(
                                SignalBlock.COLOR,
                                ItemModelUtils.plainModel(offModels.get(ColorProperty.RED)),
                                itemModels
                        )
                );
    }

    public static void generateStandardCompassItem(ItemModelGenerators iMG, Item item) {
        List<RangeSelectItemModel.Entry> list = iMG.createCompassModels(item);
        iMG.itemModelOutput
                .accept(
                        item,
                        ItemModelUtils.conditional(
                                ItemModelUtils.hasComponent(DataComponents.LODESTONE_TRACKER),
                                ItemModelUtils.rangeSelect(new CompassAngle(true, CompassAngleState.CompassTarget.LODESTONE), 32.0F, list),
                                ItemModelUtils.rangeSelect(new CompassAngle(true, CompassAngleState.CompassTarget.SPAWN), 32.0F, list)
                        )
                );
    }

    private static Identifier defaultLoc() {
        return Identifier.withDefaultNamespace("block/oak_planks");
    }
}