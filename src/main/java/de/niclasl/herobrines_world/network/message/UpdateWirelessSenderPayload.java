package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.entity.custom.WirelessSenderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record UpdateWirelessSenderPayload(
        BlockPos pos,
        String networkName,
        String password,
        int range
) implements CustomPacketPayload {

    public static final Type<UpdateWirelessSenderPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "update_wireless_sender"));

    public static final StreamCodec<FriendlyByteBuf, UpdateWirelessSenderPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, msg) -> {
                        buf.writeBlockPos(msg.pos());
                        buf.writeUtf(msg.networkName());
                        buf.writeUtf(msg.password());
                        buf.writeInt(msg.range());
                    },
                    buf -> new UpdateWirelessSenderPayload(
                            buf.readBlockPos(),
                            buf.readUtf(),
                            buf.readUtf(),
                            buf.readInt()
                    )
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateWirelessSenderPayload msg, IPayloadContext ctx) {
        if (ctx.flow() == PacketFlow.SERVERBOUND) {
            ctx.enqueueWork(() -> {
                if (ctx.player().level().getBlockEntity(msg.pos()) instanceof WirelessSenderBlockEntity be) {
                    handleAction(be, msg.networkName(), msg.password(), msg.range());
                }
            });
        }
    }

    public static void handleAction(WirelessSenderBlockEntity be, String networkName, String password, int range) {
        be.setNetwork(networkName, password, range);
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, UpdateWirelessSenderPayload::handle);
    }
}