package de.niclasl.herobrines_world.network;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
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
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

@EventBusSubscriber
public class ModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, HerobrinesWorld.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(PlayerVariables::new).build());

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
		HerobrinesWorld.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@SubscribeEvent
	public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerTickUpdateSyncPlayerVariables(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player && player.getData(PLAYER_VARIABLES)._syncDirty) {
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
			player.getData(PLAYER_VARIABLES)._syncDirty = false;
		}
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
		PlayerVariables clone = new PlayerVariables();
		clone.canFly = original.canFly;
		clone.TimerActive = original.TimerActive;
		clone.AbilityActive = original.AbilityActive;
		clone.Hide = original.Hide;
		clone.Hearts = original.Hearts;
		clone.Second = original.Second;
		clone.Minute = original.Minute;
		clone.Hour = original.Hour;
		clone.Day = original.Day;
		clone.enchantment_level = original.enchantment_level;
		clone.Soul_Current = original.Soul_Current;
		clone.PinUnlocked = original.PinUnlocked;
		clone.MyPinCode = original.MyPinCode;
		clone.HasSet = original.HasSet;
		clone.MyAccount = original.MyAccount;
		clone.X = original.X;
		clone.Y = original.Y;
		clone.Z = original.Z;
		clone.Soul_Level = original.Soul_Level;
		clone.Ticks = original.Ticks;
		clone.herobrineRelicOwner = original.herobrineRelicOwner;
		clone.ownedBossUUID = original.ownedBossUUID;
        event.getEntity().setData(PLAYER_VARIABLES, clone);
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			SavedData mapdata = MapVariables.get(event.getEntity().level());
			SavedData worlddata = WorldVariables.get(event.getEntity().level());
			if (mapdata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(0, mapdata));
			if (worlddata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			SavedData worlddata = WorldVariables.get(event.getEntity().level());
			if (worlddata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
		}
	}

	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel level) {
			WorldVariables worldVariables = WorldVariables.get(level);
			if (worldVariables._syncDirty) {
				PacketDistributor.sendToPlayersInDimension(level, new SavedDataSyncMessage(1, worldVariables));
				worldVariables._syncDirty = false;
			}
			MapVariables mapVariables = MapVariables.get(level);
			if (mapVariables._syncDirty) {
				PacketDistributor.sendToAllPlayers(new SavedDataSyncMessage(0, mapVariables));
				mapVariables._syncDirty = false;
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final SavedDataType<WorldVariables> TYPE = new SavedDataType<>("world_variables", ctx -> new WorldVariables(), ctx -> CompoundTag.CODEC.xmap(tag -> {
			WorldVariables instance = new WorldVariables();
			instance.read(tag, ctx.levelOrThrow().registryAccess());
			return instance;
		}, instance -> instance.save(new CompoundTag(), ctx.levelOrThrow().registryAccess())));
		boolean _syncDirty = false;

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		}

		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
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
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final SavedDataType<MapVariables> TYPE = new SavedDataType<>("map_variables", ctx -> new MapVariables(), ctx -> CompoundTag.CODEC.xmap(tag -> {
			MapVariables instance = new MapVariables();
			instance.read(tag, ctx.levelOrThrow().registryAccess());
			return instance;
		}, instance -> instance.save(new CompoundTag(), ctx.levelOrThrow().registryAccess())));
		boolean _syncDirty = false;
		public boolean ThreeHearts = false;
		public String modNamespace = "";
		public double LootBoxTimer = 0;
		public double LastSpawnLootBoxTimer = 0;

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			ThreeHearts = nbt.getBooleanOr("ThreeHearts", false);
			modNamespace = nbt.getStringOr("modNamespace", "");
			LootBoxTimer = nbt.getDoubleOr("LootBoxTimer", 0);
			LastSpawnLootBoxTimer = nbt.getDoubleOr("LastSpawnLootBoxTimer", 0);
		}

		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			nbt.putBoolean("ThreeHearts", ThreeHearts);
			nbt.putString("modNamespace", modNamespace);
			nbt.putDouble("LootBoxTimer", LootBoxTimer);
			nbt.putDouble("LastSpawnLootBoxTimer", LastSpawnLootBoxTimer);
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
		public static final Type<SavedDataSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "saved_data_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SavedDataSyncMessage message) -> {
			buffer.writeInt(message.dataType);
			if (message.data instanceof MapVariables mapVariables)
				buffer.writeNbt(mapVariables.save(new CompoundTag(), buffer.registryAccess()));
			else if (message.data instanceof WorldVariables worldVariables)
				buffer.writeNbt(worldVariables.save(new CompoundTag(), buffer.registryAccess()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			int dataType = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			SavedData data = null;
			if (nbt != null) {
				data = dataType == 0 ? new MapVariables() : new WorldVariables();
				if (data instanceof MapVariables mapVariables)
					mapVariables.read(nbt, buffer.registryAccess());
				else if (data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt, buffer.registryAccess());
			}
			return new SavedDataSyncMessage(dataType, data);
		});

		@Override
		public @NotNull Type<SavedDataSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final SavedDataSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					if (message.dataType == 0)
						MapVariables.clientSide.read(((MapVariables) message.data).save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
					else
						WorldVariables.clientSide.read(((WorldVariables) message.data).save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}

	public static class PlayerVariables implements ValueIOSerializable {
		boolean _syncDirty = false;
		public boolean canFly = false;
		public boolean TimerActive = false;
		public boolean AbilityActive = false;
		public boolean Hide = false;
		public double Hearts = 3.0;
		public double Second = 0.0;
		public double Minute = 0.0;
		public double Hour = 0.0;
		public double Day = 0.0;
		public double enchantment_level = 0.0;
		public int Soul_Current = 0;
		public boolean PinUnlocked = false;
		public String MyPinCode = "";
		public boolean HasSet = false;
		public String MyAccount = "";
		public double X = 0;
		public double Y = 0;
		public double Z = 0;
		public int Soul_Level = 0;
		public double Ticks = 0;
		public String herobrineRelicOwner = "";
		public String ownedBossUUID = "";

		public UUID getRelicOwner() {
			if (herobrineRelicOwner == null || herobrineRelicOwner.isEmpty())
				return null;
			return UUID.fromString(herobrineRelicOwner);
		}

		public UUID getOwnedBossUUID() {
			if (ownedBossUUID == null || ownedBossUUID.isEmpty()) {
				return null;
			}
			return UUID.fromString(ownedBossUUID);
		}

		@Override
		public void serialize(ValueOutput output) {
			output.putBoolean("canFly", canFly);
			output.putBoolean("TimerActive", TimerActive);
			output.putBoolean("AbilityActive", AbilityActive);
			output.putBoolean("Hide", Hide);
			output.putDouble("Hearts", Hearts);
			output.putDouble("Second", Second);
			output.putDouble("Minute", Minute);
			output.putDouble("Hour", Hour);
			output.putDouble("Day", Day);
			output.putDouble("enchantment_level", enchantment_level);
			output.putDouble("Soul_Current", Soul_Current);
			output.putBoolean("PinUnlocked", PinUnlocked);
			output.putString("MyPinCode", MyPinCode);
			output.putBoolean("HasSet", HasSet);
			output.putString("MyAccount", MyAccount);
			output.putDouble("X", X);
			output.putDouble("Y", Y);
			output.putDouble("Z", Z);
			output.putDouble("Soul_Level", Soul_Level);
			output.putDouble("Ticks", Ticks);
			output.putString("herobrineRelicOwner", herobrineRelicOwner);
			output.putString("ownedBossUUID", ownedBossUUID);
		}

		@Override
		public void deserialize(ValueInput input) {
			canFly = input.getBooleanOr("canFly", false);
			TimerActive = input.getBooleanOr("TimerActive", false);
			AbilityActive = input.getBooleanOr("AbilityActive", false);
			Hide = input.getBooleanOr("Hide", false);
			Hearts = input.getDoubleOr("Hearts", 0);
			Second = input.getDoubleOr("Second", 0);
			Minute = input.getDoubleOr("Minute", 0);
			Hour = input.getDoubleOr("Hour", 0);
			Day = input.getDoubleOr("Day", 0);
			enchantment_level = input.getDoubleOr("enchantment_level", 0);
			Soul_Current = input.getIntOr("Soul_Current", 0);
			PinUnlocked = input.getBooleanOr("PinUnlocked", false);
			MyPinCode = input.getStringOr("MyPinCode", "");
			HasSet = input.getBooleanOr("HasSet", false);
			MyAccount = input.getStringOr("MyAccount", "");
			X = input.getDoubleOr("X", 0);
			Y = input.getDoubleOr("Y", 0);
			Z = input.getDoubleOr("Z", 0);
			Soul_Level = input.getIntOr("Soul_Level", 0);
			Ticks = input.getDoubleOr("Ticks", 0);
			herobrineRelicOwner = input.getStringOr("herobrineRelicOwner", "");
			ownedBossUUID = input.getStringOr("ownedBossUUID", "");
		}

		public void markSyncDirty() {
			_syncDirty = true;
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "player_variables_sync"));
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

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, context.player().registryAccess());
					message.data.serialize(output);
					context.player().getData(PLAYER_VARIABLES).deserialize(TagValueInput.create(ProblemReporter.DISCARDING, context.player().registryAccess(), output.buildResult()));
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}
