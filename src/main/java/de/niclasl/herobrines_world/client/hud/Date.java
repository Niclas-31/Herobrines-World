package de.niclasl.herobrines_world.client.hud;

import de.niclasl.herobrines_world.common.util.variables.ModVariables;
import de.niclasl.herobrines_world.common.util.variables.PlayerVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@EventBusSubscriber(Dist.CLIENT)
public class Date {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		Player entity = Minecraft.getInstance().player;
		assert entity != null;

		String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);

		PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);

		boolean condition = vars.hide;

        if (condition) {
			event.getGuiGraphics().text(Minecraft.getInstance().font, date, 0, 1, -3407872, false);
			event.getGuiGraphics().text(Minecraft.getInstance().font, time, 0, 12, -3407872, false);
		}
	}
}