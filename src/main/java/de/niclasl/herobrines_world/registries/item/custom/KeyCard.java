package de.niclasl.herobrines_world.registries.item.custom;

import de.niclasl.herobrines_world.registries.components.KeyCardData;
import de.niclasl.herobrines_world.registries.components.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class KeyCard extends Item {

    public KeyCard(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NonNull ItemStack stack, @NonNull TooltipContext context,
                                @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> tooltipAdder,
                                @NonNull TooltipFlag flag) {
        KeyCardData data = stack.get(ModDataComponents.KEY_CARD_DATA);

        if (data != null) {
            tooltipAdder.accept(Component.translatable("item.herobrines_world.key_card.owner", data.owner().toString()));
            tooltipAdder.accept(Component.translatable("item.herobrines_world.key_card.level", data.level()));
        }
    }
}