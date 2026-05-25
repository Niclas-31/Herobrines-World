package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.common.registries.menu.SmartChipMenu;
import de.niclasl.herobrines_world.common.network.message.SyncChipPacket;
import de.niclasl.herobrines_world.common.network.safety.AccessMode;
import de.niclasl.herobrines_world.common.network.transfer.TransferMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public class SmartChipScreen extends AbstractContainerScreen<SmartChipMenu> {

    private enum Tab {
        TRANSFER,
        ACCESS
    }

    private Tab currentTab = Tab.TRANSFER;

    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 64;
    private static final int MIN_SPEED = 0;
    private static final int MAX_SPEED = 2;
    private static final int MIN_ACCESS_TIER = 0;
    private static final int MAX_ACCESS_TIER = 10;

    private static final int START_Y = 35;
    private static final int LINE_HEIGHT = 25;

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
        speed = transfer.speed();
        range = transfer.range();

        var access = menu.getAccessData();

        accessMode = access.mode();
        owner = access.owner();
        level = access.level();
    }

    private void rebuildTab() {
        clearWidgets();

        int half = imageWidth / 2;

        addRenderableWidget(Button.builder(
                Component.literal("Transfer"),
                b -> {
                    saveCurrentInputs();
                    currentTab = Tab.TRANSFER;
                    rebuildTab();
                }
        ).bounds(leftPos, topPos, half, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Access"),
                b -> {
                    saveCurrentInputs();
                    currentTab = Tab.ACCESS;
                    rebuildTab();
                }
        ).bounds(leftPos + half, topPos, half, 20).build());

        switch (currentTab) {

            case TRANSFER -> buildTransferTab();

            case ACCESS -> buildAccessTab();
        }
    }

    private void buildTransferTab() {
        modeButton = addRenderableWidget(Button.builder(
                Component.literal("Mode: " + transferMode),
                b -> {
                    transferMode = nextMode(transferMode);
                    updateTransferButtons();
                }
        ).bounds(leftPos + 10, row(0), 160, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("-"),
                b -> adjustBox(rangeBox, -1, MAX_RANGE)
        ).bounds(leftPos + 10, row(1), 20, 20).build());

        rangeBox = new EditBox(
                font,
                leftPos + 35,
                row(1),
                80,
                20,
                Component.literal("Range")
        );

        rangeBox.setValue(String.valueOf(range));
        addRenderableWidget(rangeBox);

        addRenderableWidget(Button.builder(
                Component.literal("+"),
                b -> adjustBox(rangeBox, 1, MAX_RANGE)
        ).bounds(leftPos + 120, row(1), 20, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("-"),
                b -> adjustBox(speedBox, -1, MAX_SPEED)
        ).bounds(leftPos + 10, row(2), 20, 20).build());

        speedBox = new EditBox(
                font,
                leftPos + 35,
                row(2),
                80,
                20,
                Component.literal("Speed")
        );

        speedBox.setValue(String.valueOf(speed));
        addRenderableWidget(speedBox);

        addRenderableWidget(Button.builder(
                Component.literal("+"),
                b -> adjustBox(speedBox, 1, MAX_SPEED)
        ).bounds(leftPos + 120, row(2), 20, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Save Transfer"),
                b -> saveTransfer()
        ).bounds(leftPos + 10, row(4), 160, 20).build());
    }

    private void buildAccessTab() {
        accessButton = addRenderableWidget(Button.builder(
                Component.literal("Access: " + accessMode),
                b -> {
                    accessMode = nextAccess(accessMode);
                    updateAccessButtons();
                }
        ).bounds(leftPos + 10, row(0), 160, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("-"),
                b -> adjustBox(tierBox, -1, MAX_ACCESS_TIER)
        ).bounds(leftPos + 10, row(1), 20, 20).build());

        tierBox = new EditBox(
                font,
                leftPos + 35,
                row(1),
                80,
                20,
                Component.literal("Tier")
        );

        tierBox.setValue(String.valueOf(level));
        addRenderableWidget(tierBox);

        addRenderableWidget(Button.builder(
                Component.literal("+"),
                b -> adjustBox(tierBox, 1, MAX_ACCESS_TIER)
        ).bounds(leftPos + 120, row(1), 20, 20).build());

        ownerBox = new EditBox(
                font,
                leftPos + 10,
                row(2),
                120,
                20,
                Component.literal("Owner UUID")
        );

        ownerBox.setMaxLength(36);

        ownerBox.setValue(owner == null ? "" : owner.toString());

        ownerBox.setFilter(this::isValidUuidInput);

        addRenderableWidget(ownerBox);

        addRenderableWidget(Button.builder(
                Component.literal("Me"),
                b -> {
                    if (minecraft.player != null) {
                        owner = minecraft.player.getUUID();
                        ownerBox.setValue(owner.toString());
                    }
                }
        ).bounds(leftPos + 135, row(2), 40, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Save Access"),
                b -> saveAccess()
        ).bounds(leftPos + 10, row(4), 160, 20).build());
    }

    private void saveCurrentInputs() {
        if (ownerBox != null) {
            owner = parseOwner();
        }

        if (tierBox != null) {
            level = parse(tierBox.getValue(),
                    MIN_ACCESS_TIER,
                    MAX_ACCESS_TIER);
        }

        if (rangeBox != null) {
            range = parse(rangeBox.getValue(),
                    MIN_RANGE,
                    MAX_RANGE);
        }

        if (speedBox != null) {
            speed = parse(speedBox.getValue(),
                    MIN_SPEED,
                    MAX_SPEED);
        }
    }

    private int parse(String text, int min, int max) {
        try {
            return Math.clamp(Integer.parseInt(text), min, max);
        } catch (NumberFormatException e) {
            return min;
        }
    }

    private UUID parseOwner() {
        String value = ownerBox.getValue();

        assert minecraft.player != null;

        if (value.isBlank()) {
            return minecraft.player.getUUID();
        }

        try {
            return UUID.fromString(value.trim());
        } catch (IllegalArgumentException e) {
            return minecraft.player.getUUID();
        }
    }

    private void updateTransferButtons() {
        modeButton.setMessage(Component.literal("Mode: " + transferMode));
    }

    private void updateAccessButtons() {
        accessButton.setMessage(Component.literal("Access: " + accessMode));
    }

    private void saveTransfer() {
        saveAll();
    }

    private void saveAccess() {
        saveAll();
    }

    private void saveAll() {
        int parsedRange = range;
        int parsedSpeed = speed;
        int parsedLevel = level;
        UUID parsedOwner = owner;

        if (rangeBox != null) {
            parsedRange = parse(rangeBox.getValue(), MIN_RANGE, MAX_RANGE);
        }

        if (speedBox != null) {
            parsedSpeed = parse(speedBox.getValue(), MIN_SPEED, MAX_SPEED);
        }

        if (tierBox != null) {
            parsedLevel = parse(tierBox.getValue(),
                    MIN_ACCESS_TIER,
                    MAX_ACCESS_TIER);
        }

        if (ownerBox != null) {
            parsedOwner = parseOwner();
        }

        range = parsedRange;
        speed = parsedSpeed;
        level = parsedLevel;
        owner = parsedOwner;

        ClientPacketDistributor.sendToServer(
                new SyncChipPacket(
                        transferMode,
                        parsedRange,
                        parsedSpeed,
                        accessMode,
                        parsedOwner,
                        parsedLevel
                )
        );
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

    private TransferMode nextMode(TransferMode mode) {
        return switch (mode) {

            case INSERT -> TransferMode.EXTRACT;

            case EXTRACT -> TransferMode.BALANCE;

            case BALANCE -> TransferMode.INSERT;
        };
    }

    private AccessMode nextAccess(AccessMode level) {
        return switch (level) {

            case PUBLIC -> AccessMode.PRIVATE;

            case PRIVATE -> AccessMode.TRUSTED;

            case TRUSTED -> AccessMode.OWNER_ONLY;

            case OWNER_ONLY -> AccessMode.PUBLIC;
        };
    }

    private boolean isValidUuidInput(String text) {
        return text.length() <= 36 &&
                text.matches("[0-9a-fA-F\\-]*");
    }

    private int row(int index) {
        return topPos + START_Y + (index * LINE_HEIGHT);
    }

    @Override
    public void render(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

        renderBackground(graphics, mouseX, mouseY, partialTick);

        super.render(graphics, mouseX, mouseY, partialTick);

        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NonNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
    }

    @Override
    protected void renderLabels(@NonNull GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(
                font,
                title,
                8,
                6,
                0xFFFFFF,
                false
        );
    }
}