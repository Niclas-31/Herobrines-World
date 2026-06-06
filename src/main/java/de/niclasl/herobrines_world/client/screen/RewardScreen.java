package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;
import de.niclasl.herobrines_world.common.leaderbaord.season.SeasonManager;
import de.niclasl.herobrines_world.common.network.message.ClaimRewardsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class RewardScreen extends Screen {

    private final List<RewardEntry> rewards;

    private boolean claimed = false;

    private int scrollOffset = 0;

    private static final int ENTRY_HEIGHT = 18;
    private static final int VISIBLE_ENTRIES = 10;

    public RewardScreen(List<RewardEntry> rewards) {
        super(Component.translatable("gui.herobrines_world.rewards.title"));

        this.rewards = rewards;
    }

    @Override
    protected void init() {
        Level level = Minecraft.getInstance().level;

        int centerX = this.width / 2;

        Button claimAll = addRenderableWidget(
                Button.builder(
                        Component.translatable("gui.herobrines_world.rewards.claim_all"),
                                b -> claimAll()
                        )
                        .pos(centerX + 155, this.height - 40)
                        .size(60, 20)
                        .build()
        );

        claimAll.active = !SeasonManager.isSeasonActive(level);
    }

    @Override
    public void render(@NonNull GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui, mouseX, mouseY, partialTick);

        int centerX = width / 2;

        int tableWidth = 300;
        int startX = centerX - (tableWidth / 2);

        int titleY = 20;
        int entryStartY = 70;

        gui.drawCenteredString(this.font, this.title, centerX, titleY, 0xFFFFFF);

        int startIndex = scrollOffset;
        int endIndex = Math.min(rewards.size(), startIndex + VISIBLE_ENTRIES);

        for (int i = startIndex; i < endIndex; i++) {

            RewardEntry r = rewards.get(i);

            int y = entryStartY + ((i - startIndex) * ENTRY_HEIGHT);

            gui.fill(startX - 5, y - 2, startX + tableWidth, y + 12, 0x33000000);

            gui.drawString(this.font,
                    r.type().name() + " x" + r.amount(),
                    startX,
                    y,
                    0xFF00FFAA
            );
        }

        super.render(gui, mouseX, mouseY, partialTick);
    }

    private void claimAll() {

        if (claimed) return;

        Minecraft mc = Minecraft.getInstance();

        ClientPacketDistributor.sendToServer(new ClaimRewardsPacket());

        claimed = true;

        mc.setScreen(new SeasonBreakScreen());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY > 0) {
            scrollOffset--;
        } else if (scrollY < 0) {
            scrollOffset++;
        }

        scrollOffset = Math.clamp(scrollOffset, 0, Math.max(0, rewards.size() - VISIBLE_ENTRIES));

        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}