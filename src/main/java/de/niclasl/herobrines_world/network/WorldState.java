package de.niclasl.herobrines_world.network;

import net.minecraft.world.level.Level;

public final class WorldState {

    public static boolean isHerobrineDead(Level level) {
        return ModVariables.WorldVariables.get(level).isHerobrineDead;
    }

    public static void setHerobrineDead(Level level, boolean value) {
        var data = ModVariables.WorldVariables.get(level);
        data.isHerobrineDead = value;
        data.markSyncDirty();
    }
}