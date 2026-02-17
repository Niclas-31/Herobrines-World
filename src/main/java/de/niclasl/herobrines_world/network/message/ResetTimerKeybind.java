package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.network.ModVariables;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record ResetTimerKeybind(int eventType) implements CustomPacketPayload {
    public static final Type<ResetTimerKeybind> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "key_reset_timer_keybind"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ResetTimerKeybind> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, ResetTimerKeybind message) -> buffer.writeInt(message.eventType), (RegistryFriendlyByteBuf buffer) -> new ResetTimerKeybind(buffer.readInt()));

    @Override
    public @NotNull Type<ResetTimerKeybind> type() {
        return TYPE;
    }

    public static void handleData(final ResetTimerKeybind message, final IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> pressAction(context.player(), message.eventType)).exceptionally(e -> {
                context.connection().disconnect(Component.literal(e.getMessage()));
                return null;
            });
        }
    }

    public static void pressAction(Player entity, int type) {
        Level world = entity.level();
        if (!world.hasChunkAt(entity.blockPosition()))
            return;
        if (type == 0) {
            ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
            _vars.Ticks = 0;
            _vars.Second = 0;
            _vars.Minute = 0;
            _vars.Hour = 0;
            _vars.Day = 0;
            _vars.markSyncDirty();

            if (entity instanceof Player _player && !_player.level().isClientSide())
                _player.displayClientMessage(Component.literal("Timer reset"), true);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(ResetTimerKeybind.TYPE, ResetTimerKeybind.STREAM_CODEC, ResetTimerKeybind::handleData);
    }
}
