package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.block.entity.custom.RedstoneTimerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record RedstoneTimerUpdate(BlockPos pos, int interval) implements CustomPacketPayload {

    public static final Type<RedstoneTimerUpdate> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "redstone_timer_update"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RedstoneTimerUpdate> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, RedstoneTimerUpdate message) -> {
                buffer.writeBlockPos(message.pos);
                buffer.writeInt(message.interval);
            },
            buffer -> new RedstoneTimerUpdate(
                    buffer.readBlockPos(),
                    buffer.readInt()
            )
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RedstoneTimerUpdate message, IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> handleAction(context.player(), message.pos, message.interval));
        }
    }

    public static void handleAction(Player player, BlockPos pos, int interval) {
        Level world = player.level();
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof RedstoneTimerBlockEntity entity) {
            entity.setInterval(interval);
            entity.setChanged();
        }
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(TYPE, STREAM_CODEC, RedstoneTimerUpdate::handle);
    }
}