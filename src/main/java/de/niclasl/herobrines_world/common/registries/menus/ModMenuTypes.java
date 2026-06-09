package de.niclasl.herobrines_world.common.registries.menus;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, HerobrinesWorld.MODID);

	public static final DeferredHolder<MenuType<?>, MenuType<SignalColorChangerMenu>> SIGNAL_COLOR_CHANGER = MENUS.register("signal_color_changer", () -> IMenuTypeExtension.create(SignalColorChangerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DelayerMenu>> DELAYER = MENUS.register("delayer", () -> IMenuTypeExtension.create(DelayerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<AutoFarmerMenu>> AUTO_FARMER = MENUS.register("auto_farmer", () -> IMenuTypeExtension.create(AutoFarmerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<BatteryChargerMenu>> BATTERY_CHARGER = MENUS.register("battery_charger", () -> IMenuTypeExtension.create(BatteryChargerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SmartChipMenu>> SMART_CHIP = MENUS.register("smart_chip", () -> IMenuTypeExtension.create(SmartChipMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<StorageControllerMenu>> STORAGE_CONTROLLER = MENUS.register("storage_controller", () -> IMenuTypeExtension.create(StorageControllerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CardReaderMenu>> CARD_READER = MENUS.register("card_reader", () -> IMenuTypeExtension.create(CardReaderMenu::new));

	public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
	}
}