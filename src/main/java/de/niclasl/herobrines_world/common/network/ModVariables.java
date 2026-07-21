package de.niclasl.herobrines_world.common.network;

import com.mojang.serialization.Codec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.api.leaderboard.LeaderboardEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

@EventBusSubscriber
public class ModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, HerobrinesWorld.MOD_ID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(PlayerVariables::new).build());

	@SubscribeEvent
	public static void onPlayerLoggedInSync(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncPlayer(player);

			ServerLevel level = player.level();

			PacketDistributor.sendToPlayer(
					player,
					new SavedDataSyncMessage(
							MapVariables.get(level)
					)
			);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawnSync(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncPlayer(player);
		}
	}

	@SubscribeEvent
	public static void onDimensionChangeSync(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncPlayer(player);
		}
	}

	private static void syncPlayer(ServerPlayer player) {
		PacketDistributor.sendToPlayer(
				player,
				new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES))
		);
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
		PlayerVariables clone = new PlayerVariables();

		clone.hide = original.hide;
		clone.hearts = original.hearts;
		clone.souls = original.souls;
		clone.soulLevel = original.soulLevel;
		clone.prestige = original.prestige;
		clone.threeHearts = original.threeHearts;
		clone.rank = original.rank;

		event.getEntity().setData(PLAYER_VARIABLES, clone);
	}

	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		if (!(event.getLevel() instanceof ServerLevel level)) return;

		MapVariables map = MapVariables.get(level);

		if (map.syncDirty) {
			PacketDistributor.sendToAllPlayers(new SavedDataSyncMessage(map));
			map.syncDirty = false;
		}
	}

	public static class MapVariables extends SavedData {
		public static final Codec<MapVariables> CODEC = CompoundTag.CODEC.xmap(
				tag -> {
					MapVariables instance = new MapVariables();

					if (tag instanceof CompoundTag compoundTag) {
						instance.read(compoundTag);
					}

					return instance;
				}, instance -> instance.save(new CompoundTag())
		);
		public static final SavedDataType<MapVariables> TYPE =
				new SavedDataType<>(
						Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "map_variables"),
						MapVariables::new, CODEC);
		boolean syncDirty = false;

		public boolean isHerobrineDead = false;
		public long seasonStart = 0;
		public long seasonEnd = 0;
		public long nextSeasonStart = 0;
		public long nextSeasonEnd = 0;
		public boolean seasonEndedHandled = false;
		public List<LeaderboardEntry> frozenLeaderboard = new ArrayList<>();

		public void read(CompoundTag nbt) {
			isHerobrineDead = nbt.getBooleanOr("HerobrineDead", false);
			seasonStart = nbt.getLongOr("seasonStart", 0);
			seasonEnd = nbt.getLongOr("seasonEnd", 0);
			nextSeasonStart = nbt.getLongOr("nextSeasonStart", 0);
			nextSeasonEnd = nbt.getLongOr("nextSeasonEnd", 0);
			seasonEndedHandled = nbt.getBooleanOr("seasonEndedHandled", false);
			frozenLeaderboard = new ArrayList<>();

			ListTag list = nbt.getListOrEmpty("frozenLeaderboard");

			for (int i = 0; i < list.size(); i++) {

				CompoundTag e = list.getCompoundOrEmpty(i);

				frozenLeaderboard.add(
						new LeaderboardEntry(
								UUID.fromString(e.getStringOr("uuid", "")),
								e.getStringOr("name", ""),
								e.getIntOr("value", 0),
								e.getIntOr("level", 0)
						)
				);
			}
		}

		public CompoundTag save(CompoundTag nbt) {
			nbt.putBoolean("HerobrineDead", isHerobrineDead);
			nbt.putLong("seasonStart", seasonStart);
			nbt.putLong("seasonEnd", seasonEnd);
			nbt.putLong("nextSeasonStart", nextSeasonStart);
			nbt.putLong("nextSeasonEnd", nextSeasonEnd);
			nbt.putBoolean("seasonEndedHandled", seasonEndedHandled);
			ListTag list = new ListTag();

			for (LeaderboardEntry e : frozenLeaderboard) {

				CompoundTag tag = new CompoundTag();

				tag.putString("uuid", e.player().toString());
				tag.putString("name", e.playerName());
				tag.putInt("value", e.value());
				tag.putInt("level", e.level());

				list.add(tag);
			}

			nbt.put("frozenLeaderboard", list);
			return nbt;
		}

		public void markSyncDirty() {
			this.setDirty();
			this.syncDirty = true;
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAccessor) {
				return Objects.requireNonNull(serverLevelAccessor.getLevel().getServer().getLevel(Level.OVERWORLD)).getDataStorage().computeIfAbsent(MapVariables.TYPE);
			} else {
				return clientSide;
			}
		}
	}

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

	public static class PlayerVariables implements ValueIOSerializable {
		boolean syncDirty = false;

		public boolean hide;

		public int hearts = 3;
		public int souls;
		public int soulLevel;
		public int prestige;

		public boolean threeHearts = true;

		public int rank;

		@Override
		public void serialize(ValueOutput output) {
			output.putBoolean("Hide", hide);

			output.putInt("Hearts", hearts);
			output.putInt("Souls", souls);
			output.putInt("SoulLevel", soulLevel);
			output.putInt("Prestige", prestige);

			output.putBoolean("ThreeHearts", threeHearts);
			output.putInt("rank", rank);
		}

		@Override
		public void deserialize(ValueInput input) {
			hide = input.getBooleanOr("Hide", false);

			hearts = input.getIntOr("Hearts", 0);
			souls = input.getIntOr("Souls", 0);
			soulLevel = input.getIntOr("SoulLevel", 0);
			prestige = input.getIntOr("Prestige", 0);

			threeHearts = input.getBooleanOr("ThreeHearts", true);
			rank = input.getIntOr("rank", 0);
		}

		public void markSyncDirty(ServerPlayer player) {
			syncDirty = true;
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
		}
	}

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
					context.player().getData(PLAYER_VARIABLES).deserialize(TagValueInput.create(ProblemReporter.DISCARDING, context.player().registryAccess(), output.buildResult()));
				});
			}
		}
	}
}