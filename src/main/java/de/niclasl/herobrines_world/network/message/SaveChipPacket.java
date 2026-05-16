package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.network.safety.AccessMode;
import de.niclasl.herobrines_world.network.transfer.TransferMode;
import de.niclasl.herobrines_world.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.registries.components.SmartChipData;
import de.niclasl.herobrines_world.registries.item.custom.SmartChip;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

@EventBusSubscriber
public record SaveChipPacket(TransferMode mode, int range, int speed, AccessMode accessLevel, UUID owner, int accessTier) implements CustomPacketPayload {

    public static final Type<SaveChipPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "save_transfer"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SaveChipPacket> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buf, SaveChipPacket message) -> {
                buf.writeInt(message.mode().ordinal());
                buf.writeInt(message.range());
                buf.writeInt(message.speed());
                buf.writeInt(message.accessLevel().ordinal());
                buf.writeUUID(message.owner());
                buf.writeInt(message.accessTier());
            },
            buf -> new SaveChipPacket(
                    TransferMode.values()[buf.readInt()],
                    buf.readInt(),
                    buf.readInt(),
                    AccessMode.values()[buf.readInt()],
                    buf.readUUID(),
                    buf.readInt()
            )
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SaveChipPacket packet, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;

        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            ItemStack stack = player.getMainHandItem();

            if (!(stack.getItem() instanceof SmartChip)) return;

            SmartChipData.Transfer oldTransfer =
                    stack.getOrDefault(ModDataComponents.TRANSFER.get(),
                            SmartChipData.Transfer.DEFAULT);

            UUID owner = packet.owner();

            if (owner == null) {
                return;
            }

            stack.set(ModDataComponents.TRANSFER.get(),
                    new SmartChipData.Transfer(
                            packet.range(),
                            oldTransfer.pos(),
                            oldTransfer.dim(),
                            packet.speed(),
                            packet.mode()
                    )
            );

            stack.set(ModDataComponents.ACCESS.get(),
                    new SmartChipData.Access(
                            packet.owner(),
                            packet.accessLevel(),
                            packet.accessTier()
                    )
            );
        });
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, SaveChipPacket::handle);
    }
}