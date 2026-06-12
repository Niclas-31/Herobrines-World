package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.Transfer;
import de.niclasl.herobrines_world.common.registries.items.custom.SmartChip;
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

public record SyncTransferChipPacket(TransferMode transferMode, int range, int speed) implements CustomPacketPayload {

    public static final Type<SyncTransferChipPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "sync_chip"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTransferChipPacket> STREAM_CODEC = StreamCodec.of(
            SyncTransferChipPacket::encode,
            SyncTransferChipPacket::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, SyncTransferChipPacket msg) {
        buf.writeIdentifier(msg.transferMode.id());
        buf.writeInt(msg.range());
        buf.writeInt(msg.speed());
    }

    private static SyncTransferChipPacket decode(RegistryFriendlyByteBuf buf) {
        TransferMode transferMode = HWRegistries.TRANSFER_MODES.get(buf.readIdentifier());
        int range = buf.readInt();
        int speed = buf.readInt();
        return new SyncTransferChipPacket(transferMode, range, speed);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncTransferChipPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof SmartChip)) return;

            Transfer oldTransfer =
                    stack.getOrDefault(ModDataComponents.TRANSFER.get(), Transfer.DEFAULT);

            stack.set(ModDataComponents.TRANSFER.get(),
                    new Transfer(
                            msg.range(),
                            oldTransfer.pos(),
                            oldTransfer.dim(),
                            msg.speed(),
                            msg.transferMode()
                    )
            );
        });
    }
}