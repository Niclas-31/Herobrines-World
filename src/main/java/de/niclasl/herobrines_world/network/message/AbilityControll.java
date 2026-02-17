package de.niclasl.herobrines_world.network.message;

import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

import java.util.Objects;

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
            if (entity.getData(ModVariables.PLAYER_VARIABLES).AbilityActive) {
                ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);
                vars.AbilityActive = false;
                vars.markSyncDirty();
                
                if (!entity.getData(ModVariables.PLAYER_VARIABLES).AbilityActive) {
                    if (entity.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
                        Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20);
                    entity.removeEffect(MobEffects.REGENERATION);
                    entity.removeEffect(MobEffects.SATURATION);
                    entity.removeEffect(MobEffects.NIGHT_VISION);
                }
            } else {
                ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);
                vars.AbilityActive = true;
                vars.markSyncDirty();

                if (entity.getData(ModVariables.PLAYER_VARIABLES).AbilityActive) {
                    if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.HEROBRINE_HELMET.get()) {
                        if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.HEROBRINE_CHESTPLATE.get()) {
                            if (entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.HEROBRINE_LEGGINGS.get()) {
                                if (entity.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.HEROBRINE_BOOTS.get()) {
                                    if (entity.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
                                        Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(1000);
                                    if (!entity.level().isClientSide()) {
                                        entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, -1, 255, false, false));
                                        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, 255, false, false));
                                        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 255, false, false));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        HerobrinesWorld.addNetworkMessage(AbilityControll.TYPE, AbilityControll.STREAM_CODEC, AbilityControll::handleData);
    }
}
