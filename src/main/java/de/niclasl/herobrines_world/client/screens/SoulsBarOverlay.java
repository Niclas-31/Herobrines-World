package de.niclasl.herobrines_world.client.screens;

import de.niclasl.herobrines_world.network.ModVariables;
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

        assert entity != null;
        boolean one = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 1 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 10;
        boolean two = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 11 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 20;
        boolean three = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 21 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 30;
        boolean four = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 31 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 40;
        boolean five = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 41 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 50;
        boolean six = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 51 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 60;
        boolean seven = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 61 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 70;
        boolean eight = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 71 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 80;
        boolean nine = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 81 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 90;
        boolean ten = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current >= 91 && entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current <= 100;

        if (one) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_1-10_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (two) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_11-20_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (three) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_21-30_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (four) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_31-40_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (five) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_41-50_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (six) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_51-60_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (seven) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_61-70_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (eight) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_71-80_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (nine) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_81-90_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        if (ten) {
            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/seelen_bar_91-100_soul_points.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);
        }
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, SoulLevelText.execute(entity), w - 53, h - 39, -16777063, false);
    }
}
