package de.niclasl.herobrines_world.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import de.niclasl.herobrines_world.procedures.Hide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@EventBusSubscriber(Dist.CLIENT)
public class DateOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		Player entity = Minecraft.getInstance().player;
		String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
        if (Hide.execute(entity)) {
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, date, 0, 1, -3407872, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, time, 0, 12, -3407872, false);
		}
	}
}
