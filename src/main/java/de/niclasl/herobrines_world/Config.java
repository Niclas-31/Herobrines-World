package de.niclasl.herobrines_world;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue THREE_HEARTS = BUILDER
            .define("threeHearts", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}