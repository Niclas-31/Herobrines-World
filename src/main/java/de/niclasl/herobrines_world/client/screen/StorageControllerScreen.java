package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.menus.StorageControllerMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class StorageControllerScreen extends AbstractContainerScreen<StorageControllerMenu> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "textures/gui/container/storage_controller.png");

    public StorageControllerScreen(StorageControllerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 222);
    }

    @Override
    protected void init() {
        super.init();

        inventoryLabelY = imageHeight - 94;
    }

    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                TEXTURE,
                leftPos,
                topPos,
                0,
                0,
                imageWidth,
                imageHeight,
                256,
                256
        );
    }
}