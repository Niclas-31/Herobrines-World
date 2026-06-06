package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.ClientHandler;
import de.niclasl.herobrines_world.common.leaderbaord.LeaderboardEntry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record SyncLeaderboardPacket(List<LeaderboardEntry> entries) implements CustomPacketPayload {
    public static final Type<SyncLeaderboardPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_leaderboard"));

    public static final StreamCodec<FriendlyByteBuf, SyncLeaderboardPacket> STREAM_CODEC = StreamCodec.of(
            SyncLeaderboardPacket::encode,
            SyncLeaderboardPacket::decode
    );

    private static void encode(FriendlyByteBuf buf, SyncLeaderboardPacket msg) {
        buf.writeInt(msg.entries.size());

        for (LeaderboardEntry entry : msg.entries) {
            buf.writeUUID(entry.uuid());
            buf.writeUtf(entry.name());
            buf.writeInt(entry.value());
        }
    }

    private static SyncLeaderboardPacket decode(FriendlyByteBuf buf) {
        int size = buf.readInt();

        List<LeaderboardEntry> entries = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            entries.add(new LeaderboardEntry(buf.readUUID(), buf.readUtf(), buf.readInt()));
        }

        return new SyncLeaderboardPacket(entries);
    }

    public static void handle(SyncLeaderboardPacket msg, IPayloadContext ctx) {
        if (ctx.flow().isClientbound()) {
            ctx.enqueueWork(() -> ClientHandler.handleOpenLeaderboard(msg));
        }
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}