package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.entity.custom.SignalAmplifierBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
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
public record SignalAmplifierUpdate(BlockPos pos, int signalStrength) implements CustomPacketPayload {

    public static final Type<SignalAmplifierUpdate> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "signal_amplifier_update"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SignalAmplifierUpdate> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, SignalAmplifierUpdate message) -> {
                buffer.writeBlockPos(message.pos);
                buffer.writeInt(message.signalStrength);
            },
            buffer -> new SignalAmplifierUpdate(
                    buffer.readBlockPos(),
                    buffer.readInt()
            )
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SignalAmplifierUpdate message, IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> handleAction(context.player(), message.pos, message.signalStrength));
        }
    }

    public static void handleAction(Player player, BlockPos pos, int signalStrength) {
        Level world = player.level();
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof SignalAmplifierBlockEntity entity) {
            entity.setSignalStrange(signalStrength);
            entity.setChanged();
        }
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, SignalAmplifierUpdate::handle);
    }
}