package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.common.network.access.AccessModeImpl;
import de.niclasl.herobrines_world.common.network.message.SyncChipPacket;
import de.niclasl.herobrines_world.common.network.transfer.TransferModeImpl;
import de.niclasl.herobrines_world.common.registries.menus.SmartChipMenu;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SmartChipScreen extends AbstractContainerScreen<SmartChipMenu> {

    private enum Tab {
        TRANSFER,
        ACCESS
    }

    private Tab currentTab = Tab.TRANSFER;

    private TransferMode transferMode;
    private int range;
    private int speed;

    private AccessMode accessMode;
    private UUID owner;
    private int level;

    private Button modeButton;
    private EditBox rangeBox;
    private EditBox speedBox;

    private Button accessButton;
    private EditBox tierBox;
    private EditBox ownerBox;

    public SmartChipScreen(SmartChipMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        loadData();
        rebuildTab();
    }

    private void loadData() {
        var transfer = menu.getTransferData();

        transferMode = transfer.mode();

        if (transferMode == null) {
            transferMode = TransferModeImpl.INSERT;
        }

        speed = transfer.speed();
        range = transfer.range();

        var access = menu.getAccessData();

        accessMode = access.mode();

        if (accessMode == null) {
            accessMode = AccessModeImpl.PUBLIC;
        }

        owner = access.owner();
        level = access.level();
    }

    private void rebuildTab() {
        clearWidgets();

        int half = this.imageWidth / 2;

        addRenderableWidget(Button.builder(
                Component.translatable("gui.herobrines_world.smart_chip.transfer"),
                b -> {
                    saveCurrentInputs();
                    currentTab = Tab.TRANSFER;
                    rebuildTab();
                }
        ).bounds(this.leftPos, this.topPos, half, 20).build());

        Button access = addRenderableWidget(Button.builder(
                Component.translatable("gui.herobrines_world.smart_chip.access"),
                b -> {
                    saveCurrentInputs();
                    currentTab = Tab.ACCESS;
                    rebuildTab();
                }
        ).bounds(this.leftPos + half, this.topPos, half, 20).build());

        access.active = false;

        switch (currentTab) {
            case TRANSFER -> buildTransferTab();
            case ACCESS -> buildAccessTab();
        }
    }

    private void buildTransferTab() {
        modeButton = addRenderableWidget(Button.builder(
                Component.translatable("gui.herobrines_world.smart_chip.transfer_mode",
                        Component.translatable(
                                "gui.herobrines_world.smart_chip.transfer_mode." + transferMode.id().getPath())),
                b -> {
                    transferMode = nextMode(transferMode);
                    updateTransferButtons();
                }
        ).bounds(this.leftPos + 10, row(0), 160, 20).build());

        addRenderableWidget(Button.builder(Component.literal("-"), b -> adjustBox(rangeBox, -1, 16))
                .bounds(this.leftPos + 10, row(1), 20, 20).build());
        rangeBox = new EditBox(this.font, this.leftPos + 35, row(1), 80, 20, Component.literal("Range"));
        rangeBox.setValue(String.valueOf(range));
        addNumberLimiter(rangeBox, 4, 16);
        addRenderableWidget(rangeBox);
        addRenderableWidget(Button.builder(Component.literal("+"), b -> adjustBox(rangeBox, 1, 16))
                .bounds(this.leftPos + 120, row(1), 20, 20).build());

        addRenderableWidget(Button.builder(Component.literal("-"), b -> adjustBox(speedBox, -1, 2))
                .bounds(this.leftPos + 10, row(2), 20, 20).build());
        speedBox = new EditBox(this.font, this.leftPos + 35, row(2), 80, 20, Component.literal("Speed"));
        speedBox.setValue(String.valueOf(speed));
        addNumberLimiter(speedBox, 0, 2);
        addRenderableWidget(speedBox);
        addRenderableWidget(Button.builder(Component.literal("+"), b -> adjustBox(speedBox, 1, 2))
                .bounds(this.leftPos + 120, row(2), 20, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.herobrines_world.smart_chip.save_transfer"), b -> saveTransfer())
                .bounds(this.leftPos + 10, row(4), 160, 20).build());
    }

    private void buildAccessTab() {
        accessButton = addRenderableWidget(Button.builder(
                Component.translatable("gui.herobrines_world.smart_chip.access_mode",
                        Component.translatable(
                                "gui.herobrines_world.smart_chip.access_mode." + accessMode.id().getPath()
                        )),
                b -> {
                    accessMode = nextAccess(accessMode);
                    updateAccessButtons();
                }
        ).bounds(this.leftPos + 10, row(0), 160, 20).build());

        addRenderableWidget(Button.builder(Component.literal("-"), b -> adjustBox(tierBox, -1, 10))
                .bounds(this.leftPos + 10, row(1), 20, 20).build());
        tierBox = new EditBox(this.font, this.leftPos + 35, row(1), 80, 20, Component.literal("Tier"));
        tierBox.setValue(String.valueOf(level));
        addNumberLimiter(tierBox, 0, 10);
        addRenderableWidget(tierBox);
        addRenderableWidget(Button.builder(Component.literal("+"), b -> adjustBox(tierBox, 1, 10))
                .bounds(this.leftPos + 120, row(1), 20, 20).build());

        ownerBox = new EditBox(this.font, this.leftPos + 10, row(2), 120, 20, Component.literal("Owner UUID"));
        ownerBox.setMaxLength(36);
        ownerBox.setValue(owner == null ? "" : owner.toString());
        ownerBox.setFilter(this::isValidUuidInput);
        addRenderableWidget(ownerBox);
        addRenderableWidget(Button.builder(
                Component.translatable("gui.herobrines_world.smart_chip.me"),
                b -> {
                    if (minecraft.player != null) {
                        owner = minecraft.player.getUUID();
                        ownerBox.setValue(owner.toString());
                    }
                }
        ).bounds(this.leftPos + 135, row(2), 4, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.herobrines_world.smart_chip.save_access"), b -> saveAccess())
                .bounds(this.leftPos + 10, row(4), 160, 20).build());
    }

    private void saveAll() {
        int range = parseBox(rangeBox);
        int speed = parseBox(speedBox);
        int accessTier = parseBox(tierBox);
        UUID owner = parseBoxOwner(ownerBox);

        ClientPacketDistributor.sendToServer(new SyncChipPacket(transferMode, range, speed, accessMode, accessTier, owner));
    }

    public void addNumberLimiter(@NotNull EditBox box, int min, int max) {
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

    private boolean onlyDigits(@NotNull String s) {
        if (s.isEmpty()) return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    private int parseBox(@NotNull EditBox box) {
        try {
            return Integer.parseInt(box.getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private @Nullable UUID parseBoxOwner(EditBox box) {
        try {
            return UUID.fromString(box.getValue());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void adjustBox(@NotNull EditBox box, int delta, int max) {
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

    private void saveCurrentInputs() {
        if (ownerBox != null) {
            owner = parseBoxOwner(ownerBox);
        }

        if (tierBox != null) {
            level = parseBox(tierBox);
        }

        if (rangeBox != null) {
            range = parseBox(rangeBox);
        }

        if (speedBox != null) {
            speed = parseBox(speedBox);
        }
    }

    private void updateTransferButtons() {
        modeButton.setMessage(Component.translatable(
                "gui.herobrines_world.smart_chip.transfer_mode",
                Component.translatable(
                        "gui.herobrines_world.smart_chip.transfer_mode." + transferMode.id().getPath()
                )));
    }

    private void updateAccessButtons() {
        accessButton.setMessage(Component.translatable(
                "gui.herobrines_world.smart_chip.access_mode",
                Component.translatable(
                        "gui.herobrines_world.smart_chip.access_mode." + accessMode.id().getPath()
                )));
    }

    private void saveTransfer() {
        saveAll();
    }

    private void saveAccess() {
        saveAll();
    }

    private TransferMode nextMode(TransferMode current) {

        List<TransferMode> modes =
                new ArrayList<>(HWRegistries.TRANSFER_MODES.values());

        int index = modes.indexOf(current);

        if (index == -1) {
            return modes.getFirst();
        }

        return modes.get((index + 1) % modes.size());
    }

    private AccessMode nextAccess(AccessMode current) {
        List<AccessMode> modes =
                new ArrayList<>(HWRegistries.ACCESS_MODES.values());

        int index = modes.indexOf(current);

        if (index == -1) {
            return modes.getFirst();
        }

        return modes.get((index + 1) % modes.size());
    }

    private boolean isValidUuidInput(@NotNull String text) {
        return text.length() <= 36 &&
                text.matches("[0-9a-fA-F\\-]*");
    }

    private int row(int index) {
        return this.topPos + 35 + (index * 25);
    }

    @Override
    protected void renderBg(@NonNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
    }

    @Override
    public void render(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderLabels(@NonNull GuiGraphics graphics, int mouseX, int mouseY) {
    }
}