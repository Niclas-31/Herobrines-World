package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.common.leaderboard.season.SeasonManager;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardAPIHolder;
import de.niclasl.herobrines_world_api.leaderboard.LeaderboardEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class SeasonBreakScreen extends Screen {

    private List<LeaderboardEntry> top3 = List.of();

    public SeasonBreakScreen() {
        super(Component.literal("Next Season"));
    }

    @Override
    protected void init() {
        super.init();

        loadTop3();
    }

    private void loadTop3() {

        var level = Minecraft.getInstance().level;
        if (level == null) return;

        top3 = LeaderboardAPIHolder.get()
                .getTop("default", 3);
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        int centerX = width / 2;

        String season_ended = Component.translatable("gui.herobrines_world.season_break.season_ended").getString();
        String next_season_start = Component.translatable("gui.herobrines_world.season_break.next_season_start").getString();
        String top_3 = Component.translatable("gui.herobrines_world.season_break.top_3").getString();

        graphics.centeredText(this.font, season_ended, centerX, 40, 0xFFFFFFFF);

        graphics.centeredText(this.font, next_season_start, centerX, 80, 0xFFCCCCCC);

        graphics.centeredText(this.font, getRemainingTime(), centerX, 100, 0xFFFFAA00);

        int startY = 130;

        graphics.centeredText(this.font, top_3, centerX, startY, 0xFFFFFF00);

        for (int i = 0; i < this.top3.size(); i++) {

            LeaderboardEntry entry = this.top3.get(i);

            String text = switch (i) {
                case 0 -> "🥇 ";
                case 1 -> "🥈 ";
                case 2 -> "🥉 ";
                default -> (i + 1) + ". ";
            } + entry.playerName() + " - " + entry.value();

            graphics.centeredText(
                    this.font,
                    text,
                    centerX,
                    startY + 15 + (i * 12),
                    0xFFFFFFFF
            );
        }
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