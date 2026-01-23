package de.niclasl.herobrines_world.network.message;

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

import de.niclasl.herobrines_world.procedures.HerobrineArmorAbilityControll;
import de.niclasl.herobrines_world.HerobrinesWorld;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record AbilityControll(int eventType) implements CustomPacketPayload {
	public static final Type<AbilityControll> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "key_ability_controll"));
	public static final StreamCodec<RegistryFriendlyByteBuf, AbilityControll> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, AbilityControll message) -> buffer.writeInt(message.eventType), (RegistryFriendlyByteBuf buffer) -> new AbilityControll(buffer.readInt()));

	@Override
	public @NotNull Type<AbilityControll> type() {
		return TYPE;
	}

	public static void handleData(final AbilityControll message, final IPayloadContext context) {
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

			HerobrineArmorAbilityControll.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		HerobrinesWorld.addNetworkMessage(AbilityControll.TYPE, AbilityControll.STREAM_CODEC, AbilityControll::handleData);
	}
}