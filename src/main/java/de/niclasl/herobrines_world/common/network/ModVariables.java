package de.niclasl.herobrines_world.common.network;

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
							1,
							WorldVariables.get(level)
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

		WorldVariables world = WorldVariables.get(level);

		if (world._syncDirty) {
			PacketDistributor.sendToPlayersInDimension(
					level,
					new SavedDataSyncMessage(1, world)
			);
			world._syncDirty = false;
		}

		MapVariables map = MapVariables.get(level);

		if (map._syncDirty) {
			PacketDistributor.sendToAllPlayers(
					new SavedDataSyncMessage(0, map)
			);
			map._syncDirty = false;
		}
	}

	public static class WorldVariables extends SavedData {
		public static final SavedDataType<WorldVariables> TYPE = new SavedDataType<>("world_variables", ctx -> new WorldVariables(), ctx -> CompoundTag.CODEC.xmap(tag -> {
			WorldVariables instance = new WorldVariables();
            instance.read(tag);
			return instance;
		}, instance -> instance.save(new CompoundTag())));
		boolean _syncDirty = false;
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
			this._syncDirty = true;
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(WorldVariables.TYPE);
			}

			return clientSide;
		}
	}

	public static class MapVariables extends SavedData {
		public static final SavedDataType<MapVariables> TYPE = new SavedDataType<>("map_variables", ctx -> new MapVariables(), ctx -> CompoundTag.CODEC.xmap(tag -> {
			MapVariables instance = new MapVariables();
            instance.read(tag);
			return instance;
		}, instance -> instance.save(new CompoundTag())));
		boolean _syncDirty = false;

		public void read(CompoundTag nbt) {
		}

		public CompoundTag save(CompoundTag nbt) {
			return nbt;
		}

		public void markSyncDirty() {
			this.setDirty();
			this._syncDirty = true;
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

	public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
		public static final Type<SavedDataSyncMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "saved_data_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SavedDataSyncMessage message) -> {
			buffer.writeInt(message.dataType);
			if (message.data instanceof MapVariables mapVariables)
				buffer.writeNbt(mapVariables.save(new CompoundTag()));
			else if (message.data instanceof WorldVariables worldVariables)
				buffer.writeNbt(worldVariables.save(new CompoundTag()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			int dataType = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			SavedData data = null;
			if (nbt != null) {
				data = dataType == 0 ? new MapVariables() : new WorldVariables();
				if (data instanceof MapVariables mapVariables)
					mapVariables.read(nbt);
				else if (data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt);
			}
			return new SavedDataSyncMessage(dataType, data);
		});

		@Override
		public @NotNull Type<SavedDataSyncMessage> type() {
			return TYPE;
		}

		public static void handle(final SavedDataSyncMessage message, final IPayloadContext context) {
			if (message.data != null) {
				context.enqueueWork(() -> {
					if (message.dataType == 0)
						MapVariables.clientSide.read(((MapVariables) message.data).save(new CompoundTag()));
					else
						WorldVariables.clientSide.read(((WorldVariables) message.data).save(new CompoundTag()));
				});
			}
		}
	}

	public static class PlayerVariables implements ValueIOSerializable {
		boolean _syncDirty = false;

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
			_syncDirty = true;
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