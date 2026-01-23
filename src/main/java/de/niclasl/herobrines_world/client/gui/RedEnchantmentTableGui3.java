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

import de.niclasl.herobrines_world.network.message.RedEnchantmentTableGui3Button;
import de.niclasl.herobrines_world.client.ModScreens;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class RedEnchantmentTableGui3 extends AbstractContainerScreen<de.niclasl.herobrines_world.world.inventory.custom.RedEnchantmentTableGui3> implements ModScreens.ScreenAccessor {
    private final int x, y, z;
	private final Player entity;

    public RedEnchantmentTableGui3(de.niclasl.herobrines_world.world.inventory.custom.RedEnchantmentTableGui3 container, Inventory inventory, Component text) {
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

	private static final ResourceLocation texture = ResourceLocation.parse("herobrines_world:textures/screens/red_enchantment_table_gui_3.png");

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.red_enchantment_table_gui_3.label_enchantment_table"), 42, 7, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
        Button button_back = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_3.button_back"), e -> {
            int x = RedEnchantmentTableGui3.this.x;
            int y = RedEnchantmentTableGui3.this.y;
            ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui3Button(0, x, y, z));
            RedEnchantmentTableGui3Button.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 114, this.topPos + 25, 56, 20).build();
		this.addRenderableWidget(button_back);
        Button button_herobrine = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_3.button_herobrine"), e -> {
            int x = RedEnchantmentTableGui3.this.x;
            int y = RedEnchantmentTableGui3.this.y;
            ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui3Button(1, x, y, z));
            RedEnchantmentTableGui3Button.handleButtonAction(entity, 1, x, y, z);
        }).bounds(this.leftPos + 24, this.topPos + 25, 72, 20).build();
		this.addRenderableWidget(button_herobrine);
        Button button_more_souls = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_3.button_more_souls"), e -> {
            int x = RedEnchantmentTableGui3.this.x;
            int y = RedEnchantmentTableGui3.this.y;
            ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui3Button(2, x, y, z));
            RedEnchantmentTableGui3Button.handleButtonAction(entity, 2, x, y, z);
        }).bounds(this.leftPos + 24, this.topPos + 52, 82, 20).build();
		this.addRenderableWidget(button_more_souls);
	}
}