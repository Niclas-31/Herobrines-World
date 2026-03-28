package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.components.ModDataComponents;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BatteryItem extends Item implements TooltipProvider {

    private static final int MAX_ENERGY = 1000;

    public BatteryItem(Properties properties) {
        super(properties);
    }

    public static int getEnergy(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.ENERGY.get(), 0);
    }

    public static void setEnergy(ItemStack stack, int energy) {
        energy = Math.clamp(energy, 0, MAX_ENERGY);
        stack.set(ModDataComponents.ENERGY.get(), energy);
    }

    @Override
    public void addToTooltip(@NotNull TooltipContext context, @NotNull Consumer<Component> consumer, @NotNull TooltipFlag tooltip, @NotNull DataComponentGetter data) {
        int energy = data.getOrDefault(ModDataComponents.ENERGY, 0);
        consumer.accept(Component.literal("Energie: " + energy + " / " + BatteryItem.MAX_ENERGY));
    }
}