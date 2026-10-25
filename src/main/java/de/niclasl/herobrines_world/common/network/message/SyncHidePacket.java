package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record SyncHidePacket() implements CustomPacketPayload {

	public static final Type<SyncHidePacket> TYPE =
			new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "sync_hide"));

	public static final StreamCodec<RegistryFriendlyByteBuf, SyncHidePacket> STREAM_CODEC = StreamCodec.of(
			SyncHidePacket::encode,
			SyncHidePacket::decode
	);

	private static void encode(RegistryFriendlyByteBuf buf, SyncHidePacket msg) {
	}

	private static SyncHidePacket decode(RegistryFriendlyByteBuf buf) {
		return new SyncHidePacket();
	}

	@Override
	public @NotNull Type<SyncHidePacket> type() {
		return TYPE;
	}
}