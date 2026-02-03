package de.niclasl.herobrines_world.client.screens;

import de.niclasl.herobrines_world.procedures.*;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

@EventBusSubscriber(Dist.CLIENT)
public class SoulsBarOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();

		Player entity = Minecraft.getInstance().player;
        event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);

        if (SoulBarCondition1.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_1-10_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition2.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_11-20_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition3.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_21-30_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition4.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_31-40_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition5.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_41-50_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition6.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_51-60_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition7.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_61-70_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition8.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_71-80_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition9.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_81-90_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (SoulBarCondition10.execute(entity)) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_91-100_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, SoulAdvertisement.execute(entity), w - 53, h - 39, -16777063, false);
    }
}
