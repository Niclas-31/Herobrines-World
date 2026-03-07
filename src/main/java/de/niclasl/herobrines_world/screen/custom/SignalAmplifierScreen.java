package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.network.message.SignalAmplifierUpdate;
import de.niclasl.herobrines_world.screen.custom.slider.SignalStrengthSlider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class SignalAmplifierScreen extends AbstractContainerScreen<SignalAmplifierMenu> {

    private SignalStrengthSlider strengthSlider;

    public SignalAmplifierScreen(SignalAmplifierMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
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

        double startValue = menu.be.getSignalStrength() / 15.0;

        strengthSlider = new SignalStrengthSlider(x, y, 120, 20, startValue);

        addRenderableWidget(strengthSlider);

        int saveButtonWidth = 60;
        int saveButtonHeight = 20;
        int saveButtonX = this.leftPos + (this.imageWidth / 2) - (saveButtonWidth / 2);
        int saveButtonY = this.topPos + this.imageHeight - 25;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.herobrines_world.delayer.button.save"), b -> saveSignalStrength())
                .bounds(saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight).build());
    }

    private void saveSignalStrength() {
        int signalStrength = parseSlider(strengthSlider);

        ClientPacketDistributor.sendToServer(new SignalAmplifierUpdate(menu.pos, signalStrength));
        SignalAmplifierUpdate.handleAction(menu.player, menu.pos, signalStrength);
    }

    private int parseSlider(SignalStrengthSlider strengthSlider) {
        return strengthSlider.getSignalStrength();
    }
}