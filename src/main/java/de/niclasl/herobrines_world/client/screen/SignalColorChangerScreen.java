package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.menus.SignalColorChangerMenu;
import de.niclasl.herobrines_world.common.network.message.SyncColorPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class SignalColorChangerScreen extends AbstractContainerScreen<SignalColorChangerMenu> {
    private final int x, y, z;

    public SignalColorChangerScreen(SignalColorChangerMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);

        this.x = container.x;
		this.y = container.y;
		this.z = container.z;

		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		Object[][] lamps = {
				{-105, 56, "red_lamp"}, {-89, 56, "red_lamp_on"},
				{-63, 56, "yellow_lamp"}, {-48, 56, "yellow_lamp_on"},
				{-21, 56, "green_lamp"}, {-5, 56, "green_lamp_on"},
				{20, 56, "blue_lamp"}, {36, 56, "blue_lamp_on"},
				{63, 56, "cyan_lamp"}, {79, 56, "cyan_lamp_on"},
				{106, 56, "orange_lamp"}, {122, 56, "orange_lamp_on"},
				{-105, 87, "gray_lamp"}, {-89, 87, "gray_lamp_on"},
				{-63, 87, "light_blue_lamp"}, {-47, 87, "light_blue_lamp_on"},
				{-21, 87, "light_gray_lamp"}, {-5, 87, "gray_lamp_on"},
				{20, 87, "lime_lamp"}, {36, 87, "lime_lamp_on"},
				{63, 87, "magenta_lamp"}, {79, 87, "magenta_lamp_on"},
				{106, 87, "pink_lamp"}, {122, 87, "pink_lamp_on"}
		};

		for (Object[] lamp : lamps) {
			int x = (int) lamp[0];
			int y = (int) lamp[1];
			String color = (String) lamp[2];

			Identifier texture = Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID,
					"container/signal/" + color + ".png");

			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos + x, this.topPos + y,
					0, 0, 16, 16, 16, 16);
		}
	}

    @Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();

		int[][] buttonData = {
				{-21, -20, 0}, {-63, -20, 1}, {-104, -20, 2}, {20, -20, 3},
				{63, -20, 4}, {-104, 20, 5}, {-63, 20, 6}, {-21, 20, 7},
				{20, 20, 8}, {63, 20, 9}, {106, -20, 10}, {106, 20, 11}
		};

		String[] colors = {
				"green", "yellow", "red", "blue",
				"cyan", "gray", "light_blue", "light_gray",
				"lime", "magenta", "orange", "pink"
		};

		for (int i = 0; i < buttonData.length; i++) {
			int xOffset = buttonData[i][0];
			int yOffset = buttonData[i][1];
			int buttonID = buttonData[i][2];
			String color = colors[i];

			int posX = this.leftPos + xOffset;
			int posY = this.topPos + yOffset;

			Identifier texture = Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID,
					"textures/gui/sprites/container/signal/" + color + "_gui_button.png");

			ImageButton button = new ImageButton(
					posX,
					posY,
					24, 24,
					new WidgetSprites(texture, texture),
					e -> ClientPacketDistributor.sendToServer(new SyncColorPacket(buttonID, x, y, z))
			) {
				@Override
				public void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
					guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, posX, posY, 0, 0, 24, 24, 24, 24);
				}
			};

			this.addRenderableWidget(button);
		}
	}
}