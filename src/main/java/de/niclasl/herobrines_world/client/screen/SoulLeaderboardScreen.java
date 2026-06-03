package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.season.SeasonManager;
import de.niclasl.herobrines_world.common.util.math.SoulMath;
import de.niclasl.herobrines_world.common.network.message.RequestRewardsScreenPacket;
import de.niclasl.herobrines_world.common.leaderbaord.LeaderboardEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SoulLeaderboardScreen extends Screen {

    private final List<LeaderboardEntry> entries;

    private int scrollOffset = 0;

    private static final int ENTRY_HEIGHT = 18;
    private static final int VISIBLE_ENTRIES = 10;

    private final Identifier ICON_REWARDS = Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "textures/gui/sprites/icon/reward.png");

    public SoulLeaderboardScreen(List<LeaderboardEntry> entries) {
        super(Component.translatable("gui.herobrines_world.soul_leaderboard.title"));

        this.entries = new ArrayList<>(entries);

        this.entries.sort(Comparator.comparingInt(LeaderboardEntry::value).reversed());
    }

    @Override
    protected void init() {
        super.init();

        int x = this.width - 30;
        int y = 10;

        this.addRenderableWidget(
                new ImageButton(
                        x, y,
                        20, 20,
                        new WidgetSprites(ICON_REWARDS),
                        button -> openRewardsScreen()
                )
        );
    }

    @Override
    public void render(@NonNull GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui, mouseX, mouseY, partialTick);

        int centerX = width / 2;

        int tableWidth = 300;
        int startX = centerX - (tableWidth / 2);

        int titleY = 20;
        int headerY = 50;
        int entryStartY = 70;

        String season_ends = Component.translatable(
                "gui.herobrines_world.soul_leaderboard.season_ends_in",
                getRemainingTime()).getString();

        String rankString = Component.translatable("gui.herobrines_world.soul_leaderboard.rank").getString();
        String player = Component.translatable("gui.herobrines_world.soul_leaderboard.player").getString();
        String souls = Component.translatable("gui.herobrines_world.soul_leaderboard.souls").getString();
        String levels = Component.translatable("gui.herobrines_world.soul_leaderboard.levels").getString();

        gui.drawCenteredString(this.font, season_ends, centerX, titleY + 12, 0xFFFFFFFF);

        gui.fill(startX - 5, headerY - 4, startX + tableWidth, headerY + 12, 0x88000000);

        gui.drawString(this.font, rankString, startX, headerY, 0xFFCCCCCC);
        gui.drawString(this.font, player, startX + 60, headerY, 0xFFCCCCCC);
        gui.drawString(this.font, souls, startX + 180, headerY, 0xFFCCCCCC);
        gui.drawString(this.font, levels, startX + 240, headerY, 0xFFCCCCCC);

        gui.fill(startX, headerY + 14, startX + tableWidth - 5, headerY + 15, 0xFF444444);

        int startIndex = scrollOffset;
        int endIndex = Math.min(entries.size(), startIndex + VISIBLE_ENTRIES);

        for (int i = startIndex; i < endIndex; i++) {

            LeaderboardEntry entry = entries.get(i);

            int y = entryStartY + ((i - startIndex) * ENTRY_HEIGHT);

            int rank = i + 1;
            int level = SoulMath.getLevelFromXP(entry.value());

            int rankColor = switch (rank) {
                case 1 -> 0xFFFFD700;
                case 2 -> 0xFFC0C0C0;
                case 3 -> 0xFFCD7F32;
                default -> 0xFFFFFFFF;
            };

            gui.fill(startX - 5, y - 2, startX + tableWidth, y + 12, 0x33000000);

            gui.drawString(this.font, "#" + rank, startX, y, rankColor);
            gui.drawString(this.font, entry.playerName(), startX + 60, y, 0xFFFFFFFF);
            gui.drawString(this.font, String.valueOf(entry.value()), startX + 180, y, 0xFFFFFFFF);
            gui.drawString(this.font, String.valueOf(level), startX + 240, y, 0xFFFFFFFF);
        }

        super.render(gui, mouseX, mouseY, partialTick);
    }

    private void openRewardsScreen() {
        ClientPacketDistributor.sendToServer(new RequestRewardsScreenPacket());
    }

    private String getRemainingTime() {
        Level level = Minecraft.getInstance().level;

        long remaining = SeasonManager.getSeasonEnd(level) - System.currentTimeMillis();

        String ended = Component.translatable("gui.herobrines_world.soul_leaderboard.ended").getString();

        if (remaining <= 0) {
            return ended;
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
    public boolean mouseScrolled(double mouseX, double mouseY,
                                 double scrollX, double scrollY) {

        if (scrollY > 0) {
            scrollOffset--;
        } else if (scrollY < 0) {
            scrollOffset++;
        }

        scrollOffset = Math.clamp(scrollOffset, 0, Math.max(0, entries.size() - VISIBLE_ENTRIES));

        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}