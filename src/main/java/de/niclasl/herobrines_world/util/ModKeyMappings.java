package de.niclasl.herobrines_world.util;

import de.niclasl.herobrines_world.network.message.SyncTimesPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(Dist.CLIENT)
public class ModKeyMappings {
	public static final KeyMapping TOGGLE_CLOCK = new KeyMapping("key.herobrines_world.toggle_clock", GLFW.GLFW_KEY_K, KeyMapping.Category.GAMEPLAY) {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ClientPacketDistributor.sendToServer(new SyncTimesPacket(0));
				assert Minecraft.getInstance().player != null;
				SyncTimesPacket.handleButtonAction(Minecraft.getInstance().player, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(TOGGLE_CLOCK);
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post event) {
		if (Minecraft.getInstance().screen == null) {
			TOGGLE_CLOCK.consumeClick();
		}
	}
}