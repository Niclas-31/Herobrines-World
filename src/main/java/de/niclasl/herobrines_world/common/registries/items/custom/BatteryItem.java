package de.niclasl.herobrines_world.common.registries.items.custom;

import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BatteryItem extends Item {

    public BatteryItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        int current = stack.getOrDefault(ModDataComponents.ENERGY.get(), 0);
        int max = 1000;

        tooltipAdder.accept(Component.translatable(
                "item.herobrines_world.battery.hover",
                current,
                max
        ).withStyle(ChatFormatting.GREEN));

        tooltipAdder.accept(Component.literal(
                getEnergyBar(current, max, 10)
        ).withStyle(ChatFormatting.DARK_GREEN));
    }

    public static String getEnergyBar(int current, int max, int length) {
        if (max <= 0) return "";

        float percent = (float) current / max;
        int filled = (int) (percent * length);

        StringBuilder bar = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }

        return bar.toString();
    }

    public int getEnergy(ItemStack stack) {
        Integer energy = stack.get(ModDataComponents.ENERGY.get());
        return energy != null ? energy : 0;
    }

    public void setEnergy(ItemStack stack, int value) {
        int clamped = Math.clamp(value, 0, 1000);
        stack.set(ModDataComponents.ENERGY.get(), clamped);
    }

    public void consumeEnergy(ItemStack stack, int amount) {
        int current = getEnergy(stack);
        setEnergy(stack, current - amount);
    }

    public void addEnergy(ItemStack stack, int amount) {
        int current = getEnergy(stack);
        setEnergy(stack, current + amount);
    }
}