package de.niclasl.herobrines_world.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import de.niclasl.herobrines_world.procedures.TimerText;
import de.niclasl.herobrines_world.procedures.TimerOverlayInGame;

@EventBusSubscriber(Dist.CLIENT)
public class TimerOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Player entity = Minecraft.getInstance().player;
		if (TimerOverlayInGame.execute(entity)) {
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					TimerText.execute(entity), w / 2 - 19, h - 63, -65536, false);
		}
	}
}