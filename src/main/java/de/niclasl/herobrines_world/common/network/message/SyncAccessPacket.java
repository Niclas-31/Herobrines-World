package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.Access;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.items.custom.SmartChip;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record SyncAccessPacket(AccessMode accessMode, int level, UUID owner) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncAccessPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "sync_access"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncAccessPacket> STREAM_CODEC = StreamCodec.of(
            SyncAccessPacket::encode,
            SyncAccessPacket::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, SyncAccessPacket msg) {
        buf.writeIdentifier(msg.accessMode.id());
        buf.writeInt(msg.level());
        buf.writeBoolean(msg.owner != null);
        if (msg.owner != null) {
            buf.writeUUID(msg.owner());
        }
    }

    private static SyncAccessPacket decode(RegistryFriendlyByteBuf buf) {
        AccessMode accessMode = HWRegistries.ACCESS_MODES.get(buf.readIdentifier());
        int level = buf.readInt();
        UUID owner = null;
        if (buf.readBoolean()) {
            owner = buf.readUUID();
        }
        return new SyncAccessPacket(accessMode, level, owner);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncAccessPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof SmartChip)) return;

            UUID owner = msg.owner;

            if (owner == null) {
                owner = player.getUUID();
            }

            stack.set(ModDataComponents.ACCESS.get(),
                    new Access(
                            owner,
                            msg.level(),
                            msg.accessMode()
                    ));
        });
    }
}