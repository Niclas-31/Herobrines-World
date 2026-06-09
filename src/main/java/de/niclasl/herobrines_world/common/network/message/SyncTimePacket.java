package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.blocks.entities.DelayerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SyncTimePacket(BlockPos pos, int ticks, int seconds, int minutes, int hours) implements CustomPacketPayload {

    public static final Type<SyncTimePacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_time"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTimePacket> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, SyncTimePacket msg) -> {
                buffer.writeBlockPos(msg.pos);
                buffer.writeInt(msg.ticks);
                buffer.writeInt(msg.seconds);
                buffer.writeInt(msg.minutes);
                buffer.writeInt(msg.hours);
            },
            buf -> new SyncTimePacket(
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

    public static void handle(SyncTimePacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            ServerLevel world = player.level();
            BlockEntity be = world.getBlockEntity(msg.pos);
            if (be instanceof DelayerBlockEntity delayer) {
                delayer.setTimes(msg.ticks, msg.seconds, msg.minutes, msg.hours);
                delayer.setChanged();

                world.sendBlockUpdated(msg.pos(), world.getBlockState(msg.pos()), world.getBlockState(msg.pos()), 3);
            }
        });
    }
}