package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.ModVariables;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SyncHidePacket(int buttonID) implements CustomPacketPayload {

	public static final Type<SyncHidePacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "sync_hide"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncHidePacket> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SyncHidePacket message) -> buffer.writeInt(message.buttonID), (RegistryFriendlyByteBuf buffer) -> new SyncHidePacket(buffer.readInt()));
	@Override
	public @NotNull Type<SyncHidePacket> type() {
		return TYPE;
	}

	public static void handle(final SyncHidePacket message, final IPayloadContext context) {
		context.enqueueWork(() -> {
			if (message.buttonID != 0) return;

			var player = context.player();

			var vars = player.getData(ModVariables.PLAYER_VARIABLES);

			vars.Hide = !vars.Hide;

			if (player instanceof ServerPlayer serverPlayer) {
				vars.markSyncDirty(serverPlayer);
			}
		});
	}
}