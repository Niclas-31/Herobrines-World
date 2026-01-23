package de.niclasl.herobrines_world.client.gui;

import net.minecraft.client.input.KeyEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import de.niclasl.herobrines_world.world.inventory.custom.LogOut;
import de.niclasl.herobrines_world.network.message.LogOutButton;
import de.niclasl.herobrines_world.client.ModScreens;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class LogOutGui extends AbstractContainerScreen<LogOut> implements ModScreens.ScreenAccessor {
    private final int x, y, z;
	private final Player entity;

    public LogOutGui(LogOut container, Inventory inventory, Component text) {
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
    }

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		return super.keyPressed(key);
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
        Button button_logout = Button.builder(Component.translatable("gui.herobrines_world.log_out.button_logout"), e -> {
            int x = LogOutGui.this.x;
            int y = LogOutGui.this.y;
			int z = LogOutGui.this.z;
            ClientPacketDistributor.sendToServer(new LogOutButton(0, x, y, z));
            LogOutButton.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 62, this.topPos + 69, 56, 20).build();
		this.addRenderableWidget(button_logout);
	}
}