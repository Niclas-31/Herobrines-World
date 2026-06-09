package de.niclasl.herobrines_world.common.network;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.message.*;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessage {

    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(HerobrinesWorld.MOD_ID);

        registrar.playToServer(
                SyncHidePacket.TYPE,
                SyncHidePacket.STREAM_CODEC,
                SyncHidePacket::handle
        );
        registrar.playToServer(
                SyncColorPacket.TYPE,
                SyncColorPacket.STREAM_CODEC,
                SyncColorPacket::handle
        );
        registrar.playToServer(
                SyncTimePacket.TYPE,
                SyncTimePacket.STREAM_CODEC,
                SyncTimePacket::handle
        );
        registrar.playToServer(
                SyncChipPacket.TYPE,
                SyncChipPacket.STREAM_CODEC,
                SyncChipPacket::handle
        );
        registrar.playToServer(
                RequestRewardsScreenPacket.TYPE,
                RequestRewardsScreenPacket.STREAM_CODEC,
                RequestRewardsScreenPacket::handle
        );
        registrar.playToServer(
                ClaimRewardsPacket.TYPE,
                ClaimRewardsPacket.STREAM_CODEC,
                ClaimRewardsPacket::handle
        );
        registrar.playToServer(
                SyncWaypointCompass.TYPE,
                SyncWaypointCompass.STREAM_CODEC,
                SyncWaypointCompass::handle
        );
        registrar.playToServer(
                RenameWaypointPacket.TYPE,
                RenameWaypointPacket.STREAM_CODEC,
                RenameWaypointPacket::handle
        );
        registrar.playToServer(
                DeleteWaypointPacket.TYPE,
                DeleteWaypointPacket.STREAM_CODEC,
                DeleteWaypointPacket::handle
        );
        registrar.playToServer(
                DeselectWaypointPacket.TYPE,
                DeselectWaypointPacket.STREAM_CODEC,
                DeselectWaypointPacket::handle
        );

        registrar.playToClient(
                SyncLeaderboardPacket.TYPE,
                SyncLeaderboardPacket.STREAM_CODEC,
                SyncLeaderboardPacket::handle
        );
        registrar.playToClient(
                OpenRewardScreenPacket.TYPE,
                OpenRewardScreenPacket.STREAM_CODEC,
                OpenRewardScreenPacket::handle
        );
        registrar.playToClient(
                OpenWaypointScreenPacket.TYPE,
                OpenWaypointScreenPacket.STREAM_CODEC,
                OpenWaypointScreenPacket::handle
        );
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
        registrar.playToClient(
                SyncClaimStatePacket.TYPE,
                SyncClaimStatePacket.STREAM_CODEC,
                SyncClaimStatePacket::handleSyncClaimState
        );
    }
}