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

import de.niclasl.herobrines_world.world.inventory.custom.Account;
import de.niclasl.herobrines_world.network.message.AccountButton;
import de.niclasl.herobrines_world.client.ModScreens;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class AccountGui extends AbstractContainerScreen<Account> implements ModScreens.ScreenAccessor {
    private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox Account;
	private EditBox PinCode;

    public AccountGui(Account container, Inventory inventory, Component text) {
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
			if (name.equals("Account"))
				Account.setValue(stringState);
			else if (name.equals("PinCode"))
				PinCode.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		Account.render(guiGraphics, mouseX, mouseY, partialTicks);
		PinCode.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (Account.isFocused())
			return Account.keyPressed(key);
		if (PinCode.isFocused())
			return PinCode.keyPressed(key);
		return super.keyPressed(key);
	}

	@Override
	public void resize(@NotNull Minecraft minecraft, int width, int height) {
		String AccountValue = Account.getValue();
		String PinCodeValue = PinCode.getValue();
		super.resize(minecraft, width, height);
		Account.setValue(AccountValue);
		PinCode.setValue(PinCodeValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.account.label_account"), 70, 8, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.account.label_pin"), 81, 54, -1, false);
	}

	@Override
	public void init() {
		super.init();
		Account = new EditBox(this.font, this.leftPos + 28, this.topPos + 26, 118, 18, Component.translatable("gui.herobrines_world.account.Account"));
		Account.setMaxLength(8192);
		Account.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "Account", content, false);
		});
		this.addWidget(this.Account);
		PinCode = new EditBox(this.font, this.leftPos + 28, this.topPos + 70, 118, 18, Component.translatable("gui.herobrines_world.account.PinCode"));
		PinCode.setMaxLength(8192);
		PinCode.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "PinCode", content, false);
		});
		this.addWidget(this.PinCode);
        Button button_confirm = Button.builder(Component.translatable("gui.herobrines_world.account.button_confirm"), e -> {
            ClientPacketDistributor.sendToServer(new AccountButton(0, x, y, z));
            AccountButton.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 27, this.topPos + 96, 61, 20).build();
		this.addRenderableWidget(button_confirm);
        Button button_delete = Button.builder(Component.translatable("gui.herobrines_world.account.button_delete"), e -> {
            ClientPacketDistributor.sendToServer(new AccountButton(1, x, y, z));
            AccountButton.handleButtonAction(entity, 1, x, y, z);
        }).bounds(this.leftPos + 91, this.topPos + 96, 56, 20).build();
		this.addRenderableWidget(button_delete);
	}
}
