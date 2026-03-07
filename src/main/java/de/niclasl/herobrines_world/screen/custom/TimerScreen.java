package de.niclasl.herobrines_world.screen.custom;

import de.niclasl.herobrines_world.network.ModVariables;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import de.niclasl.herobrines_world.network.message.TimerGuiButton;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class TimerScreen extends AbstractContainerScreen<TimerMenu> {
    private final int x, y, z;
	private final Player entity;

    public TimerScreen(TimerMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
        this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 105;
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/gui/container/clock.png"), this.leftPos - 13, this.topPos - 51, 0, 0, 200, 200, 200, 200);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		String timer = new DecimalFormat("####").format(entity.getData(ModVariables.PLAYER_VARIABLES).Day) + ":" + new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Hour) + ":"
				+ new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Minute) + ":" + new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Second);
		String ticks = new DecimalFormat("##").format(entity.getData(ModVariables.PLAYER_VARIABLES).Ticks);
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.timer_gui.label_timer"), 116, 17, -65536, false);
		guiGraphics.drawString(this.font, timer, 20, 21, -65536, false);
		guiGraphics.drawString(this.font, ticks, 111, 70, -65536, false);
	}

	@Override
	public void init() {
		super.init();
        Button button_start = Button.builder(Component.translatable("gui.herobrines_world.timer_gui.button_start"), e -> {
            int x = TimerScreen.this.x;
            int y = TimerScreen.this.y;
            ClientPacketDistributor.sendToServer(new TimerGuiButton(0, x, y, z));
            TimerGuiButton.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 60, this.topPos + 111, 51, 20).build();
		this.addRenderableWidget(button_start);
        Button button_stop = Button.builder(Component.translatable("gui.herobrines_world.timer_gui.button_stop"), e -> {
            ClientPacketDistributor.sendToServer(new TimerGuiButton(1, x, y, z));
            TimerGuiButton.handleButtonAction(entity, 1, x, y, z);
        }).bounds(this.leftPos + 62, this.topPos + 69, 46, 20).build();
		this.addRenderableWidget(button_stop);
        Button button_reset = Button.builder(Component.translatable("gui.herobrines_world.timer_gui.button_reset"), e -> {
            ClientPacketDistributor.sendToServer(new TimerGuiButton(2, x, y, z));
            TimerGuiButton.handleButtonAction(entity, 2, x, y, z);
        }).bounds(this.leftPos + 60, this.topPos + 90, 51, 20).build();
		this.addRenderableWidget(button_reset);
	}
}
