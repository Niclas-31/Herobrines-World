package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.network.message.entry.RewardEntry;
import de.niclasl.herobrines_world.registries.screen.custom.leaderboard.RewardScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
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

    public static final StreamCodec<FriendlyByteBuf, OpenRewardScreenPacket> STREAM_CODEC = StreamCodec.of(
            OpenRewardScreenPacket::encode,
            OpenRewardScreenPacket::decode
    );

    private static void encode(FriendlyByteBuf buf, OpenRewardScreenPacket packet) {
        buf.writeInt(packet.rewards.size());

        for (RewardEntry reward : packet.rewards) {
            buf.writeUtf(reward.name());
            buf.writeInt(reward.amount());
        }
    }

    private static OpenRewardScreenPacket decode(FriendlyByteBuf buf) {
        int size = buf.readInt();

        List<RewardEntry> rewards = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            rewards.add(new RewardEntry(buf.readUtf(), buf.readInt()));
        }

        return new OpenRewardScreenPacket(rewards);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenRewardScreenPacket packet, IPayloadContext ctx) {
        if (ctx.flow().isClientbound()) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();

                mc.setScreen(new RewardScreen(packet.rewards()));
            });
        }
    }
}