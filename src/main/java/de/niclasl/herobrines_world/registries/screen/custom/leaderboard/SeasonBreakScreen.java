package de.niclasl.herobrines_world.registries.screen.custom.leaderboard;

import de.niclasl.herobrines_world.core.manager.SeasonManager;
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

        gui.drawCenteredString(
                font,
                "§6§lSEASON ENDED",
                centerX,
                40,
                0xFFFFFFFF
        );

        gui.drawCenteredString(
                font,
                "Next Season starts in:",
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

        if (remaining <= 0) {
            return "Starting...";
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