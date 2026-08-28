package de.niclasl.herobrines_world.common.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.database.PlayerData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

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

	public static void handle(SyncHidePacket msg, IPayloadContext ctx) {
		ctx.enqueueWork(() -> {
			var player = ctx.player();

            try {
                PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

				data.hide = !data.hide;

				HerobrinesWorld.DATABASE.savePlayerData(data);
            } catch (SQLException e) {
				HerobrinesWorld.LOGGER.error(
						"Failed to update hide state for {}",
						player.getUUID(),
						e
				);
            }
		});
	}
}