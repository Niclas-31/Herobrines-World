package de.niclasl.herobrines_world.client.gui;

import de.niclasl.herobrines_world.world.inventory.custom.SignalColorChanger;
import net.minecraft.client.input.KeyEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import de.niclasl.herobrines_world.network.message.SignalColorChangerGuiButton;
import de.niclasl.herobrines_world.client.ModScreens;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class SignalColorChangerGui extends AbstractContainerScreen<SignalColorChanger> implements ModScreens.ScreenAccessor {
    private final int x, y, z;
	private final Player entity;

    public SignalColorChangerGui(SignalColorChanger container, Inventory inventory, Component text) {
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
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/red_lamp.png"), this.leftPos - 105, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/red_lamp_on.png"), this.leftPos - 89, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/yellow_lamp.png"), this.leftPos - 63, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/yellow_lamp_on.png"), this.leftPos - 48, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/green_lamp.png"), this.leftPos - 21, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/green_lamp_on.png"), this.leftPos - 5, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/blue_lamp.png"), this.leftPos + 20, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/blue_lamp_on.png"), this.leftPos + 36, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/cyan_lamp.png"), this.leftPos + 63, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/cyan_lamp_on.png"), this.leftPos + 79, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/orange_lamp.png"), this.leftPos + 106, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/orange_lamp_on.png"), this.leftPos + 122, this.topPos + 56, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/gray_lamp.png"), this.leftPos - 105, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/gray_lamp_on.png"), this.leftPos - 89, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/light_blue_lamp.png"), this.leftPos - 63, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/light_blue_lamp_on.png"), this.leftPos - 47, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/light_gray_lamp.png"), this.leftPos - 21, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/gray_lamp_on.png"), this.leftPos - 5, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/lime_lamp.png"), this.leftPos + 20, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/lime_lamp_on.png"), this.leftPos + 36, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/magenta_lamp.png"), this.leftPos + 63, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/magenta_lamp_on.png"), this.leftPos + 79, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/pink_lamp.png"), this.leftPos + 106, this.topPos + 87, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("herobrines_world:textures/screens/pink_lamp_on.png"), this.leftPos + 122, this.topPos + 87, 0, 0, 16, 16, 16, 16);
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
        ImageButton imagebutton_green_gui_button = new ImageButton(this.leftPos - 21, this.topPos - 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/green_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/green_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(0, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 0, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_green_gui_button);
        ImageButton imagebutton_yellow_gui_button = new ImageButton(this.leftPos - 63, this.topPos - 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/yellow_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/yellow_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(1, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 1, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_yellow_gui_button);
        ImageButton imagebutton_red_gui_button = new ImageButton(this.leftPos - 104, this.topPos - 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/red_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/red_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(2, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 2, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_red_gui_button);
        ImageButton imagebutton_blue_gui_button = new ImageButton(this.leftPos + 20, this.topPos - 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/blue_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/blue_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(3, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 3, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_blue_gui_button);
        ImageButton imagebutton_cyan_gui_button = new ImageButton(this.leftPos + 63, this.topPos - 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/cyan_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/cyan_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(4, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 4, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_cyan_gui_button);
        ImageButton imagebutton_gray_gui_button = new ImageButton(this.leftPos - 104, this.topPos + 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/gray_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/gray_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(5, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 5, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_gray_gui_button);
        ImageButton imagebutton_light_blue_gui_button = new ImageButton(this.leftPos - 63, this.topPos + 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/light_blue_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/light_blue_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(6, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 6, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_light_blue_gui_button);
        ImageButton imagebutton_light_gray_gui_button = new ImageButton(this.leftPos - 21, this.topPos + 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/light_gray_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/light_gray_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(7, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 7, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_light_gray_gui_button);
        ImageButton imagebutton_lime_gui_button = new ImageButton(this.leftPos + 20, this.topPos + 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/lime_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/lime_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(8, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 8, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_lime_gui_button);
        ImageButton imagebutton_magenta_gui_button = new ImageButton(this.leftPos + 63, this.topPos + 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/magenta_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/magenta_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(9, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 9, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_magenta_gui_button);
        ImageButton imagebutton_orange_gui_button = new ImageButton(this.leftPos + 106, this.topPos - 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/orange_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/orange_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(10, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 10, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_orange_gui_button);
        ImageButton imagebutton_pink_gui_button = new ImageButton(this.leftPos + 106, this.topPos + 20, 24, 24,
                new WidgetSprites(ResourceLocation.parse("herobrines_world:textures/screens/pink_gui_button.png"), ResourceLocation.parse("herobrines_world:textures/screens/pink_gui_button.png")), e -> {
            int x = SignalColorChangerGui.this.x;
            int y = SignalColorChangerGui.this.y;
            ClientPacketDistributor.sendToServer(new SignalColorChangerGuiButton(11, x, y, z));
            SignalColorChangerGuiButton.handleButtonAction(entity, 11, x, y, z);
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_pink_gui_button);
	}
}