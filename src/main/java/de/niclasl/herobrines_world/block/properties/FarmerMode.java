package de.niclasl.herobrines_world.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum FarmerMode implements StringRepresentable {
    BREAKER("breaker"),
    PLACER("placer"),
    BOTH("both");

    private final String name;

    FarmerMode(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}