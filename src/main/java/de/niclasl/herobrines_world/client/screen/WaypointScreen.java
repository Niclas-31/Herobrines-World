package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.message.DeleteWaypointPacket;
import de.niclasl.herobrines_world.common.network.message.DeselectWaypointPacket;
import de.niclasl.herobrines_world.common.network.message.RenameWaypointPacket;
import de.niclasl.herobrines_world.common.network.message.SyncWaypointCompass;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SavedWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.UUID;

public class WaypointScreen extends Screen {

    private final List<SavedWaypoint> waypoints;

    private int scrollOffset;

    private UUID editingWaypoint;

    private EditBox renameBox;
    private Button saveButton;

    private final Identifier EDIT = Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "icon/edit");
    private final Identifier TRASH = Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "icon/trash");

    public WaypointScreen(ItemStack stack) {
        super(Component.translatable("gui.herobrines_world.waypoint.title"));

        List<SavedWaypoint> list = stack.get(ModDataComponents.WAYPOINTS);

        this.waypoints = list == null ? List.of() : list;
    }

    @Override
    protected void init() {

        super.init();

        createWaypointButtons();

        renameBox = new EditBox(this.font, this.width / 2 - 100, this.height - 60, 200, 20,
                Component.literal("Name")
        );

        renameBox.setVisible(false);
        addRenderableWidget(renameBox);

        saveButton = Button.builder(Component.literal("Save"), b -> save())
                .bounds(this.width / 2 - 100, this.height - 30, 100, 20)
                .build();

        saveButton.active = false;

        addRenderableWidget(saveButton);

        assert Minecraft.getInstance().player != null;
        ItemStack stack = Minecraft.getInstance().player.getMainHandItem();

        UUID selected = stack.get(ModDataComponents.SELECTED_WAYPOINT.get());

        Button deselect = addRenderableWidget(
                Button.builder(Component.literal("Deselect"), b -> deselect())
                        .bounds(this.width / 2, this.height - 30, 100, 20)
                        .build()
        );

        deselect.active = selected != null;
    }

    private void createWaypointButtons() {
        Minecraft mc = Minecraft.getInstance();
        int startY = 40;

        int maxVisible = 6;

        for (int i = scrollOffset; i < Math.min(scrollOffset + maxVisible, waypoints.size()); i++) {

            SavedWaypoint waypoint = waypoints.get(i);

            int visibleIndex = i - scrollOffset;

            int y = startY + (visibleIndex * 25);

            if (y < 35 || y > height - 50) continue;

            Component text = Component.literal(waypoint.name());

            final int index = i;

            addRenderableWidget(
                    Button.builder(
                            text,
                            button -> {
                                        ClientPacketDistributor.sendToServer(new SyncWaypointCompass(waypoint.id()));

                                        this.onClose();
                                    }
                            )
                            .bounds(this.width / 2 - 100, y, 200, 20)
                            .build()
            );

            Component message = Component.translatable("gui.herobrines_world.waypoint.edit");
            EditButton editButton = new EditButton(message, EDIT, b -> {
                editingWaypoint = waypoint.id();

                renameBox.setValue(waypoints.get(index).name());
                renameBox.setVisible(true);
                renameBox.setFocused(true);
                saveButton.active = true;
            }, message);

            editButton.setPosition(this.width / 2 + 105, y);

            addRenderableWidget(editButton);

            Component message1 = Component.translatable("gui.herobrines_world.waypoint.delete");
            TrashButton trashButton = new TrashButton(message1, TRASH, b -> {
                if (!mc.hasShiftDown()) return;

                ClientPacketDistributor.sendToServer(new DeleteWaypointPacket(waypoint.id()));

                this.onClose();
            }, message1);

            trashButton.setPosition(this.width / 2 + 130, y);

            addRenderableWidget(trashButton);
        }
    }

    private void save() {
        if (editingWaypoint == null) return;

        String newName = renameBox.getValue().trim();

        if (newName.isEmpty()) return;

        ClientPacketDistributor.sendToServer(new RenameWaypointPacket(editingWaypoint, newName));

        editingWaypoint = null;
        renameBox.setVisible(false);

        this.onClose();
    }

    private void deselect() {
        ClientPacketDistributor.sendToServer(new DeselectWaypointPacket());
        this.onClose();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int maxScroll = Math.max(0, waypoints.size() - 6);

        scrollOffset -= (int)scrollY;

        scrollOffset = Mth.clamp(scrollOffset, 0, maxScroll);

        scrollOffset = Mth.clamp(scrollOffset, 0, maxScroll);

        clearWidgets();
        init();

        return true;
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFFFF);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_ENTER && editingWaypoint != null) {
            String newName = renameBox.getValue().trim();

            if (!newName.isEmpty()) {
                ClientPacketDistributor.sendToServer(new RenameWaypointPacket(editingWaypoint, newName));
            }

            editingWaypoint = null;
            renameBox.setVisible(false);

            this.onClose();
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static class EditButton extends SpriteIconButton.CenteredIcon {

        protected EditButton(Component message, Identifier sprite, OnPress onPress, @Nullable Component tooltip) {
            super(20, 20, message, 14, 14, new WidgetSprites(sprite), onPress, tooltip, null);
        }
    }

    static class TrashButton extends SpriteIconButton.CenteredIcon {

        protected TrashButton(Component message, Identifier sprite, OnPress onPress, @Nullable Component tooltip) {
            super(20, 20, message, 14, 14, new WidgetSprites(sprite), onPress, tooltip, null);
        }
    }
}