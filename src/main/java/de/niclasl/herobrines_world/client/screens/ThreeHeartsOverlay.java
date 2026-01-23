package de.niclasl.herobrines_world.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

import de.niclasl.herobrines_world.procedures.ThreeHeartsOverlayDisplay;
import de.niclasl.herobrines_world.procedures.Heart3;
import de.niclasl.herobrines_world.procedures.Heart2;
import de.niclasl.herobrines_world.procedures.Heart1;

@EventBusSubscriber(Dist.CLIENT)
public class ThreeHeartsOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Level world = null;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
		}
		if (ThreeHeartsOverlayDisplay.execute(world, entity)) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/container.png"), w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/container.png"), w / 2, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/container.png"), w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);

			if (Heart3.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/frozen_full.png"), w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);
			}
			if (Heart1.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/frozen_full.png"), w / 2, h - 47, 0, 0, 9, 9, 9, 9);
			}
			if (Heart2.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/frozen_full.png"), w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);
			}
		}
	}
}