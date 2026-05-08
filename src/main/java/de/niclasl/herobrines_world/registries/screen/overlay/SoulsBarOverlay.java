package de.niclasl.herobrines_world.registries.screen.overlay;

import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.text.DecimalFormat;

@EventBusSubscriber(Dist.CLIENT)
public class SoulsBarOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
        int w = event.getGuiGraphics().guiWidth();
        int h = event.getGuiGraphics().guiHeight();

        Player entity = Minecraft.getInstance().player;
        event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("herobrines_world:textures/gui/sprites/hud/soul_bar/seelen_bar.png"), w - 59, h - 28, 0, 0, 13, 6, 13, 6);

        assert entity != null;
        int souls = entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Current;

        souls = Math.clamp(souls, 0, 100);

        int stage = (int) Math.ceil(souls / 10.0);

        if (stage > 0) {
            Identifier texture = Identifier.parse(
                    "herobrines_world:textures/gui/sprites/hud/soul_bar/seelen_bar_"
                            + ((stage - 1) * 10 + 1) + "-" + (stage * 10) + "_soul_points.png"
            );

            event.getGuiGraphics().blit(
                    RenderPipelines.GUI_TEXTURED,
                    texture,
                    w - 59, h - 28,
                    0, 0,
                    13, 6,
                    13, 6
            );
        }

        String level = new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Soul_Level);

        event.getGuiGraphics().drawString(Minecraft.getInstance().font, level, w - 53, h - 39, -16777063, false);
    }
}