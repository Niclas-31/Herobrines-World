package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.network.ClientHandler;
import de.niclasl.herobrines_world.network.message.entry.LeaderboardEntry;
import de.niclasl.herobrines_world.network.message.type.LeaderboardType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record SyncLeaderboardPacket(LeaderboardType leaderboardType, List<LeaderboardEntry> entries) implements CustomPacketPayload {
    public static final Type<SyncLeaderboardPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_leaderboard"));

    public static final StreamCodec<FriendlyByteBuf, SyncLeaderboardPacket> STREAM_CODEC = StreamCodec.of(
            SyncLeaderboardPacket::encode,
            SyncLeaderboardPacket::decode
    );

    private static void encode(FriendlyByteBuf buf, SyncLeaderboardPacket packet) {
        buf.writeEnum(packet.leaderboardType);

        buf.writeInt(packet.entries.size());

        for (LeaderboardEntry entry : packet.entries) {

            buf.writeUtf(entry.playerName());
            buf.writeInt(entry.value());
        }
    }

    private static SyncLeaderboardPacket decode(FriendlyByteBuf buf) {
        LeaderboardType type = buf.readEnum(LeaderboardType.class);

        int size = buf.readInt();

        List<LeaderboardEntry> entries = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            entries.add(
                    new LeaderboardEntry(
                            buf.readUtf(),
                            buf.readInt()
                    )
            );
        }

        return new SyncLeaderboardPacket(type, entries);
    }

    public static void handle(SyncLeaderboardPacket packet, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> ClientHandler.handle(packet));
        }
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}