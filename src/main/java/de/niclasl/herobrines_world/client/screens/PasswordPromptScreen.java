package de.niclasl.herobrines_world.client.screens;

import de.niclasl.herobrines_world.network.data.VisibleNetwork;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class PasswordPromptScreen extends Screen {

    private final VisibleNetwork network;
    private final Consumer<String> callback;
    private EditBox box;

    public PasswordPromptScreen(VisibleNetwork net,
                                Consumer<String> callback) {
        super(Component.literal("Password"));
        this.network = net;
        this.callback = callback;
    }

    @Override
    protected void init() {
        box = new EditBox(font, width / 2 - 50, height / 2 - 10,
                100, 20, Component.empty());
        addRenderableWidget(box);

        addRenderableWidget(
                Button.builder(Component.literal("Connect"),
                        b -> {
                    callback.accept(box.getValue());

                            if (network.receiver() != null) {
                                network.receiver().connectToNetwork(true);
                            }
                }
                ).bounds(width / 2 - 30, height / 2 + 20, 60, 20).build()
        );
    }
}