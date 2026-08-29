package de.niclasl.herobrines_world.common.util.variables;

import de.niclasl.herobrines_world.common.network.message.PlayerVariablesSyncMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

public class PlayerVariables implements ValueIOSerializable {
    boolean syncDirty = false;

    public boolean hide;

    public int hearts = 3;
    public int souls;
    public int soulLevel;
    public int prestige;

    public boolean threeHearts = true;

    public int rank;

    @Override
    public void serialize(ValueOutput output) {
        output.putBoolean("hide", hide);

        output.putInt("hearts", hearts);
        output.putInt("souls", souls);
        output.putInt("soulLevel", soulLevel);
        output.putInt("prestige", prestige);

        output.putBoolean("threeHearts", threeHearts);
        output.putInt("rank", rank);
    }

    @Override
    public void deserialize(ValueInput input) {
        hide = input.getBooleanOr("hide", false);

        hearts = input.getIntOr("hearts", 0);
        souls = input.getIntOr("souls", 0);
        soulLevel = input.getIntOr("soulLevel", 0);
        prestige = input.getIntOr("prestige", 0);

        threeHearts = input.getBooleanOr("threeHearts", true);
        rank = input.getIntOr("rank", 0);
    }

    public void markSyncDirty(ServerPlayer player) {
        syncDirty = true;
        PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(ModVariables.PLAYER_VARIABLES)));
    }
}