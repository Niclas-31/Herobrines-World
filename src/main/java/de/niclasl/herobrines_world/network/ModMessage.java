package de.niclasl.herobrines_world.network;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.network.message.*;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessage {

    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(HerobrinesWorld.MODID);

        registrar.playToServer(SyncHidePacket.TYPE, SyncHidePacket.STREAM_CODEC, SyncHidePacket::handle);
        registrar.playToServer(SyncColorPacket.TYPE, SyncColorPacket.STREAM_CODEC, SyncColorPacket::handle);
        registrar.playToServer(SyncTimePacket.TYPE, SyncTimePacket.STREAM_CODEC, SyncTimePacket::handle);
        registrar.playToServer(SyncChipPacket.TYPE, SyncChipPacket.STREAM_CODEC, SyncChipPacket::handle);
        registrar.playToClient(SyncLeaderboardPacket.TYPE, SyncLeaderboardPacket.STREAM_CODEC, SyncLeaderboardPacket::handle);
        registrar.playToClient(
                ModVariables.SavedDataSyncMessage.TYPE,
                ModVariables.SavedDataSyncMessage.STREAM_CODEC,
                ModVariables.SavedDataSyncMessage::handle
        );
        registrar.playToClient(
                ModVariables.PlayerVariablesSyncMessage.TYPE,
                ModVariables.PlayerVariablesSyncMessage.STREAM_CODEC,
                ModVariables.PlayerVariablesSyncMessage::handle
        );
    }
}