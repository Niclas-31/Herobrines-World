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

import de.niclasl.herobrines_world.procedures.EnchantenDisplayCondition;
import de.niclasl.herobrines_world.network.message.RedEnchantmentTableGuiButton;
import de.niclasl.herobrines_world.client.ModScreens;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class RedEnchantmentTableGui extends AbstractContainerScreen<de.niclasl.herobrines_world.world.inventory.custom.RedEnchantmentTableGui> implements ModScreens.ScreenAccessor {
    private final int x, y, z;
	private final Player entity;
    private Button button_enchanten;

	public RedEnchantmentTableGui(de.niclasl.herobrines_world.world.inventory.custom.RedEnchantmentTableGui container, Inventory inventory, Component text) {
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

	private static final ResourceLocation texture = ResourceLocation.parse("herobrines_world:textures/screens/red_enchantment_table_gui.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.red_enchantment_table_gui.label_enchantment_table"), 42, 7, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
        Button button_ench_level = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui.button_ench_level"), e -> {
            int x = RedEnchantmentTableGui.this.x;
            int y = RedEnchantmentTableGui.this.y;
            ClientPacketDistributor.sendToServer(new RedEnchantmentTableGuiButton(0, x, y, z));
            RedEnchantmentTableGuiButton.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 87, this.topPos + 25, 82, 20).build();
		this.addRenderableWidget(button_ench_level);
        Button button_enchantments = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui.button_enchantments"), e -> {
            int x = RedEnchantmentTableGui.this.x;
            int y = RedEnchantmentTableGui.this.y;
            ClientPacketDistributor.sendToServer(new RedEnchantmentTableGuiButton(1, x, y, z));
            RedEnchantmentTableGuiButton.handleButtonAction(entity, 1, x, y, z);
        }).bounds(this.leftPos + 81, this.topPos + 57, 88, 20).build();
		this.addRenderableWidget(button_enchantments);
		button_enchanten = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui.button_enchanten"), e -> {
			int x = RedEnchantmentTableGui.this.x;
			int y = RedEnchantmentTableGui.this.y;
			if (EnchantenDisplayCondition.execute(entity)) {
				ClientPacketDistributor.sendToServer(new RedEnchantmentTableGuiButton(2, x, y, z));
				RedEnchantmentTableGuiButton.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 5, this.topPos + 61, 72, 20).build();
		this.addRenderableWidget(button_enchanten);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_enchanten.visible = EnchantenDisplayCondition.execute(entity);
	}
}