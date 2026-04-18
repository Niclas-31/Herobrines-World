package de.niclasl.herobrines_world.network;

import net.minecraft.server.level.ServerPlayer;

public final class PlayerState {

    public static int hearts(ServerPlayer player) {
        return player.getData(ModVariables.PLAYER_VARIABLES).Hearts;
    }

    public static void setHearts(ServerPlayer player, int value) {
        var data = player.getData(ModVariables.PLAYER_VARIABLES);
        data.Hearts = value;
        data.markSyncDirty();
    }

    public static void addHearts(ServerPlayer player, int value) {
        var data = player.getData(ModVariables.PLAYER_VARIABLES);
        data.Hearts += value;
        data.markSyncDirty();
    }
}