package de.niclasl.herobrines_world.screen;

import de.niclasl.herobrines_world.screen.custom.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.HerobrinesWorld;

import java.awt.*;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, HerobrinesWorld.MODID);

	public static final DeferredHolder<MenuType<?>, MenuType<TimerMenu>> TIMER = MENUS.register("timer", () -> IMenuTypeExtension.create(TimerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<TimeMenu>> TIME = MENUS.register("time", () -> IMenuTypeExtension.create(TimeMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SignalColorChangerMenu>> SIGNAL_COLOR_CHANGER = MENUS.register("signal_color_changer", () -> IMenuTypeExtension.create(SignalColorChangerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DelayerMenu>> DELAYER = MENUS.register("delayer", () -> IMenuTypeExtension.create(DelayerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SignalAmplifierMenu>> SIGNAL_AMPLIFIER = MENUS.register("signal_amplifier", () -> IMenuTypeExtension.create(SignalAmplifierMenu::new));
		public static final DeferredHolder<MenuType<?>, MenuType<RedstoneTimerMenu>> REDSTONE_TIMER = MENUS.register("redstone_timer", () -> IMenuTypeExtension.create(RedstoneTimerMenu::new));

	public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
	}
}
