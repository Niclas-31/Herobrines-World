package de.niclasl.herobrines_world.client.gui;

import de.niclasl.herobrines_world.client.ModScreens;
import de.niclasl.herobrines_world.client.screens.PasswordPromptScreen;
import de.niclasl.herobrines_world.network.data.ConnectNetworkPayload;
import de.niclasl.herobrines_world.network.data.VisibleNetwork;
import de.niclasl.herobrines_world.world.inventory.custom.WirelessReceiverMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class WirelessReceiverScreen
        extends AbstractContainerScreen<WirelessReceiverMenu> implements ModScreens.ScreenAccessor {

    public WirelessReceiverScreen(WirelessReceiverMenu menu,
                                  Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        super.init();

        int x = leftPos + 10;
        int y = topPos + 20;

        for (VisibleNetwork net : menu.getNetworks()) {
            this.addRenderableWidget(Button.builder(Component.literal(net.name() + (net.locked() ? " 🔒" : "")),
                            btn -> onNetworkClicked(net)
                    ).bounds(x, y, 150, 20).build()
            );
            y += 24;
        }
    }


    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float v, int i, int i1) {
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void onNetworkClicked(VisibleNetwork net) {
        if (net.locked()) {
            assert minecraft != null;
            minecraft.setScreen(
                    new PasswordPromptScreen(net, pwd -> {
                        sendConnect(net, pwd);
                        minecraft.setScreen(this);
                    })
            );
        } else {
            sendConnect(net, "");
        }
    }

    private void sendConnect(VisibleNetwork net, String password) {
        ClientPacketDistributor.sendToServer(
                new ConnectNetworkPayload(
                        menu.getBlockPos(),
                        net.pos(),
                        password
                )
        );
    }

    @Override
    public void updateMenuState(int elementType, String name, Object elementState) {
    }
}
