package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;
import de.niclasl.herobrines_world.common.leaderbaord.RewardType;
import de.niclasl.herobrines_world.client.ClientHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record OpenRewardScreenPacket(List<RewardEntry> rewards) implements CustomPacketPayload {

    public static final Type<OpenRewardScreenPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "open_reward_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenRewardScreenPacket> STREAM_CODEC = StreamCodec.of(
            OpenRewardScreenPacket::encode,
            OpenRewardScreenPacket::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, OpenRewardScreenPacket msg) {
        buf.writeInt(msg.rewards.size());

        for (RewardEntry reward : msg.rewards) {
            buf.writeEnum(reward.type());
            buf.writeInt(reward.amount());
        }
    }

    private static OpenRewardScreenPacket decode(RegistryFriendlyByteBuf buf) {
        int size = buf.readInt();

        List<RewardEntry> rewards = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            rewards.add(new RewardEntry(buf.readEnum(RewardType.class), buf.readInt()));
        }

        return new OpenRewardScreenPacket(rewards);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenRewardScreenPacket msg, IPayloadContext ctx) {
        if (ctx.flow().isClientbound()) {
            ctx.enqueueWork(() -> ClientHandler.handleOpenReward(msg));
        }
    }
}