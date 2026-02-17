package de.niclasl.herobrines_world.init;

import org.lwjgl.glfw.GLFW;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import de.niclasl.herobrines_world.network.message.ResetTimerKeybind;
import de.niclasl.herobrines_world.network.message.ControllVariableTimer;
import de.niclasl.herobrines_world.network.message.AbilityControll;

@EventBusSubscriber(Dist.CLIENT)
public class ModKeyMappings {
	public static final KeyMapping CONTROLL_VARIABLE_TIMER = new KeyMapping("key.herobrines_world.controll_variable_timer", GLFW.GLFW_KEY_K, KeyMapping.Category.GAMEPLAY) {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ClientPacketDistributor.sendToServer(new ControllVariableTimer(0));
                assert Minecraft.getInstance().player != null;
                ControllVariableTimer.pressAction(Minecraft.getInstance().player, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping RESET_TIMER_KEYBIND = new KeyMapping("key.herobrines_world.reset_timer_keybind", GLFW.GLFW_KEY_R, KeyMapping.Category.GAMEPLAY) {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ClientPacketDistributor.sendToServer(new ResetTimerKeybind(0));
                assert Minecraft.getInstance().player != null;
                ResetTimerKeybind.pressAction(Minecraft.getInstance().player, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping ABILITY_CONTROLL = new KeyMapping("key.herobrines_world.ability_controll", GLFW.GLFW_KEY_I, KeyMapping.Category.GAMEPLAY) {
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
		event.register(CONTROLL_VARIABLE_TIMER);
		event.register(RESET_TIMER_KEYBIND);
		event.register(ABILITY_CONTROLL);
	}

	@EventBusSubscriber(Dist.CLIENT)
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(ClientTickEvent.Post event) {
			if (Minecraft.getInstance().screen == null) {
				CONTROLL_VARIABLE_TIMER.consumeClick();
				RESET_TIMER_KEYBIND.consumeClick();
				ABILITY_CONTROLL.consumeClick();
			}
		}
	}
}
