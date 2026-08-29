package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.variables.MapVariables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SavedDataSyncMessage(MapVariables data) implements CustomPacketPayload {
    public static final Type<SavedDataSyncMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "saved_data_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buf, SavedDataSyncMessage msg) ->
                    buf.writeNbt(msg.data.save(new CompoundTag())), (RegistryFriendlyByteBuf buffer) -> {
                CompoundTag nbt = buffer.readNbt();
                MapVariables data = new MapVariables();
                if (nbt != null) {
                    data.read(nbt);
                }
                return new SavedDataSyncMessage(data);
            });

    @Override
    public @NotNull Type<SavedDataSyncMessage> type() {
        return TYPE;
    }

    public static void handle(final SavedDataSyncMessage message, final IPayloadContext context) {
        if (message.data != null) {
            context.enqueueWork(() -> MapVariables.clientSide.read(message.data.save(new CompoundTag())));
        }
    }
}