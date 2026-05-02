package de.niclasl.herobrines_world.worldgen.dimension.surface;

import com.google.common.collect.ImmutableList;
import de.niclasl.herobrines_world.registries.block.ModBlocks;
import de.niclasl.herobrines_world.worldgen.biome.ModBiomes;
import de.niclasl.herobrines_world.worldgen.dimension.noise.ModNoises;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRuleData {
    private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource SAND = makeStateRule(ModBlocks.ASH_BLOCK.get());
    private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(ModBlocks.BLUE_SANDSTONE.get());
    private static final SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
    private static final SurfaceRules.RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static SurfaceRules.RuleSource herobrine() {
        return herobrine(false, true);
    }

    public static SurfaceRules.RuleSource herobrine(
            boolean bedrockRoof,
            boolean bedrockFloor
    ) {

        SurfaceRules.ConditionSource surfacerules$conditionsource7 = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource8 = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource9 = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.ConditionSource surfacerules$conditionsource10 = SurfaceRules.hole();
        SurfaceRules.ConditionSource surfacerules$conditionsource11 = SurfaceRules.steep();

        SurfaceRules.RuleSource cursedForest = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT)
        );

        SurfaceRules.RuleSource fireLand = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, NETHERRACK),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, NETHERRACK)
        );

        SurfaceRules.RuleSource frozenForest = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT)
        );

        SurfaceRules.RuleSource ashDesert = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SAND),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SAND),
                SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SANDSTONE)
        );

        SurfaceRules.RuleSource biomeRules = SurfaceRules.sequence(

                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.CURSED_FOREST), cursedForest),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.FIRE_LAND), fireLand),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.FROZEN_FOREST), frozenForest),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ASH_DESERT), ashDesert)
        );

        SurfaceRules.RuleSource waterRules = SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.ifTrue(
                        surfacerules$conditionsource7,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        surfacerules$conditionsource11,
                                        SurfaceRules.ifTrue(
                                                surfacerules$conditionsource10,
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(surfacerules$conditionsource8, AIR),
                                                        SurfaceRules.ifTrue(SurfaceRules.temperature(), ICE),
                                                        WATER
                                                )
                                        )
                                ),
                                STONE
                        )
                )
        );

        SurfaceRules.RuleSource undergroundRules = SurfaceRules.ifTrue(
                surfacerules$conditionsource9,
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT)
                )
        );

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();

        if (bedrockRoof) {
            builder.add(
                    SurfaceRules.ifTrue(
                            SurfaceRules.not(
                                    SurfaceRules.verticalGradient(
                                            "bedrock_roof",
                                            VerticalAnchor.belowTop(5),
                                            VerticalAnchor.top()
                                    )
                            ),
                            BEDROCK
                    )
            );
        }

        if (bedrockFloor) {
            builder.add(
                    SurfaceRules.ifTrue(
                            SurfaceRules.verticalGradient(
                                    "bedrock_floor",
                                    VerticalAnchor.bottom(),
                                    VerticalAnchor.aboveBottom(5)
                            ),
                            BEDROCK
                    )
            );
        }

        SurfaceRules.RuleSource surface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.sequence(
                                biomeRules,
                                waterRules,
                                undergroundRules
                        )
                )
        );

        builder.add(surface);

        builder.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient(
                                "deepslate",
                                VerticalAnchor.absolute(0),
                                VerticalAnchor.absolute(8)
                        ),
                        DEEPSLATE
                )
        );

        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    public static SurfaceRules.RuleSource underworld() {

        SurfaceRules.RuleSource abyssalBlock =
                SurfaceRules.state(ModBlocks.ABYSSAL_BLOCK.get().defaultBlockState());

        SurfaceRules.RuleSource blackConcrete =
                SurfaceRules.state(Blocks.BLACK_CONCRETE.defaultBlockState());

        SurfaceRules.RuleSource netherrack =
                SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());

        SurfaceRules.RuleSource lava =
                SurfaceRules.state(Blocks.LAVA.defaultBlockState());

        SurfaceRules.RuleSource bedrock =
                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor",
                                VerticalAnchor.bottom(),
                                VerticalAnchor.aboveBottom(5)),
                        bedrock
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.not(
                                SurfaceRules.verticalGradient("bedrock_roof",
                                        VerticalAnchor.belowTop(5),
                                        VerticalAnchor.top())
                        ),
                        bedrock
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.ABYSSAL_WASTES),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, abyssalBlock),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, netherrack)
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.VOID_DEPTHS),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, blackConcrete)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.MISTY_CHASMS),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, netherrack)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(ModNoises.CHAOS, 0.3),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, lava)
                )
        );
    }
}