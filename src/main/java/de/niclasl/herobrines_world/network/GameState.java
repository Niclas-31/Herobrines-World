package de.niclasl.herobrines_world.network;

import net.minecraft.world.level.Level;

public final class GameState {

    public static boolean threeHearts(Level level) {
        return ModVariables.MapVariables.get(level).ThreeHearts;
    }

    public static void setThreeHearts(Level level, boolean value) {
        var data = ModVariables.MapVariables.get(level);
        data.ThreeHearts = value;
        data.markSyncDirty();
    }
}