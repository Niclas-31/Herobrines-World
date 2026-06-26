package de.niclasl.herobrines_world.client.screen;

import de.niclasl.herobrines_world.common.network.message.SyncChipPacket;
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
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class SmartChipScreen extends AbstractContainerScreen<SmartChipMenu> {

    private enum Tab {
        TRANSFER,
        ACCESS
    }

    private enum TransferRow {
        MODE,
        RANGE,
        SPEED,
        KEEP_AMOUNT,
        VOID_TRASH
    }

    private Tab currentTab = Tab.TRANSFER;

    private static final int VISIBLE_ROWS = 4;
    private int scrollOffset = 0;

    private TransferMode transferMode;
    private int range;
    private int speed;
    private int keepAmount;
    private boolean voidTrash;

    private AccessMode accessMode;
    private UUID owner;
    private int level;

    private Button modeButton;
    private EditBox rangeBox;
    private EditBox speedBox;
    private EditBox keepAmountBox;
    private Button voidTrashButton;

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
        keepAmount = transfer.keepAmount();

        voidTrash = transfer.voidTrash();

        var access = menu.getAccessData();

        accessMode = access.mode();

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

        addRenderableWidget(Button.builder(
                Component.translatable("gui.herobrines_world.smart_chip.access"),
                b -> {
                    saveCurrentInputs();
                    currentTab = Tab.ACCESS;
                    rebuildTab();
                }
        ).bounds(this.leftPos + half, this.topPos, half, 20).build());

        switch (currentTab) {
            case TRANSFER -> buildTransferTab();
            case ACCESS -> buildAccessTab();
        }
    }

    private void buildTransferTab() {
        List<TransferRow> rows = getRows();

        int start = scrollOffset;
        int end = Math.min(rows.size(), start + VISIBLE_ROWS);

        int visibleIndex = 0;

        for (int i = start; i < end; i++) {
            TransferRow row = rows.get(i);

            int y = this.topPos + 35 + visibleIndex * 25;

            renderRow(row, y);

            visibleIndex++;
        }

        buildSaveButton();
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
        ).bounds(this.leftPos + 135, row(2), 40, 20).build());

        buildSaveButton();
    }

    private void renderRow(TransferRow row, int y) {
        switch (row) {
            case MODE -> modeButton = addRenderableWidget(Button.builder(
                    Component.translatable("gui.herobrines_world.smart_chip.transfer_mode",
                            Component.translatable(
                                    "gui.herobrines_world.smart_chip.transfer_mode." + transferMode.id().getPath())),
                    b -> {
                        transferMode = nextMode(transferMode);
                        updateTransferButtons();
                    }
            ).bounds(this.leftPos + 10, y, 160, 20).build());
            case RANGE -> {
                addRenderableWidget(Button.builder(Component.literal("-"), b -> adjustBox(rangeBox, -1, 16))
                        .bounds(this.leftPos + 10, y, 20, 20).build());
                rangeBox = new EditBox(this.font, this.leftPos + 35, y, 80, 20, Component.literal("Range"));
                rangeBox.setValue(String.valueOf(range));
                addNumberLimiter(rangeBox, 4, 16);
                addRenderableWidget(rangeBox);
                addRenderableWidget(Button.builder(Component.literal("+"), b -> adjustBox(rangeBox, 1, 16))
                        .bounds(this.leftPos + 120, y, 20, 20).build());
            }
            case SPEED -> {
                addRenderableWidget(Button.builder(Component.literal("-"), b -> adjustBox(speedBox, -1, 2))
                        .bounds(this.leftPos + 10, y, 20, 20).build());
                speedBox = new EditBox(this.font, this.leftPos + 35, y, 80, 20, Component.literal("Speed"));
                speedBox.setValue(String.valueOf(speed));
                addNumberLimiter(speedBox, 0, 2);
                addRenderableWidget(speedBox);
                addRenderableWidget(Button.builder(Component.literal("+"), b -> adjustBox(speedBox, 1, 2))
                        .bounds(this.leftPos + 120, y, 20, 20).build());
            }
            case KEEP_AMOUNT -> {
                addRenderableWidget(Button.builder(Component.literal("-"), b -> adjustBox(keepAmountBox, -1, 64))
                        .bounds(this.leftPos + 10, y, 20, 20).build());
                keepAmountBox = new EditBox(this.font, this.leftPos + 35, y, 80, 20, Component.literal("KeepAmount"));
                keepAmountBox.setValue(String.valueOf(keepAmount));
                addNumberLimiter(keepAmountBox, 1, 64);
                addRenderableWidget(keepAmountBox);
                addRenderableWidget(Button.builder(Component.literal("+"), b -> adjustBox(keepAmountBox, 1, 64))
                        .bounds(this.leftPos + 120, y, 20, 20).build());
            }
            case VOID_TRASH -> voidTrashButton = addRenderableWidget(Button.builder(
                    Component.translatable("gui.herobrines_world.smart_chip.transfer.void_trash",
                            voidTrash),
                    b -> {
                        voidTrash = !voidTrash;
                        updateTransferButtons();
                    }
            ).bounds(this.leftPos + 10, y, 160, 20).build());
        }
    }

    private void buildSaveButton() {
        addRenderableWidget(Button.builder(Component.translatable("gui.herobrines_world.smart_chip.save"), b -> saveAll())
                .bounds(this.leftPos + 10, this.topPos + 135, 160, 20).build());
    }

    private void saveAll() {
        switch (currentTab) {
            case TRANSFER -> saveTransfer();
            case ACCESS -> saveAccess();
        }
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

    private int parseBox(EditBox box) {
        if (box == null) {
            throw new IllegalStateException("Eine EditBox wurde nicht initialisiert.");
        }

        try {
            return Integer.parseInt(box.getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private @Nullable UUID parseBoxOwner(@NotNull EditBox box) {
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

        if (keepAmountBox != null) {
            keepAmount = parseBox(keepAmountBox);
        }
    }

    private void updateTransferButtons() {
        modeButton.setMessage(Component.translatable(
                "gui.herobrines_world.smart_chip.transfer_mode",
                Component.translatable(
                        "gui.herobrines_world.smart_chip.transfer_mode." + transferMode.id().getPath()
                )));
        voidTrashButton.setMessage(Component.translatable(
                "gui.herobrines_world.smart_chip.transfer.void_trash", voidTrash));
    }

    private void updateAccessButtons() {
        accessButton.setMessage(Component.translatable(
                "gui.herobrines_world.smart_chip.access_mode",
                Component.translatable(
                        "gui.herobrines_world.smart_chip.access_mode." + accessMode.id().getPath()
                )));
    }

    private void saveTransfer() {
        int range = parseBox(rangeBox);
        int speed = parseBox(speedBox);
        int keepAmount = parseBox(keepAmountBox);

        ClientPacketDistributor.sendToServer(
                new SyncChipPacket(
                        transferMode,
                        range,
                        speed,
                        accessMode,
                        level,
                        owner,
                        keepAmount,
                        voidTrash
                )
        );
    }

    private void saveAccess() {
        int level = parseBox(tierBox);
        UUID owner = parseBoxOwner(ownerBox);

        ClientPacketDistributor.sendToServer(
                new SyncChipPacket(
                        transferMode,
                        range,
                        speed,
                        accessMode,
                        level,
                        owner,
                        keepAmount,
                        voidTrash
                )
        );
    }

    private TransferMode nextMode(TransferMode current) {
        List<TransferMode> modes =
                new ArrayList<>(HWRegistries.TRANSFER_MODES.values());

        modes.sort(Comparator.comparingInt(TransferMode::priority));

        int index = modes.indexOf(current);

        if (index == -1) {
            return modes.getFirst();
        }

        return modes.get((index + 1) % modes.size());
    }

    private AccessMode nextAccess(AccessMode current) {
        List<AccessMode> modes =
                new ArrayList<>(HWRegistries.ACCESS_MODES.values());

        modes.sort(Comparator.comparingInt(AccessMode::priority));

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

    private List<TransferRow> getRows() {
        return List.of(
                TransferRow.MODE,
                TransferRow.RANGE,
                TransferRow.SPEED,
                TransferRow.KEEP_AMOUNT,
                TransferRow.VOID_TRASH
        );
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (currentTab != Tab.TRANSFER) return false;

        if (scrollY > 0) {
            scrollOffset--;
        } else if (scrollY < 0) {
            scrollOffset++;
        }

        scrollOffset = Math.clamp(scrollOffset, 0, Math.max(0, getRows().size() - VISIBLE_ROWS));
        rebuildTab();
        return true;
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