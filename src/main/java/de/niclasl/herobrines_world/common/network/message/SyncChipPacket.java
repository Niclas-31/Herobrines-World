package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.safety.AccessMode;
import de.niclasl.herobrines_world.common.network.transfer.TransferMode;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SmartChipData;
import de.niclasl.herobrines_world.common.registries.item.custom.SmartChip;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record SyncChipPacket(TransferMode mode, int range, int speed, AccessMode accessLevel, UUID owner, int accessTier) implements CustomPacketPayload {

    public static final Type<SyncChipPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_chip"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncChipPacket> STREAM_CODEC =
            StreamCodec.of(
                    (buf, msg) -> {
                        buf.writeInt(msg.mode().ordinal());
                        buf.writeInt(msg.range());
                        buf.writeInt(msg.speed());
                        buf.writeInt(msg.accessLevel().ordinal());
                        buf.writeBoolean(msg.owner() != null);
                        if (msg.owner() != null) {
                            buf.writeUUID(msg.owner());
                        }
                        buf.writeInt(msg.accessTier());
                    },

                    buf -> {
                        TransferMode mode = TransferMode.values()[buf.readInt()];
                        int range = buf.readInt();
                        int speed = buf.readInt();
                        AccessMode access = AccessMode.values()[buf.readInt()];
                        UUID owner = null;
                        if (buf.readBoolean()) {
                            owner = buf.readUUID();
                        }
                        int tier = buf.readInt();
                        return new SyncChipPacket(
                                mode,
                                range,
                                speed,
                                access,
                                owner,
                                tier
                        );
                    }
            );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncChipPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof SmartChip)) return;

            SmartChipData.Transfer oldTransfer =
                    stack.getOrDefault(ModDataComponents.TRANSFER.get(),
                            SmartChipData.Transfer.DEFAULT);

            UUID owner = msg.owner();

            if (owner == null) {
                owner = player.getUUID();
            }

            stack.set(ModDataComponents.TRANSFER.get(),
                    new SmartChipData.Transfer(
                            msg.range(),
                            oldTransfer.pos(),
                            oldTransfer.dim(),
                            msg.speed(),
                            msg.mode()
                    )
            );

            stack.set(ModDataComponents.ACCESS.get(),
                    new SmartChipData.Access(
                            owner,
                            msg.accessLevel(),
                            msg.accessTier()
                    )
            );
        });
    }
}