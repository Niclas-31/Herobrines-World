package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.core.manager.SeasonManager;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.network.message.entry.LeaderboardEntry;
import de.niclasl.herobrines_world.registries.screen.custom.leaderboard.SeasonBreakScreen;
import de.niclasl.herobrines_world.registries.screen.custom.leaderboard.SoulLeaderboardScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record SyncLeaderboardPacket(List<LeaderboardEntry> entries, boolean rewardsClaimed) implements CustomPacketPayload {
    public static final Type<SyncLeaderboardPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_leaderboard"));

    public static final StreamCodec<FriendlyByteBuf, SyncLeaderboardPacket> STREAM_CODEC = StreamCodec.of(
            SyncLeaderboardPacket::encode,
            SyncLeaderboardPacket::decode
    );

    private static void encode(FriendlyByteBuf buf, SyncLeaderboardPacket packet) {
        buf.writeBoolean(packet.rewardsClaimed);

        buf.writeInt(packet.entries.size());

        for (LeaderboardEntry entry : packet.entries) {
            buf.writeUtf(entry.playerName());
            buf.writeInt(entry.value());
        }
    }

    private static SyncLeaderboardPacket decode(FriendlyByteBuf buf) {
        boolean claimed = buf.readBoolean();

        int size = buf.readInt();

        List<LeaderboardEntry> entries = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            entries.add(new LeaderboardEntry(buf.readUtf(), buf.readInt()));
        }

        return new SyncLeaderboardPacket(entries, claimed);
    }

    public static void handle(SyncLeaderboardPacket packet, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();

                Level level = mc.level;

                if (level == null || mc.player == null) {
                    return;
                }

                ModVariables.WorldVariables world = ModVariables.WorldVariables.get(level);

                if (SeasonManager.isSeasonActive(level)) {
                    mc.setScreen(new SoulLeaderboardScreen(packet.entries()));
                    return;
                }

                if (!packet.rewardsClaimed()) {
                    mc.setScreen(new SoulLeaderboardScreen(world.frozenLeaderboard));
                } else {
                    mc.setScreen(new SeasonBreakScreen());
                }
            });
        }
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}