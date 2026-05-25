package de.niclasl.herobrines_world.common.network.safety;

import com.mojang.serialization.Codec;

public enum AccessMode {
    PUBLIC("Public"),
    PRIVATE("Private"),
    TRUSTED("Trusted"),
    OWNER_ONLY("OwnerOnly");

    private final String displayName;

    AccessMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static final Codec<AccessMode> CODEC =
            Codec.STRING.xmap(
                    AccessMode::valueOf,
                    AccessMode::name
            );
}