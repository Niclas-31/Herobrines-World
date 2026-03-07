package de.niclasl.herobrines_world.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LogicMode implements StringRepresentable {
    AND("and", "&&"),
    OR("or", "||"),
    NOT("not", "!"),
    XOR("xor", "^");

    private final String name;
    private final String symbol;

    LogicMode(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}