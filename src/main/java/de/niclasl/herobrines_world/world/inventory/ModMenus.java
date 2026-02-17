package de.niclasl.herobrines_world.world.inventory;

import de.niclasl.herobrines_world.world.inventory.custom.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.HerobrinesWorld;

import java.util.Map;

public class ModMenus {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, HerobrinesWorld.MODID);

	public static final DeferredHolder<MenuType<?>, MenuType<Timer>> TIMER = MENUS.register("timer", () -> IMenuTypeExtension.create(Timer::new));
	public static final DeferredHolder<MenuType<?>, MenuType<Time>> TIME = MENUS.register("time", () -> IMenuTypeExtension.create(Time::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SignalColorChanger>> SIGNAL_COLOR_CHANGER = MENUS.register("signal_color_changer", () -> IMenuTypeExtension.create(SignalColorChanger::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DelayerMenu>> DELAYER = MENUS.register("delayer", () -> IMenuTypeExtension.create(DelayerMenu::new));

	public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
	}

	public interface MenuAccessor {
		Map<String, Object> getMenuState();
	}
}
