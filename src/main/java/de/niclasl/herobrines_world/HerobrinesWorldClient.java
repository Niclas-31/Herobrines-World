package de.niclasl.herobrines_world;

import de.niclasl.herobrines_world.registries.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.registries.block.entity.renderer.BatteryChargerRenderer;
import de.niclasl.herobrines_world.registries.block.entity.renderer.DelayerRenderer;
import de.niclasl.herobrines_world.registries.block.entity.renderer.LogicGateBlockEntityRenderer;
import de.niclasl.herobrines_world.registries.screen.ModMenuTypes;
import de.niclasl.herobrines_world.registries.screen.custom.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = HerobrinesWorld.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = HerobrinesWorld.MODID, value = Dist.CLIENT)
public class HerobrinesWorldClient {
    public HerobrinesWorldClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.DELAYER.get(), DelayerRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LOGIC_GATE_BLOCK.get(), LogicGateBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BATTERY_CHARGER.get(), BatteryChargerRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.DELAYER.get(), DelayerScreen::new);
        event.register(ModMenuTypes.SIGNAL_COLOR_CHANGER.get(), SignalColorChangerScreen::new);
        event.register(ModMenuTypes.AUTO_FARMER.get(), AutoFarmerScreen::new);
        event.register(ModMenuTypes.BATTERY_CHARGER.get(), BatteryChargerScreen::new);
        event.register(ModMenuTypes.SMART_CHIP.get(), SmartChipScreen::new);
        event.register(ModMenuTypes.STORAGE_CONTROLLER.get(), StorageControllerScreen::new);
        event.register(ModMenuTypes.CARD_READER.get(), CardReaderScreen::new);
    }
}