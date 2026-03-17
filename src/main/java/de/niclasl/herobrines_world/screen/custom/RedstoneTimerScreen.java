package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.network.message.RedstoneTimerUpdate;
import de.niclasl.herobrines_world.screen.custom.slider.TimerSlider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class RedstoneTimerScreen extends AbstractContainerScreen<RedstoneTimerMenu> {

    private TimerSlider slider;

    public RedstoneTimerScreen(RedstoneTimerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
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

    @Override
    protected void init() {
        super.init();

        int x = leftPos + 20;
        int y = topPos + 40;

        double startValue = menu.be.getInterval() / 200.0;

        slider = new TimerSlider(x, y, 120, 20, startValue);

        addRenderableWidget(slider);

        int saveButtonWidth = 60;
        int saveButtonHeight = 20;
        int saveButtonX = this.leftPos + (this.imageWidth / 2) - (saveButtonWidth / 2);
        int saveButtonY = this.topPos + this.imageHeight - 25;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.herobrines_world.delayer.button.save"), b -> saveInterval())
                .bounds(saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight).build());
    }

    private void saveInterval() {
        int interval = parseSlider(slider);

        ClientPacketDistributor.sendToServer(new RedstoneTimerUpdate(menu.pos, interval));
        RedstoneTimerUpdate.handleAction(menu.player, menu.pos, interval);
    }

    private int parseSlider(TimerSlider slider) {
        return slider.getInterval();
    }
}
