package de.niclasl.herobrines_world.world.inventory;

import de.niclasl.herobrines_world.client.ModScreens;
import de.niclasl.herobrines_world.world.inventory.custom.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import de.niclasl.herobrines_world.network.message.MenuStateUpdate;
import de.niclasl.herobrines_world.HerobrinesWorld;

import java.awt.*;
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

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdate(elementType, name, elementState));
			} else if (player.level().isClientSide()) {
				if (Minecraft.getInstance().screen instanceof ModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				ClientPacketDistributor.sendToServer(new MenuStateUpdate(elementType, name, elementState));
			}
		}

		@SuppressWarnings("unchecked")
		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}
