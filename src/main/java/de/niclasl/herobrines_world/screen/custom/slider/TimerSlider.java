package de.niclasl.herobrines_world.screen.custom.slider;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class TimerSlider extends AbstractSliderButton {

    public TimerSlider(int x, int y, int width, int height, double interval) {
        super(x, y, width, height, Component.literal("Interval"), interval);
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal("Ticks: " + getInterval()));
    }

    public int getInterval() {
        return (int)(this.value * 200);
    }

    @Override
    protected void applyValue() {

    }
}
