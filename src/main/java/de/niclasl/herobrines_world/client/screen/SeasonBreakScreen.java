package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.common.season.SeasonManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class SeasonBreakScreen extends Screen {

    public SeasonBreakScreen() {
        super(Component.literal("Next Season"));
    }

    @Override
    public void render(@NonNull GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui, mouseX, mouseY, partialTick);

        int centerX = width / 2;

        String season_ended = Component.translatable("gui.herobrines_world.season_break.season_ended").getString();
        String next_season_start = Component.translatable("gui.herobrines_world.season_break.next_season_start").getString();

        gui.drawCenteredString(
                font,
                season_ended,
                centerX,
                40,
                0xFFFFFFFF
        );

        gui.drawCenteredString(
                font,
                next_season_start,
                centerX,
                80,
                0xFFCCCCCC
        );

        gui.drawCenteredString(
                font,
                getRemainingTime(),
                centerX,
                100,
                0xFFFFAA00
        );

        super.render(gui, mouseX, mouseY, partialTick);
    }

    private String getRemainingTime() {
        Level level = Minecraft.getInstance().level;

        long nextSeasonStart = SeasonManager.getNextSeasonStart(level);
        long remaining = nextSeasonStart - System.currentTimeMillis();

        String starting = Component.translatable("gui.herobrines_world.season_break.starting").getString();

        if (remaining <= 0) {
            return starting;
        }

        long seconds = remaining / 1000;

        long days = seconds / 86400;
        seconds %= 86400;

        long hours = seconds / 3600;
        seconds %= 3600;

        long minutes = seconds / 60;
        seconds %= 60;

        return days + "d "
                + hours + "h "
                + minutes + "m "
                + seconds + "s";
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}