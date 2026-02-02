package de.niclasl.herobrines_world.client;

import de.niclasl.herobrines_world.client.gui.*;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(Dist.CLIENT)
public class ModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(ModMenus.TIMER.get(), TimerGui::new);
		event.register(ModMenus.TIME.get(), ClockGui::new);
		event.register(ModMenus.PIN_CODE.get(), PinCodeGui::new);
		event.register(ModMenus.ACCOUNT.get(), AccountGui::new);
		event.register(ModMenus.LOG_OUT.get(), LogOutGui::new);
		event.register(ModMenus.SIGNAL_COLOR_CHANGER.get(), SignalColorChangerGui::new);
		event.register(ModMenus.DELAYER.get(), DelayerScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}
