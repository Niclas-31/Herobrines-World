package de.niclasl.herobrines_world.common.registries.menus;

import de.niclasl.herobrines_world.common.registries.items.custom.KeyCard;
import de.niclasl.herobrines_world.common.registries.items.custom.SmartChip;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class CardReaderMenu extends AbstractContainerMenu {

    private final Container container;

    public CardReaderMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, new SimpleContainer(2));
    }

    public CardReaderMenu(int containerId, Inventory inventory, Container container) {
        super(ModMenuTypes.CARD_READER.get(), containerId);

        this.container = container;

        this.addSlot(new Slot(container, 0, 44, 36) {
            @Override
            public boolean mayPlace(@NonNull ItemStack stack) {
                return stack.getItem() instanceof SmartChip;
            }
        });

        this.addSlot(new Slot(container, 1, 116, 36) {
            @Override
            public boolean mayPlace(@NonNull ItemStack stack) {
                return stack.getItem() instanceof KeyCard;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + row * 18, 84 + col * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return this.container.stillValid(player);
    }
}