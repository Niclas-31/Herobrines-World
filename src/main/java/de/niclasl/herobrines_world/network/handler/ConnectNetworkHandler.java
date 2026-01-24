package de.niclasl.herobrines_world.network.handler;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import de.niclasl.herobrines_world.network.data.ConnectNetworkPayload;
import de.niclasl.herobrines_world.network.data.WirelessSenderData;
import de.niclasl.herobrines_world.network.manager.WirelessNetworkManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@EventBusSubscriber
public class ConnectNetworkHandler {

    public static void handle(ConnectNetworkPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            Level level = player.level();

            if (!(level.getBlockEntity(msg.receiver()) instanceof WirelessReceiverBlockEntity receiver)) return;

            WirelessSenderData sender = WirelessNetworkManager.getSender(msg.sender());
            if (sender == null) return;

            if (sender.hasPassword() && !sender.passwordMatches(msg.password())) {
                receiver.setConnected(false);
                return;
            }

            receiver.setConnected(true);
        });
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        addNetworkMessage();
    }

    public static void addNetworkMessage() {
        HerobrinesWorld.addNetworkMessage(
                ConnectNetworkPayload.TYPE,
                ConnectNetworkPayload.STREAM_CODEC,
                ConnectNetworkHandler::handle
        );
    }
}