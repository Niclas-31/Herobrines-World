package de.niclasl.herobrines_world.network.data;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import de.niclasl.herobrines_world.network.manager.WirelessNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record ConnectNetworkPayload(
        BlockPos receiver,
        BlockPos sender,
        String password
) implements CustomPacketPayload {

    public static final Type<ConnectNetworkPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "connect"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConnectNetworkPayload> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, ConnectNetworkPayload msg) -> {
                buffer.writeBlockPos(msg.receiver);
                buffer.writeBlockPos(msg.sender);
                buffer.writeUtf(msg.password);
            }, (RegistryFriendlyByteBuf buffer) -> new ConnectNetworkPayload(
                    buffer.readBlockPos(),
                    buffer.readBlockPos(),
                    buffer.readUtf()
            )
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ConnectNetworkPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            Level level = player.level();

            if (!(level.getBlockEntity(msg.receiver()) instanceof WirelessReceiverBlockEntity receiver)) return;

            WirelessSenderData sender = WirelessNetworkManager.getSender(msg.sender());
            if (sender == null) return;

            if (sender.hasPassword() && !sender.passwordMatches(msg.password())) {
                receiver.setConnected(false, sender, msg.password());
                return;
            }

            receiver.setConnected(true, sender, msg.password());
        });
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(
                ConnectNetworkPayload.TYPE,
                ConnectNetworkPayload.STREAM_CODEC,
                ConnectNetworkPayload::handle
        );
    }
}