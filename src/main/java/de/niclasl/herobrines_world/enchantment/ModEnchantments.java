package de.niclasl.herobrines_world.enchantment;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.enchantment.custom.HeartOfTheForestEffect;
import de.niclasl.herobrines_world.enchantment.custom.HerobrineEnchantmentEffect;
import de.niclasl.herobrines_world.enchantment.custom.RootsOfTheEarthEffect;
import de.niclasl.herobrines_world.enchantment.custom.WildernessAcumenEffect;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.Enchantments;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> HEROBRINE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "herobrine"));
    public static final ResourceKey<Enchantment> MORE_SOULS = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "more_souls"));
    public static final ResourceKey<Enchantment> HEART_OF_THE_FOREST = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "heart_of_the_forest"));
    public static final ResourceKey<Enchantment> ROOTS_OF_THE_EARTH = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "roots_of_the_earth"));
    public static final ResourceKey<Enchantment> WILDERNESS_ACUMEN =  ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "wilderness_acumen"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        var bindingCurse = enchantments.getOrThrow(Enchantments.BINDING_CURSE);
        var vanishingCurse = enchantments.getOrThrow(Enchantments.VANISHING_CURSE);

        register(context, HEROBRINE, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        5,
                        4,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(30, 10),
                        1,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new HerobrineEnchantmentEffect()));

        register(context, MORE_SOULS, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                8,
                2,
                Enchantment.dynamicCost(5, 10),
                Enchantment.dynamicCost(20, 10),
                1,
                EquipmentSlotGroup.MAINHAND)));

        register(context, HEART_OF_THE_FOREST, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                4,
                2,
                Enchantment.dynamicCost(20, 15),
                Enchantment.dynamicCost(40, 15),
                1,
                EquipmentSlotGroup.CHEST))
                .exclusiveWith(HolderSet.direct(bindingCurse, vanishingCurse))
                .withEffect(EnchantmentEffectComponents.TICK, new HeartOfTheForestEffect()));

        register(context, ROOTS_OF_THE_EARTH, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                6,
                4,
                Enchantment.dynamicCost(8, 10),
                Enchantment.dynamicCost(25, 10),
                1,
                EquipmentSlotGroup.LEGS))
                .exclusiveWith(HolderSet.direct(bindingCurse, vanishingCurse))
                .withEffect(EnchantmentEffectComponents.TICK, new RootsOfTheEarthEffect()));

        register(context, WILDERNESS_ACUMEN, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                7,
                3,
                Enchantment.dynamicCost(10, 12),
                Enchantment.dynamicCost(30, 12),
                1,
                EquipmentSlotGroup.HEAD))
                .exclusiveWith(HolderSet.direct(bindingCurse, vanishingCurse))
                .withEffect(EnchantmentEffectComponents.TICK, new WildernessAcumenEffect()));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}