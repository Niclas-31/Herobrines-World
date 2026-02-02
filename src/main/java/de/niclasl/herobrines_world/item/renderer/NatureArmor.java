package de.niclasl.herobrines_world.item.renderer;

import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.resources.model.EquipmentClientInfo;

import de.niclasl.herobrines_world.item.ModItems;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(Dist.CLIENT)
public class NatureArmor {
	@SubscribeEvent
	public static void registerItemExtensions(RegisterClientExtensionsEvent event) {
		event.registerItem(new IClientItemExtensions() {
			@Override
			public ResourceLocation getArmorTexture(@NotNull ItemStack stack, EquipmentClientInfo.@NotNull LayerType type, EquipmentClientInfo.@NotNull Layer layer, @NotNull ResourceLocation _default) {
				return ResourceLocation.parse("herobrines_world:textures/models/armor/nature_layer_1.png");
			}
		}, ModItems.NATURE_HELMET.get());
		event.registerItem(new IClientItemExtensions() {
			@Override
			public ResourceLocation getArmorTexture(@NotNull ItemStack stack, EquipmentClientInfo.@NotNull LayerType type, EquipmentClientInfo.@NotNull Layer layer, @NotNull ResourceLocation _default) {
				return ResourceLocation.parse("herobrines_world:textures/models/armor/nature_layer_1.png");
			}
		}, ModItems.NATURE_CHESTPLATE.get());
		event.registerItem(new IClientItemExtensions() {
			@Override
			public ResourceLocation getArmorTexture(@NotNull ItemStack stack, EquipmentClientInfo.@NotNull LayerType type, EquipmentClientInfo.@NotNull Layer layer, @NotNull ResourceLocation _default) {
				return ResourceLocation.parse("herobrines_world:textures/models/armor/nature_layer_2.png");
			}
		}, ModItems.NATURE_LEGGINGS.get());
		event.registerItem(new IClientItemExtensions() {
			@Override
			public ResourceLocation getArmorTexture(@NotNull ItemStack stack, EquipmentClientInfo.@NotNull LayerType type, EquipmentClientInfo.@NotNull Layer layer, @NotNull ResourceLocation _default) {
				return ResourceLocation.parse("herobrines_world:textures/models/armor/nature_layer_1.png");
			}
		}, ModItems.NATURE_BOOTS.get());
	}

}
