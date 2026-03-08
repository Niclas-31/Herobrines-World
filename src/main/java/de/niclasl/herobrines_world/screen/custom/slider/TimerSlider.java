package de.niclasl.herobrines_world.screen.custom.slider;

import de.niclasl.herobrines_world.block.entity.custom.RedstoneTimerBlockEntity;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class TimerSlider extends AbstractSliderButton {

    private final RedstoneTimerBlockEntity blockEntity;

    public TimerSlider(int x, int y, int width, int height,
                       Component text,
                       RedstoneTimerBlockEntity blockEntity) {

        super(x, y, width, height, text, 0);
        this.blockEntity = blockEntity;

        this.value = blockEntity.getInterval() / 200.0;
        updateMessage();
    }

    @Override
    protected void updateMessage() {

        int interval = (int)(value * 200);

        setMessage(Component.literal("Ticks: " + interval));
    }

    @Override
    protected void applyValue() {

        int interval = (int)(value * 200);

        blockEntity.setInterval(interval);
    }
}