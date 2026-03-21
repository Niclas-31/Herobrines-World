<<<<<<< HEAD
package de.niclasl.herobrines_world.screen.custom.slider;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class SignalStrengthSlider extends AbstractSliderButton {

    public SignalStrengthSlider(int x, int y, int width, int height, double value) {
        super(x, y, width, height, Component.literal("Signal"), value);
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal("Signal: " + getSignalStrength()));
    }

    public int getSignalStrength() {
        return (int)(this.value * 15);
    }

    @Override
    protected void applyValue() {

    }
=======
package de.niclasl.herobrines_world.screen.custom.slider;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class SignalStrengthSlider extends AbstractSliderButton {

    public SignalStrengthSlider(int x, int y, int width, int height, double value) {
        super(x, y, width, height, Component.literal("Signal"), value);
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal("Signal: " + getSignalStrength()));
    }

    public int getSignalStrength() {
        return (int)(this.value * 15);
    }

    @Override
    protected void applyValue() {

    }
>>>>>>> 518d1b56f9483d0f8c1bbbbd1257398343a230b6
}