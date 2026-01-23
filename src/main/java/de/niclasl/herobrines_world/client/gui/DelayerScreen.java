package de.niclasl.herobrines_world.client.gui;

import de.niclasl.herobrines_world.block.entity.custom.DelayerEntity;
import de.niclasl.herobrines_world.client.ModScreens;
import de.niclasl.herobrines_world.network.message.DelayerSetTimes;
import de.niclasl.herobrines_world.world.inventory.custom.DelayerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class DelayerScreen extends AbstractContainerScreen<DelayerMenu> implements ModScreens.ScreenAccessor {
    private EditBox ticksBox, secondsBox, minutesBox, hoursBox;

    public DelayerScreen(DelayerMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.leftPos + this.imageWidth / 2;
        int yBoxes = this.topPos + this.imageHeight / 2;

        int boxWidth = 40;
        int spacing = 5;

        int totalWidth = 4 * boxWidth + 3 * spacing;
        int startX = centerX - totalWidth / 2;
        int yPlus = yBoxes - 25;
        int yMinus = yBoxes + 25;

        ticksBox = new EditBox(this.font, startX, yBoxes, boxWidth, 20, Component.literal("Ticks"));
        secondsBox = new EditBox(this.font, startX + (boxWidth + spacing), yBoxes, boxWidth, 20, Component.literal("Seconds"));
        minutesBox = new EditBox(this.font, startX + 2 * (boxWidth + spacing), yBoxes, boxWidth, 20, Component.literal("Minutes"));
        hoursBox = new EditBox(this.font, startX + 3 * (boxWidth + spacing), yBoxes, boxWidth, 20, Component.literal("Hours"));

        DelayerEntity e = menu.getEntity();

        ticksBox.setValue(String.valueOf(e.getTicks()));
        secondsBox.setValue(String.valueOf(e.getSeconds()));
        minutesBox.setValue(String.valueOf(e.getMinutes()));
        hoursBox.setValue(String.valueOf(e.getHours()));

        addNumberLimiter(ticksBox, 0, 20);
        addNumberLimiter(secondsBox, 0, 60);
        addNumberLimiter(minutesBox, 0, 60);
        addNumberLimiter(hoursBox, 0, 24);

        this.addRenderableWidget(ticksBox);
        this.addRenderableWidget(secondsBox);
        this.addRenderableWidget(minutesBox);
        this.addRenderableWidget(hoursBox);

        this.addRenderableWidget(Button.builder(Component.literal("+t"), b -> adjustBox(ticksBox, 1, 20))
                .bounds(startX, yPlus, boxWidth, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+s"), b -> adjustBox(secondsBox, 1, 60))
                .bounds(startX + (boxWidth + spacing), yPlus, boxWidth, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+m"), b -> adjustBox(minutesBox, 1, 60))
                .bounds(startX + 2 * (boxWidth + spacing), yPlus, boxWidth, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+h"), b -> adjustBox(hoursBox, 1, 24))
                .bounds(startX + 3 * (boxWidth + spacing), yPlus, boxWidth, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("-t"), b -> adjustBox(ticksBox, -1, 20))
                .bounds(startX, yMinus, boxWidth, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("-s"), b -> adjustBox(secondsBox, -1, 60))
                .bounds(startX + (boxWidth + spacing), yMinus, boxWidth, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("-m"), b -> adjustBox(minutesBox, -1, 60))
                .bounds(startX + 2 * (boxWidth + spacing), yMinus, boxWidth, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("-h"), b -> adjustBox(hoursBox, -1, 24))
                .bounds(startX + 3 * (boxWidth + spacing), yMinus, boxWidth, 20).build());

        int saveButtonWidth = 60;
        int saveButtonHeight = 20;
        int saveButtonX = this.leftPos + (this.imageWidth / 2) - (saveButtonWidth / 2);
        int saveButtonY = this.topPos + this.imageHeight - 25;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.herobrines_world.delayer.button.save"), b -> saveTimes())
                .bounds(saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight).build());
    }

    private void saveTimes() {
        int ticks = parseBox(ticksBox);
        int seconds = parseBox(secondsBox);
        int minutes = parseBox(minutesBox);
        int hours = parseBox(hoursBox);

        ClientPacketDistributor.sendToServer(new DelayerSetTimes(menu.pos, ticks, seconds, minutes, hours));
        DelayerSetTimes.handleAction(menu.player, menu.pos, ticks, seconds, minutes, hours);
    }

    public void addNumberLimiter(EditBox box, int min, int max) {

        box.setFilter(this::onlyDigits);

        box.setResponder(input -> {
            if (input.isEmpty()) return;

            int value = Integer.parseInt(input);

            if (value < min) {
                if (!box.getValue().equals(String.valueOf(min))) {
                    box.setValue(String.valueOf(min));
                }
                return;
            }

            if (value > max) {
                if (!box.getValue().equals(String.valueOf(max))) {
                    box.setValue(String.valueOf(max));
                }
            }
        });
    }

    private boolean onlyDigits(String s) {
        if (s.isEmpty()) return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }


    private int parseBox(EditBox box) {
        try {
            return Integer.parseInt(box.getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void adjustBox(EditBox box, int delta, int max) {
        try {
            int value = Integer.parseInt(box.getValue());
            value += delta;
            if (value < 0) value = 0;
            if (value > max) value = max;
            box.setValue(String.valueOf(value));
        } catch (NumberFormatException e) {
            box.setValue("0");
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics p_281635_, int p_282681_, int p_283686_) {

    }

    @Override
    public void updateMenuState(int elementType, String name, Object elementState) {

    }
}