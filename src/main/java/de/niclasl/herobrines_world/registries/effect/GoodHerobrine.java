package de.niclasl.herobrines_world.registries.effect;

import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.NeoForgeMod;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.Identifier;

import de.niclasl.herobrines_world.HerobrinesWorld;

public class GoodHerobrine extends MobEffect {
	public GoodHerobrine(MobEffectCategory category) {
		super(category, -3407872);

		this.withSoundOnAdded(SoundEvents.BOTTLE_EMPTY);
		this.addAttributeModifier(Attributes.ARMOR, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_0"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_1"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_2"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_3"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_4"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.FLYING_SPEED, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_5"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_6"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.LUCK, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_7"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MAX_HEALTH, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_8"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MINING_EFFICIENCY, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_9"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MOVEMENT_EFFICIENCY, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_10"), 0.1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_11"), 0.05, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.OXYGEN_BONUS, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_12"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.SNEAKING_SPEED, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_13"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_14"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.WATER_MOVEMENT_EFFICIENCY, Identifier.fromNamespaceAndPath(HerobrinesWorld.MODID, "effect.good_herobrine_effect_15"), 0.5, AttributeModifier.Operation.ADD_VALUE);
	}
}