package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.screen.custom.slider.TimerSlider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class RedstoneTimerScreen extends AbstractContainerScreen<RedstoneTimerMenu> {

    private TimerSlider slider;

    public RedstoneTimerScreen(RedstoneTimerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {

        slider = new TimerSlider(
                leftPos + 20,
                topPos + 40,
                120,
                20,
                Component.literal("Interval"),
                menu.getBlockEntity()
        );

        addRenderableWidget(slider);
    }

    private void saveTimer() {

    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float v, int i, int i1) {
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }
}