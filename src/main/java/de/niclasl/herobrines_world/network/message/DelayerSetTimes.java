package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.entity.custom.DelayerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record DelayerSetTimes(BlockPos pos, int ticks, int seconds, int minutes, int hours) implements CustomPacketPayload {

    public static final Type<DelayerSetTimes> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "delayer_set_times"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DelayerSetTimes> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, DelayerSetTimes message) -> {
                buffer.writeBlockPos(message.pos);
                buffer.writeInt(message.ticks);
                buffer.writeInt(message.seconds);
                buffer.writeInt(message.minutes);
                buffer.writeInt(message.hours);
            },
            buffer -> new DelayerSetTimes(
                    buffer.readBlockPos(),
                    buffer.readInt(),
                    buffer.readInt(),
                    buffer.readInt(),
                    buffer.readInt()
            )
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DelayerSetTimes message, IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> {
                handleAction(context.player(), message.pos, message.ticks, message.seconds, message.minutes, message.hours);
            }).exceptionally(e -> {
                context.connection().disconnect(Component.literal(e.getMessage()));
                return null;
            });
        }
    }

    public static void handleAction(Player player, BlockPos pos, int ticks, int seconds, int minutes, int hours) {
        Level world = player.level();
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof DelayerEntity delayer) {
            delayer.setTimes(ticks, seconds, minutes, hours);
            delayer.setChanged();
        }
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, DelayerSetTimes::handle);
    }
}