package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ToxeniumHoe extends HoeItem {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
			10000,
			12f,
			0,
			20,
			TagKey.create(
					Registries.ITEM,
					ResourceLocation.parse("herobrines_world:toxenium_hoe_repair_items")));

	public ToxeniumHoe(Item.Properties properties) {
		super(TOOL_MATERIAL, 6f, -2.4f, properties.fireResistant());
	}

	@Override
	public void inventoryTick(@NotNull ItemStack itemstack, @NotNull ServerLevel world, @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		
		if (!((entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_HELMET.get()
				&& (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_CHESTPLATE.get()
				&& (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_LEGGINGS.get()
				&& (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_BOOTS.get())) {
			if (entity instanceof LivingEntity entity1 && !entity1.level().isClientSide())
				entity1.addEffect(new MobEffectInstance(ModMobEffects.RADIO_ACTIVE, 40, 0, false, false));
		}
	}
}
