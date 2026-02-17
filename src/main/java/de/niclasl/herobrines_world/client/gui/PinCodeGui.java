package de.niclasl.herobrines_world.client.gui;

import net.minecraft.client.input.KeyEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import de.niclasl.herobrines_world.world.inventory.custom.PinCode;
import de.niclasl.herobrines_world.network.message.PinCodeButton;
import de.niclasl.herobrines_world.client.ModScreens;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class PinCodeGui extends AbstractContainerScreen<PinCode> implements ModScreens.ScreenAccessor {
    private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox SetPin;
	private EditBox SetAccount;

    public PinCodeGui(PinCode container, Inventory inventory, Component text) {
		super(container, inventory, text);
        this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("SetPin"))
				SetPin.setValue(stringState);
			else if (name.equals("SetAccount"))
				SetAccount.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		SetPin.render(guiGraphics, mouseX, mouseY, partialTicks);
		SetAccount.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
	}

	@Override
	public boolean keyPressed(@NotNull KeyEvent key) {
		if (key.key() == GLFW.GLFW_KEY_ESCAPE) {
            assert Objects.requireNonNull(this.minecraft).player != null;
            this.minecraft.player.closeContainer();
			return true;
		}
		if (SetPin.isFocused())
			return SetPin.keyPressed(key);
		if (SetAccount.isFocused())
			return SetAccount.keyPressed(key);
		return super.keyPressed(key);
	}

	@Override
	public void resize(@NotNull Minecraft minecraft, int width, int height) {
		String SetPinValue = SetPin.getValue();
		String SetAccountValue = SetAccount.getValue();
		super.resize(minecraft, width, height);
		SetPin.setValue(SetPinValue);
		SetAccount.setValue(SetAccountValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.pin_code.label_create_pin_code"), 52, 46, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.pin_code.label_create_account"), 52, 6, -1, false);
	}

	@Override
	public void init() {
		super.init();
		SetPin = new EditBox(this.font, this.leftPos + 31, this.topPos + 61, 118, 18, Component.translatable("gui.herobrines_world.pin_code.SetPin"));
		SetPin.setMaxLength(8192);
		SetPin.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "SetPin", content, false);
		});
		this.addWidget(this.SetPin);
		SetAccount = new EditBox(this.font, this.leftPos + 31, this.topPos + 21, 118, 18, Component.translatable("gui.herobrines_world.pin_code.SetAccount"));
		SetAccount.setMaxLength(8192);
		SetAccount.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "SetAccount", content, false);
		});
		this.addWidget(this.SetAccount);
        Button button_create = Button.builder(Component.translatable("gui.herobrines_world.pin_code.button_create"), e -> {
            ClientPacketDistributor.sendToServer(new PinCodeButton(0, x, y, z));
            PinCodeButton.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 59, this.topPos + 91, 56, 20).build();
		this.addRenderableWidget(button_create);
	}
}
