package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderbaord.season.cache.ClientCache;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record SyncClaimStatePacket(Set<UUID> claimed) implements CustomPacketPayload {

    public static final Type<SyncClaimStatePacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_claim_state"));

    public static final StreamCodec<FriendlyByteBuf, SyncClaimStatePacket> STREAM_CODEC =
            StreamCodec.of(SyncClaimStatePacket::encode, SyncClaimStatePacket::decode);

    private static void encode(FriendlyByteBuf buf, SyncClaimStatePacket msg) {

        buf.writeInt(msg.claimed.size());

        for (UUID uuid : msg.claimed) {
            buf.writeUUID(uuid);
        }
    }

    private static SyncClaimStatePacket decode(FriendlyByteBuf buf) {

        int size = buf.readInt();

        Set<UUID> claimed = new HashSet<>();

        for (int i = 0; i < size; i++) {
            claimed.add(buf.readUUID());
        }

        return new SyncClaimStatePacket(claimed);
    }

    public static void handleSyncClaimState(SyncClaimStatePacket packet, IPayloadContext ctx) {
        if (ctx.flow().isClientbound()) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();

                if (mc.level == null) return;

                ClientCache.setClaimed(packet.claimed());
            });
        }
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}