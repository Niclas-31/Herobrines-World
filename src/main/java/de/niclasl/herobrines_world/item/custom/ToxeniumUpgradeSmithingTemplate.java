package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ToxeniumUpgradeSmithingTemplate extends Item {
	public ToxeniumUpgradeSmithingTemplate(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC).fireResistant());
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemstack, Item.@NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> componentConsumer, @NotNull TooltipFlag flag) {
		super.appendHoverText(itemstack, context, tooltipDisplay, componentConsumer, flag);
		componentConsumer.accept(Component.translatable("item.herobrines_world.toxenium_upgrade_smithing_template.description_0"));
		componentConsumer.accept(Component.translatable("item.herobrines_world.toxenium_upgrade_smithing_template.description_1"));
		componentConsumer.accept(Component.translatable("item.herobrines_world.toxenium_upgrade_smithing_template.description_2"));
		componentConsumer.accept(Component.translatable("item.herobrines_world.toxenium_upgrade_smithing_template.description_3"));
		componentConsumer.accept(Component.translatable("item.herobrines_world.toxenium_upgrade_smithing_template.description_4"));
		componentConsumer.accept(Component.translatable("item.herobrines_world.toxenium_upgrade_smithing_template.description_5"));
	}
}