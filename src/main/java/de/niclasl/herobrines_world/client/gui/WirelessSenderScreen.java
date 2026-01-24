package de.niclasl.herobrines_world.client.gui;

import de.niclasl.herobrines_world.block.entity.custom.WirelessSenderBlockEntity;
import de.niclasl.herobrines_world.network.message.UpdateWirelessSenderPayload;
import de.niclasl.herobrines_world.world.inventory.custom.WirelessSenderMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class WirelessSenderScreen extends AbstractContainerScreen<WirelessSenderMenu> {

    private EditBox nameField;
    private EditBox passwordField;
    private EditBox rangeField;

    public WirelessSenderScreen(WirelessSenderMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        int x = leftPos + 10;
        int y = topPos + 20;

        WirelessSenderBlockEntity be = menu.getBlockEntity();

        nameField = new EditBox(this.font, x, y, 120, 20, Component.literal("Network Name"));
        nameField.setValue(be != null ? be.getNetworkName() : "");
        addRenderableWidget(nameField);

        y += 25;
        passwordField = new EditBox(this.font, x, y, 120, 20, Component.literal("Password"));
        passwordField.setValue(be != null ? be.getPassword() : "");
        addRenderableWidget(passwordField);

        y += 25;
        rangeField = new EditBox(this.font, x, y, 50, 20, Component.literal("Range"));
        rangeField.setValue(be != null ? String.valueOf(be.getRange()) : "16");
        addRenderableWidget(rangeField);

        y += 25;
        this.addRenderableWidget(Button.builder(Component.translatable("gui.herobrines_world.wireless_sender_screen.button.save"), b -> saveNetwork())
                .bounds(x, y, 60, 20).build());
    }

    private void saveNetwork() {
        ClientPacketDistributor.sendToServer(new UpdateWirelessSenderPayload(menu.getBlockEntity().getBlockPos(), nameField.getValue(), passwordField.getValue(), parseInt(rangeField.getValue())));
        UpdateWirelessSenderPayload.handleAction(menu.getBlockEntity(), nameField.getValue(), passwordField.getValue(), parseInt(rangeField.getValue()));
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean keyPressed(@NotNull KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_E) {
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float v, int i, int i1) {
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}