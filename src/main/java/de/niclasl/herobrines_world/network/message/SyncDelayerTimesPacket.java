package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.registries.block.entity.custom.DelayerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record SyncDelayerTimesPacket(BlockPos pos, int ticks, int seconds, int minutes, int hours) implements CustomPacketPayload {

    public static final Type<SyncDelayerTimesPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_delayer_times"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncDelayerTimesPacket> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, SyncDelayerTimesPacket msg) -> {
                buffer.writeBlockPos(msg.pos);
                buffer.writeInt(msg.ticks);
                buffer.writeInt(msg.seconds);
                buffer.writeInt(msg.minutes);
                buffer.writeInt(msg.hours);
            },
            buf -> new SyncDelayerTimesPacket(
                    buf.readBlockPos(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt()
            )
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncDelayerTimesPacket msg, IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() ->
                    handleAction(context.player(), msg.pos, msg.ticks, msg.seconds, msg.minutes, msg.hours)
            );
        }
    }

    public static void handleAction(Player player, BlockPos pos, int ticks, int seconds, int minutes, int hours) {
        Level world = player.level();
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof DelayerBlockEntity delayer) {
            delayer.setTimes(ticks, seconds, minutes, hours);
            delayer.setChanged();
        }
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, SyncDelayerTimesPacket::handle);
    }
}