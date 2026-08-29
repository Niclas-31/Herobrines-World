package de.niclasl.herobrines_world.common.util.variables;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.message.PlayerVariablesSyncMessage;
import de.niclasl.herobrines_world.common.network.message.SavedDataSyncMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

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
}