package de.niclasl.herobrines_world.potion.custom;

import net.neoforged.neoforge.common.NeoForgeMod;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import de.niclasl.herobrines_world.HerobrinesWorld;

import java.util.Objects;

public class GoodHerobrine extends MobEffect {
	public GoodHerobrine() {
		super(MobEffectCategory.BENEFICIAL, -3407872);
		this.withSoundOnAdded(Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("item.bottle.empty"))));
		this.addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_0"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_1"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_2"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_3"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_4"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.FLYING_SPEED, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_5"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_6"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.LUCK, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_7"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_8"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MINING_EFFICIENCY, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_9"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MOVEMENT_EFFICIENCY, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_10"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_11"), 0.05, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.OXYGEN_BONUS, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_12"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.SNEAKING_SPEED, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_13"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_14"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.WATER_MOVEMENT_EFFICIENCY, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_15"), 0.5, AttributeModifier.Operation.ADD_VALUE);
	}
}