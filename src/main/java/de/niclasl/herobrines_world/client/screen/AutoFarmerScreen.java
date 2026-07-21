package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.menus.AutoFarmerMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class AutoFarmerScreen extends AbstractContainerScreen<AutoFarmerMenu> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "textures/gui/container/auto_farmer.png");

    public AutoFarmerScreen(AutoFarmerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 198, 166);
    }

    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                TEXTURE,
                x,
                y,
                0.0F,
                0.0F,
                imageWidth,
                imageHeight,
                256,
                256
        );
    }
}