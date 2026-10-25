package de.niclasl.herobrines_world.common.world.surface;

import com.google.common.collect.ImmutableList;
import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import de.niclasl.herobrines_world.common.world.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRuleData {

    public static SurfaceRules.RuleSource herobrine(HolderGetter<Biome> biomes) {
        return herobrine(biomes, false, true);
    }

  public static SurfaceRules.RuleSource herobrine(
          HolderGetter<Biome> biomes,
          boolean bedrockRoof,
          boolean bedrockFloor
  ) {
    SurfaceRules.ConditionSource aboveWater = SurfaceRules.waterBlockCheck(0, 0);

    SurfaceRules.RuleSource cursedForest =
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(
                                    Blocks.GRASS_BLOCK.defaultBlockState()
                            )
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.UNDER_FLOOR,
                            SurfaceRules.state(
                                    Blocks.DIRT.defaultBlockState()
                            )
                    )
            );

    SurfaceRules.RuleSource fireLand =
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(
                                    Blocks.NETHERRACK.defaultBlockState()
                            )
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.UNDER_FLOOR,
                            SurfaceRules.state(
                                    Blocks.NETHERRACK.defaultBlockState()
                            )
                    )
            );

    SurfaceRules.RuleSource frozenForest =
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(
                                    Blocks.GRASS_BLOCK.defaultBlockState()
                            )
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.UNDER_FLOOR,
                            SurfaceRules.state(
                                    Blocks.DIRT.defaultBlockState()
                            )
                    )
            );

    SurfaceRules.RuleSource ashDesert =
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(
                                    ModBlocks.ASH_BLOCK.get().defaultBlockState()
                            )
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.VERY_DEEP_UNDER_FLOOR,
                            SurfaceRules.state(
                                    ModBlocks.BLUE_SANDSTONE.get().defaultBlockState()
                            )
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.UNDER_FLOOR,
                            SurfaceRules.state(
                                    ModBlocks.ASH_BLOCK.get().defaultBlockState()
                            )
                    )
            );

    SurfaceRules.RuleSource customBiomeRules =
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.isBiome(
                                    biomes, ModBiomes.CURSED_FOREST
                            ),
                            cursedForest
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.isBiome(
                                    biomes, ModBiomes.FIRE_LAND
                            ),
                            fireLand
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.isBiome(
                                    biomes, ModBiomes.FROZEN_FOREST
                            ),
                            frozenForest
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.isBiome(
                                    biomes, ModBiomes.ASH_DESERT
                            ),
                            ashDesert
                    )
            );

    SurfaceRules.RuleSource defaultSurface =
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(
                                            aboveWater,
                                            SurfaceRules.state(
                                                    Blocks.GRASS_BLOCK.defaultBlockState()
                                            )
                                    ),
                                    SurfaceRules.state(
                                            Blocks.DIRT.defaultBlockState()
                                    )
                            )
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.UNDER_FLOOR,
                            SurfaceRules.state(
                                    Blocks.DIRT.defaultBlockState()
                            )
                    )
            );

    SurfaceRules.RuleSource surfaceRules =
            SurfaceRules.sequence(
                    customBiomeRules,
                    defaultSurface
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
                      SurfaceRules.state(
                              Blocks.BEDROCK.defaultBlockState()
                      )
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
                      SurfaceRules.state(
                              Blocks.BEDROCK.defaultBlockState()
                      )
              )
      );
    }

    builder.add(
            SurfaceRules.ifTrue(
                    SurfaceRules.abovePreliminarySurface(),
                    surfaceRules
            )
    );

    builder.add(
            SurfaceRules.ifTrue(
                    SurfaceRules.verticalGradient(
                            "deepslate",
                            VerticalAnchor.absolute(0),
                            VerticalAnchor.absolute(8)
                    ),
                    SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())
            )
    );

    return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
  }

  public static SurfaceRules.RuleSource underworld(HolderGetter<Biome> biomes) {
    SurfaceRules.ConditionSource aboveNetherLavaSurface = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0);
    SurfaceRules.ConditionSource closeToCeiling = SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0);
    SurfaceRules.ConditionSource hole = SurfaceRules.hole();

    SurfaceRules.RuleSource abyssalBlock = SurfaceRules.state(ModBlocks.ABYSSAL_BLOCK.get().defaultBlockState());
    SurfaceRules.RuleSource blackConcrete = SurfaceRules.state(Blocks.CONCRETE.black().defaultBlockState());
    SurfaceRules.RuleSource netherrack = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
    SurfaceRules.RuleSource lava = SurfaceRules.state(Blocks.LAVA.defaultBlockState());
    SurfaceRules.RuleSource bedrock = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());

    return SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), bedrock),
            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), bedrock),
            SurfaceRules.ifTrue(closeToCeiling, netherrack),
            SurfaceRules.ifTrue(
                    SurfaceRules.isBiome(biomes, ModBiomes.ABYSSAL_WASTES),
                    SurfaceRules.sequence(
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, abyssalBlock),
                            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, netherrack)
                    )
            ),
            SurfaceRules.ifTrue(
                    SurfaceRules.isBiome(biomes, ModBiomes.VOID_DEPTHS),
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            blackConcrete
                    )
            ),
            SurfaceRules.ifTrue(
                    SurfaceRules.isBiome(
                            biomes,
                            ModBiomes.MISTY_CHASMS
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            netherrack
                    )
            ),
            SurfaceRules.ifTrue(
                    SurfaceRules.ON_FLOOR,
                    SurfaceRules.ifTrue(
                            SurfaceRules.not(aboveNetherLavaSurface),
                            SurfaceRules.ifTrue(
                                    hole,
                                    lava
                            )
                    )
            ),
            netherrack
    );
  }
}