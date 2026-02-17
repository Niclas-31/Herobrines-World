package de.niclasl.herobrines_world.client.gui;

import net.minecraft.client.input.KeyEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import de.niclasl.herobrines_world.world.inventory.custom.Time;
import de.niclasl.herobrines_world.network.message.ClockGuiButton;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class TimeGui extends AbstractContainerScreen<Time> {
    private final int x, y, z;
	private final Player entity;

    public TimeGui(Time container, Inventory inventory, Component text) {
		super(container, inventory, text);
        this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 100;
		this.imageHeight = 80;
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/uhr.png"), this.leftPos - 47, this.topPos - 59, 0, 0, -1, -1, -1, -1);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/clock.png"), this.leftPos - 49, this.topPos - 59, 0, 0, 200, 200, 200, 200);
	}

	@Override
	public boolean keyPressed(KeyEvent key) {
		if (key.key() == GLFW.GLFW_KEY_ESCAPE) {
            assert Objects.requireNonNull(this.minecraft).player != null;
            this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.clock_gui.label_clock"), 63, 9, -65536, false);
		String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
		guiGraphics.drawString(this.font, date, -12, 19, -65536, false);
		guiGraphics.drawString(this.font, time, -11, 55, -65536, false);
	}

	@Override
	public void init() {
		super.init();
        Button button_hide = Button.builder(Component.translatable("gui.herobrines_world.clock_gui.button_hide"), e -> {
            ClientPacketDistributor.sendToServer(new ClockGuiButton(0, x, y, z));
            ClockGuiButton.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 31, this.topPos + 83, 46, 20).build();
		this.addRenderableWidget(button_hide);
	}
}

