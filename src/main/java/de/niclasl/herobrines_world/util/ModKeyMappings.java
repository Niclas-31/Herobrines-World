package de.niclasl.herobrines_world.util;

import org.lwjgl.glfw.GLFW;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import de.niclasl.herobrines_world.network.message.AbilityControll;

@EventBusSubscriber(Dist.CLIENT)
public class ModKeyMappings {
	public static final KeyMapping ABILITY_CONTROLL = new KeyMapping("key.herobrines_world.ability_controll", GLFW.GLFW_KEY_K, KeyMapping.Category.GAMEPLAY) {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ClientPacketDistributor.sendToServer(new AbilityControll(0));
				assert Minecraft.getInstance().player != null;
				AbilityControll.pressAction(Minecraft.getInstance().player, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(ABILITY_CONTROLL);
	}

	@EventBusSubscriber(Dist.CLIENT)
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(ClientTickEvent.Post event) {
			if (Minecraft.getInstance().screen == null) {
				ABILITY_CONTROLL.consumeClick();
			}
		}
	}
}