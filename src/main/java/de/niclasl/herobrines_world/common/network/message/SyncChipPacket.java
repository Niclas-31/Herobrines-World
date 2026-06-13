package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SmartChipData;
import de.niclasl.herobrines_world.common.registries.items.custom.SmartChip;
import de.niclasl.herobrines_world_api.annotation.Experimental;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
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

@Experimental
public record SyncChipPacket(TransferMode transferMode, int range, int speed, AccessMode accessMode, int level, UUID owner) implements CustomPacketPayload {

    public static final Type<SyncChipPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "sync_chip"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncChipPacket> STREAM_CODEC = StreamCodec.of(
            SyncChipPacket::encode,
            SyncChipPacket::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, SyncChipPacket msg) {
        buf.writeIdentifier(msg.transferMode.id());
        buf.writeInt(msg.range());
        buf.writeInt(msg.speed());
        buf.writeIdentifier(msg.accessMode.id());
        buf.writeInt(msg.level());
        buf.writeBoolean(msg.owner != null);
        if (msg.owner != null) {
            buf.writeUUID(msg.owner());
        }
    }

    private static SyncChipPacket decode(RegistryFriendlyByteBuf buf) {
        TransferMode transferMode = HWRegistries.TRANSFER_MODES.get(buf.readIdentifier());
        int range = buf.readInt();
        int speed = buf.readInt();
        AccessMode accessMode = HWRegistries.ACCESS_MODES.get(buf.readIdentifier());
        int level = buf.readInt();
        UUID owner = null;
        if (buf.readBoolean()) {
            owner = buf.readUUID();
        }
        return new SyncChipPacket(transferMode, range, speed, accessMode, level, owner);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncChipPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof SmartChip)) return;

            SmartChipData.Transfer oldTransfer =
                    stack.getOrDefault(ModDataComponents.TRANSFER.get(), SmartChipData.Transfer.DEFAULT);

            UUID owner = msg.owner;

            if (owner == null) {
                owner = player.getUUID();
            }

            stack.set(ModDataComponents.TRANSFER.get(),
                    new SmartChipData.Transfer(
                            msg.range(),
                            oldTransfer.pos(),
                            oldTransfer.dim(),
                            msg.speed(),
                            msg.transferMode()
                    )
            );

            stack.set(ModDataComponents.ACCESS.get(),
                    new SmartChipData.Access(
                            owner,
                            msg.level(),
                            msg.accessMode()
                    )
            );
        });
    }
}