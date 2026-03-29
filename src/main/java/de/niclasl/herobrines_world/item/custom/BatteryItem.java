package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.components.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BatteryItem extends Item {

    private static final int MAX_ENERGY = 1000;

    public BatteryItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        Integer energyComponent = stack.get(ModDataComponents.ENERGY.get());
        int energy = energyComponent != null ? energyComponent : 0;

        tooltipAdder.accept(Component.literal("Energy: " + energy + " / " + MAX_ENERGY));
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