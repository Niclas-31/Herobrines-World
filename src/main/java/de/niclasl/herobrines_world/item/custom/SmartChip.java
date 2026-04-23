package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.components.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SmartChip extends Item {

    public SmartChip(Properties properties) {
        super(properties);
    }

    public Integer getMachineUpgradeLevel(ItemStack stack) {
        return stack.get(ModDataComponents.MACHINE_UPGRADE_LEVEL.get());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        Integer machineUpgradeLevelComponent = stack.get(ModDataComponents.MACHINE_UPGRADE_LEVEL.get());
        int machineUpgradeLevel = machineUpgradeLevelComponent != null ? machineUpgradeLevelComponent : 0;

        tooltipAdder.accept(Component.translatable("hover.herobrines_world.smart_chip", machineUpgradeLevel));
    }
}
