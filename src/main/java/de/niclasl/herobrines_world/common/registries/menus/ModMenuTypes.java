package de.niclasl.herobrines_world.common.registries.menus;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, HerobrinesWorld.MOD_ID);

	public static final DeferredHolder<MenuType<?>, MenuType<SignalColorChangerMenu>> SIGNAL_COLOR_CHANGER =
			registerMenuType("signal_color_changer", SignalColorChangerMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<DelayerMenu>> DELAYER =
			registerMenuType("delayer", DelayerMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<AutoFarmerMenu>> AUTO_FARMER =
			registerMenuType("auto_farmer", AutoFarmerMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<BatteryChargerMenu>> BATTERY_CHARGER =
			registerMenuType("battery_charger", BatteryChargerMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<SmartChipMenu>> SMART_CHIP =
			registerMenuType("smart_chip", SmartChipMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<StorageControllerMenu>> STORAGE_CONTROLLER =
			registerMenuType("storage_controller", StorageControllerMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<CardReaderMenu>> CARD_READER =
			registerMenuType("card_reader", CardReaderMenu::new);

	private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
	                                                                                                           IContainerFactory<T> factory) {
		return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
	}

	public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
	}
}