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

import de.niclasl.herobrines_world.procedures.ButtonCondition4;
import de.niclasl.herobrines_world.procedures.ButtonCondition3;
import de.niclasl.herobrines_world.procedures.ButtonCondition2;
import de.niclasl.herobrines_world.procedures.ButtonCondition1;
import de.niclasl.herobrines_world.network.message.RedEnchantmentTableGui2Button;
import de.niclasl.herobrines_world.client.ModScreens;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class RedEnchantmentTableGui2 extends AbstractContainerScreen<de.niclasl.herobrines_world.world.inventory.custom.RedEnchantmentTableGui2> implements ModScreens.ScreenAccessor {
    private final int x, y, z;
	private final Player entity;
    private Button button_1;
	private Button button_2;
	private Button button_3;
	private Button button_4;

	public RedEnchantmentTableGui2(de.niclasl.herobrines_world.world.inventory.custom.RedEnchantmentTableGui2 container, Inventory inventory, Component text) {
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

	private static final ResourceLocation texture = ResourceLocation.parse("herobrines_world:textures/screens/red_enchantment_table_gui_2.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.herobrines_world.red_enchantment_table_gui_2.label_enchantment_table"), 42, 7, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
        Button button_back = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_2.button_back"), e -> {
            int x = RedEnchantmentTableGui2.this.x;
            int y = RedEnchantmentTableGui2.this.y;
            ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui2Button(0, x, y, z));
            RedEnchantmentTableGui2Button.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 114, this.topPos + 25, 56, 20).build();
		this.addRenderableWidget(button_back);
		button_1 = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_2.button_1"), e -> {
			int x = RedEnchantmentTableGui2.this.x;
			int y = RedEnchantmentTableGui2.this.y;
			if (ButtonCondition1.execute(entity)) {
				ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui2Button(1, x, y, z));
				RedEnchantmentTableGui2Button.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 25, 30, 20).build();
		this.addRenderableWidget(button_1);
		button_2 = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_2.button_2"), e -> {
			int x = RedEnchantmentTableGui2.this.x;
			int y = RedEnchantmentTableGui2.this.y;
			if (ButtonCondition2.execute(entity)) {
				ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui2Button(2, x, y, z));
				RedEnchantmentTableGui2Button.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 52, 30, 20).build();
		this.addRenderableWidget(button_2);
		button_3 = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_2.button_3"), e -> {
			int x = RedEnchantmentTableGui2.this.x;
			int y = RedEnchantmentTableGui2.this.y;
			if (ButtonCondition3.execute(entity)) {
				ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui2Button(3, x, y, z));
				RedEnchantmentTableGui2Button.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 51, this.topPos + 25, 30, 20).build();
		this.addRenderableWidget(button_3);
		button_4 = Button.builder(Component.translatable("gui.herobrines_world.red_enchantment_table_gui_2.button_4"), e -> {
			int x = RedEnchantmentTableGui2.this.x;
			int y = RedEnchantmentTableGui2.this.y;
			if (ButtonCondition4.execute(entity)) {
				ClientPacketDistributor.sendToServer(new RedEnchantmentTableGui2Button(4, x, y, z));
				RedEnchantmentTableGui2Button.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 51, this.topPos + 52, 30, 20).build();
		this.addRenderableWidget(button_4);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_1.visible = ButtonCondition1.execute(entity);
		this.button_2.visible = ButtonCondition2.execute(entity);
		this.button_3.visible = ButtonCondition3.execute(entity);
		this.button_4.visible = ButtonCondition4.execute(entity);
	}
}