package de.niclasl.herobrines_world.client.overlay;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import de.niclasl.herobrines_world.common.network.ModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(Dist.CLIENT)
public class SoulsBarOverlay {
    private static final Identifier SOUL_BAR_BACKGROUND_SPRITE = Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "textures/gui/sprites/hud/soul_bar_background.png");
    private static final Identifier SOUL_BAR_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "textures/gui/sprites/hud/soul_bar_progress.png");

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        int w = event.getGuiGraphics().guiWidth();
        int h = event.getGuiGraphics().guiHeight();

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int souls = player.getData(ModVariables.PLAYER_VARIABLES).Souls;

        int level = SoulMath.getLevelFromXP(souls);
        float progress = SoulMath.getProgress(souls);

        int barWidth = 13;
        int filled = (int)(progress * barWidth);

        event.getGuiGraphics().blit(
                RenderPipelines.GUI_TEXTURED,
                SOUL_BAR_BACKGROUND_SPRITE,
                w - 59, h - 28,
                0, 0,
                barWidth, 6,
                barWidth, 6);

        if (filled > 0) {
            event.getGuiGraphics().blit(
                    RenderPipelines.GUI_TEXTURED,
                    SOUL_BAR_PROGRESS_SPRITE,
                    w - 59, h - 28,
                    0, 0,
                    filled, 6,
                    barWidth, 6
            );
        }

        event.getGuiGraphics().drawString(Minecraft.getInstance().font, String.valueOf(level), w - 53, h - 39, -16777063, false);
    }
}