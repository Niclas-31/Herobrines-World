package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.client.ClientHandler;
import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardEntry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record SyncLeaderboardPacket(List<LeaderboardEntry> entries) implements CustomPacketPayload {
    public static final Type<SyncLeaderboardPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "sync_leaderboard"));

    public static final StreamCodec<FriendlyByteBuf, SyncLeaderboardPacket> STREAM_CODEC = StreamCodec.of(
            SyncLeaderboardPacket::encode,
            SyncLeaderboardPacket::decode
    );

    private static void encode(FriendlyByteBuf buf, SyncLeaderboardPacket msg) {
        buf.writeInt(msg.entries.size());

        for (LeaderboardEntry entry : msg.entries) {
            buf.writeUUID(entry.player());
            buf.writeUtf(entry.playerName());
            buf.writeInt(entry.value());
        }
    }

    private static SyncLeaderboardPacket decode(FriendlyByteBuf buf) {
        int size = buf.readInt();

        List<LeaderboardEntry> entries = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            UUID player = buf.readUUID();
            String playerName = buf.readUtf();
            int value = buf.readInt();

            entries.add(new LeaderboardEntry(player, playerName, value));
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