package de.niclasl.herobrines_world.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class OreDetector extends Item {

	public OreDetector(Item.Properties properties) {
		super(properties
				.durability(100)
				.fireResistant()
				.enchantable(1)
				.attributes(ItemAttributeModifiers.builder()
						.add(Attributes.ATTACK_DAMAGE,
								new AttributeModifier(BASE_ATTACK_DAMAGE_ID, -1,
										AttributeModifier.Operation.ADD_VALUE),
								EquipmentSlotGroup.MAINHAND)
						.add(Attributes.ATTACK_SPEED,
								new AttributeModifier(BASE_ATTACK_SPEED_ID, -4,
										AttributeModifier.Operation.ADD_VALUE),
								EquipmentSlotGroup.MAINHAND)
						.build()));
	}

	@Override
	public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
		return 1.0F;
	}

	@Override
	public boolean mineBlock(
			ItemStack stack,
			@NotNull Level level,
			@NotNull BlockState state,
			@NotNull BlockPos pos,
			@NotNull LivingEntity entity
	) {
		stack.hurtAndBreak(
				1,
				entity,
				entity.getUsedItemHand() == InteractionHand.MAIN_HAND
						? EquipmentSlot.MAINHAND
						: EquipmentSlot.OFFHAND
		);
		return true;
	}

	@Override
	public void hurtEnemy(
			ItemStack stack,
			@NotNull LivingEntity target,
			@NotNull LivingEntity attacker
	) {
		stack.hurtAndBreak(
				2,
				attacker,
				attacker.getUsedItemHand() == InteractionHand.MAIN_HAND
						? EquipmentSlot.MAINHAND
						: EquipmentSlot.OFFHAND
		);
	}

	@Override
	public @NotNull InteractionResult use(
			@NotNull Level level,
			@NotNull Player entity,
			@NotNull InteractionHand hand
	) {
		InteractionResult result = super.use(level, entity, hand);
		if (entity.isShiftKeyDown()) {
			if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Coal")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Copper";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §cCopper"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Copper")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Diamond";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §bDiamond"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Diamond")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Emerald";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §2Emerald"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Emerald")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Gold";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §6Gold"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Gold")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Iron";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §8Iron"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Iron")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Lapis";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §1Lapis"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Lapis")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Quartz";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §7Quartz"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Quartz")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Redstone";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §4Redstone"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Redstone")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Ancient_Debris";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §eAncient Debris"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ancient_Debris")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Toxenium";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §eToxenium"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Toxenium")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Ash";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §7Ash"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ash")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Herobrine";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §0Herobrine"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Herobrine")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Frozen";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §9Frozen"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Frozen")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Green";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §aGreen"), false);
			} else if ((entity.getItemInHand(hand).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Green")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Coal";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §0Coal"), false);
			} else {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Coal";
					CustomData.update(DataComponents.CUSTOM_DATA, entity.getItemInHand(hand), tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §0Coal"), false);
			}
		}
		return result;
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
		super.useOn(context);
		
		rightClickedOnBlock(
				context.getLevel(),
				context.getClickedPos().getX(),
				context.getClickedPos().getY(),
				context.getClickedPos().getZ(),
				context.getPlayer(),
				context.getItemInHand());

		return InteractionResult.SUCCESS;
	}
	
	private void rightClickedOnBlock(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double posY;
		String namespace;
		namespace = "neoforge";
		if (!entity.isShiftKeyDown()) {
			posY = y;
			if (posY > -64) {
				for (int index0 = 0; index0 < 256; index0++) {
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Coal")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/coal")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Copper")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/copper")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Diamond")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/diamond")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Emerald")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/emerald")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Gold")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/gold")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Iron")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/iron")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Lapis")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/lapis")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Quartz")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/quartz")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Redstone")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/redstone")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ancient_Debris")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/ancient_debris")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Toxenium")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/toxenium")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ash")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/ash")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Herobrine")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/herobrine")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Frozen")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/frozen_heart")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Green")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/green")).toLowerCase(Locale.ENGLISH))))) {
						if (entity instanceof Player player && !player.level().isClientSide())
							player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					posY = posY - 1;
				}
				if (entity instanceof LivingEntity livingEntity)
					livingEntity.swing(InteractionHand.MAIN_HAND, true);
			}
		}
	}
}
