package de.niclasl.herobrines_world.registries.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ColorProperty implements StringRepresentable {
    RED("red"), ORANGE("orange"), YELLOW("yellow"), LIME("lime"),
    GREEN("green"), CYAN("cyan"), BLUE("blue"), MAGENTA("magenta"),
    PINK("pink"), GRAY("gray"), LIGHT_GRAY("light_gray"), LIGHT_BLUE("light_blue");

    private final String name;

    ColorProperty(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}