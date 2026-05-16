package de.niclasl.herobrines_world.network.transfer;

import com.mojang.serialization.Codec;

public enum TransferMode {
    INSERT("Insert"),
    EXTRACT("Extract"),
    BALANCE("Balance");

    private final String displayName;

    TransferMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static final Codec<TransferMode> CODEC =
            Codec.STRING.xmap(
                    TransferMode::valueOf,
                    TransferMode::name
            );
}