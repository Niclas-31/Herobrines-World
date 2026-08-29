package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.variables.ModVariables;
import de.niclasl.herobrines_world.common.util.variables.PlayerVariables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
    public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "player_variables_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> {
        TagValueOutput output = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
        message.data.serialize(output);
        buffer.writeNbt(output.buildResult());
    }, (RegistryFriendlyByteBuf buffer) -> {
        PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
        CompoundTag tag = buffer.readNbt();
        if (tag == null) tag = new CompoundTag();
        message.data.deserialize(TagValueInput.create(ProblemReporter.DISCARDING, buffer.registryAccess(), tag));
        return message;
    });

    @Override
    public @NotNull Type<PlayerVariablesSyncMessage> type() {
        return TYPE;
    }

    public static void handle(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
        if (message.data != null) {
            context.enqueueWork(() -> {
                TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, context.player().registryAccess());
                message.data.serialize(output);
                context.player().getData(ModVariables.PLAYER_VARIABLES).deserialize(TagValueInput.create(ProblemReporter.DISCARDING, context.player().registryAccess(), output.buildResult()));
            });
        }
    }
}