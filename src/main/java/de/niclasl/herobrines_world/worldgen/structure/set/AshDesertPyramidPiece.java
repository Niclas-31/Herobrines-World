package de.niclasl.herobrines_world.worldgen.structure.set;

import de.niclasl.herobrines_world.registries.block.ModBlocks;
import de.niclasl.herobrines_world.util.ModLootTables;
import de.niclasl.herobrines_world.worldgen.structure.ModStructurePieceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import org.jspecify.annotations.NonNull;

public class AshDesertPyramidPiece extends ScatteredFeaturePiece {

    public static final int WIDTH = 21;
    public static final int DEPTH = 21;

    private final boolean[] hasPlacedChest = new boolean[4];

    public AshDesertPyramidPiece(RandomSource random, int x, int z) {
        super(ModStructurePieceType.ASH_DESERT_PYRAMID_PIECE.get(),
                x, 64, z,
                WIDTH, 15, DEPTH,
                getRandomHorizontalDirection(random));
    }

    public AshDesertPyramidPiece(CompoundTag tag) {
        super(ModStructurePieceType.ASH_DESERT_PYRAMID_PIECE.get(), tag);

        this.hasPlacedChest[0] = tag.getBooleanOr("hasPlacedChest0", false);
        this.hasPlacedChest[1] = tag.getBooleanOr("hasPlacedChest1", false);
        this.hasPlacedChest[2] = tag.getBooleanOr("hasPlacedChest2", false);
        this.hasPlacedChest[3] = tag.getBooleanOr("hasPlacedChest3", false);
    }

    @Override
    protected void addAdditionalSaveData(@NonNull StructurePieceSerializationContext context, @NonNull CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);

        tag.putBoolean("hasPlacedChest0", this.hasPlacedChest[0]);
        tag.putBoolean("hasPlacedChest1", this.hasPlacedChest[1]);
        tag.putBoolean("hasPlacedChest2", this.hasPlacedChest[2]);
        tag.putBoolean("hasPlacedChest3", this.hasPlacedChest[3]);
    }

    @Override
    public void postProcess(
            @NonNull WorldGenLevel level,
            @NonNull StructureManager manager,
            @NonNull ChunkGenerator generator,
            RandomSource random,
            @NonNull BoundingBox box,
            @NonNull ChunkPos chunkPos,
            @NonNull BlockPos blockPos
    ) {
        if (this.updateHeightPositionToLowestGroundHeight(level, -random.nextInt(3))) {
            this.generateBox(
                    level,
                    box,
                    0,
                    -4,
                    0,
                    this.width - 1,
                    0,
                    this.depth - 1,
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    false
            );

            for (int i = 1; i <= 9; i++) {
                this.generateBox(
                        level,
                        box,
                        i,
                        i,
                        i,
                        this.width - 1 - i,
                        i,
                        this.depth - 1 - i,
                        ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                        ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                        false
                );
                this.generateBox(
                        level,
                        box,
                        i + 1,
                        i,
                        i + 1,
                        this.width - 2 - i,
                        i,
                        this.depth - 2 - i,
                        Blocks.AIR.defaultBlockState(),
                        Blocks.AIR.defaultBlockState(),
                        false
                );
            }

            for (int k1 = 0; k1 < this.width; k1++) {
                for (int j = 0; j < this.depth; j++) {
                    this.fillColumnDown(level, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), k1, -5, j, box);
                }
            }

            BlockState blockstate1 = ModBlocks.BLUE_SANDSTONE_STAIRS.get().defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockstate2 = ModBlocks.BLUE_SANDSTONE_STAIRS.get().defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
            BlockState blockstate3 = ModBlocks.BLUE_SANDSTONE_STAIRS.get().defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
            BlockState blockstate = ModBlocks.BLUE_SANDSTONE_STAIRS.get().defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
            this.generateBox(level, box, 0, 0, 0, 4, 9, 4, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(level, box, 1, 10, 1, 3, 10, 3, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), false);
            this.placeBlock(level, blockstate1, 2, 10, 0, box);
            this.placeBlock(level, blockstate2, 2, 10, 4, box);
            this.placeBlock(level, blockstate3, 0, 10, 2, box);
            this.placeBlock(level, blockstate, 4, 10, 2, box);
            this.generateBox(
                    level, box, this.width - 5, 0, 0, this.width - 1, 9, 4, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), false
            );
            this.generateBox(
                    level,
                    box,
                    this.width - 4,
                    10,
                    1,
                    this.width - 2,
                    10,
                    3,
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.placeBlock(level, blockstate1, this.width - 3, 10, 0, box);
            this.placeBlock(level, blockstate2, this.width - 3, 10, 4, box);
            this.placeBlock(level, blockstate3, this.width - 5, 10, 2, box);
            this.placeBlock(level, blockstate, this.width - 1, 10, 2, box);
            this.generateBox(level, box, 8, 0, 0, 12, 4, 4, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(level, box, 9, 1, 0, 11, 3, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 9, 1, 1, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 9, 2, 1, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 9, 3, 1, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 10, 3, 1, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 11, 3, 1, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 11, 2, 1, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 11, 1, 1, box);
            this.generateBox(level, box, 4, 1, 1, 8, 3, 3, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(level, box, 4, 1, 2, 8, 2, 2, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(level, box, 12, 1, 1, 16, 3, 3, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(level, box, 12, 1, 2, 16, 2, 2, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(
                    level,
                    box,
                    5,
                    4,
                    5,
                    this.width - 6,
                    4,
                    this.depth - 6,
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.generateBox(level, box, 9, 4, 9, 11, 4, 11, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(level, box, 8, 1, 8, 8, 3, 8, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false);
            this.generateBox(
                    level, box, 12, 1, 8, 12, 3, 8, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false
            );
            this.generateBox(
                    level, box, 8, 1, 12, 8, 3, 12, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false
            );
            this.generateBox(
                    level, box, 12, 1, 12, 12, 3, 12, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false
            );
            this.generateBox(level, box, 1, 1, 5, 4, 4, 11, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), false);
            this.generateBox(
                    level,
                    box,
                    this.width - 5,
                    1,
                    5,
                    this.width - 2,
                    4,
                    11,
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.generateBox(level, box, 6, 7, 9, 6, 7, 11, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), false);
            this.generateBox(
                    level,
                    box,
                    this.width - 7,
                    7,
                    9,
                    this.width - 7,
                    7,
                    11,
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.generateBox(level, box, 5, 5, 9, 5, 7, 11, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false);
            this.generateBox(
                    level,
                    box,
                    this.width - 6,
                    5,
                    9,
                    this.width - 6,
                    7,
                    11,
                    ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 5, 5, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 5, 6, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 6, 6, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), this.width - 6, 5, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), this.width - 6, 6, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), this.width - 7, 6, 10, box);
            this.generateBox(level, box, 2, 4, 4, 2, 6, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(
                    level, box, this.width - 3, 4, 4, this.width - 3, 6, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false
            );
            this.placeBlock(level, blockstate1, 2, 4, 5, box);
            this.placeBlock(level, blockstate1, 2, 3, 4, box);
            this.placeBlock(level, blockstate1, this.width - 3, 4, 5, box);
            this.placeBlock(level, blockstate1, this.width - 3, 3, 4, box);
            this.generateBox(level, box, 1, 1, 3, 2, 2, 3, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), false);
            this.generateBox(
                    level,
                    box,
                    this.width - 3,
                    1,
                    3,
                    this.width - 2,
                    2,
                    3,
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.placeBlock(level, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), 1, 1, 2, box);
            this.placeBlock(level, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), this.width - 2, 1, 2, box);
            this.placeBlock(level, ModBlocks.BLUE_SANDSTONE_SLAB.get().defaultBlockState(), 1, 2, 2, box);
            this.placeBlock(level, ModBlocks.BLUE_SANDSTONE_SLAB.get().defaultBlockState(), this.width - 2, 2, 2, box);
            this.placeBlock(level, blockstate, 2, 1, 2, box);
            this.placeBlock(level, blockstate3, this.width - 3, 1, 2, box);
            this.generateBox(level, box, 4, 3, 5, 4, 3, 17, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), false);
            this.generateBox(
                    level,
                    box,
                    this.width - 5,
                    3,
                    5,
                    this.width - 5,
                    3,
                    17,
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.generateBox(level, box, 3, 1, 5, 4, 2, 16, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.generateBox(
                    level, box, this.width - 6, 1, 5, this.width - 5, 2, 16, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false
            );

            for (int l = 5; l <= 17; l += 2) {
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 4, 1, l, box);
                this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), 4, 2, l, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), this.width - 5, 1, l, box);
                this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), this.width - 5, 2, l, box);
            }

            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 7, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 8, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 0, 9, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 0, 9, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 8, 0, 10, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 12, 0, 10, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 7, 0, 10, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 13, 0, 10, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 0, 11, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 0, 11, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 12, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 13, box);
            this.placeBlock(level, Blocks.BLUE_TERRACOTTA.defaultBlockState(), 10, 0, 10, box);

            for (int l1 = 0; l1 <= this.width - 1; l1 += this.width - 1) {
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 2, 1, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 2, 2, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 2, 3, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 3, 1, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 3, 2, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 3, 3, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 4, 1, box);
                this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), l1, 4, 2, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 4, 3, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 5, 1, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 5, 2, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 5, 3, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 6, 1, box);
                this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), l1, 6, 2, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 6, 3, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 7, 1, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 7, 2, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), l1, 7, 3, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 8, 1, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 8, 2, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), l1, 8, 3, box);
            }

            for (int i2 = 2; i2 <= this.width - 3; i2 += this.width - 3 - 2) {
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 - 1, 2, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2, 2, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 + 1, 2, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 - 1, 3, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2, 3, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 + 1, 3, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2 - 1, 4, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), i2, 4, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2 + 1, 4, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 - 1, 5, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2, 5, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 + 1, 5, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2 - 1, 6, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), i2, 6, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2 + 1, 6, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2 - 1, 7, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2, 7, 0, box);
                this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), i2 + 1, 7, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 - 1, 8, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2, 8, 0, box);
                this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), i2 + 1, 8, 0, box);
            }

            this.generateBox(level, box, 8, 4, 0, 12, 6, 0, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 8, 6, 0, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 12, 6, 0, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 5, 0, box);
            this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), 10, 5, 0, box);
            this.placeBlock(level, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 5, 0, box);
            this.generateBox(
                    level, box, 8, -14, 8, 12, -11, 12, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false
            );
            this.generateBox(
                    level,
                    box,
                    8,
                    -10,
                    8,
                    12,
                    -10,
                    12,
                    ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(),
                    ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(),
                    false
            );
            this.generateBox(
                    level, box, 8, -9, 8, 12, -9, 12, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), false
            );
            this.generateBox(level, box, 8, -8, 8, 12, -1, 12, ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), ModBlocks.BLUE_SANDSTONE.get().defaultBlockState(), false);
            this.generateBox(level, box, 9, -11, 9, 11, -1, 11, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.placeBlock(level, Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), 10, -11, 10, box);
            this.generateBox(level, box, 9, -13, 9, 11, -13, 11, Blocks.TNT.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 8, -11, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 8, -10, 10, box);
            this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), 7, -10, 10, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 7, -11, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 12, -11, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 12, -10, 10, box);
            this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), 13, -10, 10, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 13, -11, 10, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 10, -11, 8, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 10, -10, 8, box);
            this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), 10, -10, 7, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 10, -11, 7, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 10, -11, 12, box);
            this.placeBlock(level, Blocks.AIR.defaultBlockState(), 10, -10, 12, box);
            this.placeBlock(level, ModBlocks.BLUE_CHISELED_SANDSTONE.get().defaultBlockState(), 10, -10, 13, box);
            this.placeBlock(level, ModBlocks.BLUE_CUT_SANDSTONE.get().defaultBlockState(), 10, -11, 13, box);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (!this.hasPlacedChest[direction.get2DDataValue()]) {
                    int i1 = direction.getStepX() * 2;
                    int j1 = direction.getStepZ() * 2;
                    this.hasPlacedChest[direction.get2DDataValue()] = this.createChest(
                            level, box, random, 10 + i1, -11, 10 + j1, ModLootTables.ASH_DESERT_PYRAMID
                    );
                }
            }
        }
    }
}