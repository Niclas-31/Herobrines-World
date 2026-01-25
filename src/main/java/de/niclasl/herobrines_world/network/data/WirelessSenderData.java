package de.niclasl.herobrines_world.network.data;

import net.minecraft.core.BlockPos;

public record WirelessSenderData(
        BlockPos pos,
        String name,
        String password,
        int range
) {
    public boolean hasPassword() {
        return password != null && !password.isEmpty();
    }

    public boolean passwordMatches(String input) {
        return password.equals(input);
    }

    public String getName() {
        return name;
    }
}
