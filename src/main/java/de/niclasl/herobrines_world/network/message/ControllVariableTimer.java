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
public record ControllVariableTimer(int eventType) implements CustomPacketPayload {
    public static final Type<ControllVariableTimer> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "key_controll_variable_timer"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ControllVariableTimer> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, ControllVariableTimer message) -> buffer.writeInt(message.eventType), (RegistryFriendlyByteBuf buffer) -> new ControllVariableTimer(buffer.readInt()));

    @Override
    public @NotNull Type<ControllVariableTimer> type() {
        return TYPE;
    }

    public static void handleData(final ControllVariableTimer message, final IPayloadContext context) {
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
            if (entity.getData(ModVariables.PLAYER_VARIABLES).TimerActive) {
                {
                    ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
                    _vars.TimerActive = false;
                    _vars.markSyncDirty();
                }
                if (entity instanceof Player _player && !_player.level().isClientSide())
                    _player.displayClientMessage(Component.literal("Timer stopped"), true);
            } else {
                {
                    ModVariables.PlayerVariables _vars = entity.getData(ModVariables.PLAYER_VARIABLES);
                    _vars.TimerActive = true;
                    _vars.markSyncDirty();
                }
                if (entity instanceof Player _player && !_player.level().isClientSide())
                    _player.displayClientMessage(Component.literal("Timer continued"), true);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(ControllVariableTimer.TYPE, ControllVariableTimer.STREAM_CODEC, ControllVariableTimer::handleData);
    }
}
