package de.niclasl.herobrines_world.network.data;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

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
}