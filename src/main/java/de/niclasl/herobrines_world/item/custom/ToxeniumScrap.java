package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.effect.ModEffects;
import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ToxeniumScrap extends Item {

    public ToxeniumScrap(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NonNull ItemStack stack, @NonNull ServerLevel level, @NonNull Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);

        if (!((entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_HELMET.get()
                && (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_CHESTPLATE.get()
                && (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_LEGGINGS.get()
                && (entity instanceof LivingEntity armor ? armor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == ModItems.TOXENIUM_BOOTS.get())) {
            if (entity instanceof LivingEntity entity1 && !entity1.level().isClientSide())
                entity1.addEffect(new MobEffectInstance(ModEffects.RADIO_ACTIVE, 40, 0, false, false));
        }
    }
}