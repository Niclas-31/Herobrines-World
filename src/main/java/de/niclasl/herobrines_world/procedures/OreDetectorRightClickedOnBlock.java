package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

public class OreDetectorRightClickedOnBlock {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
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
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/coal")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Copper")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/copper")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Diamond")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/diamond")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Emerald")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/emerald")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Gold")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/gold")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Iron")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/iron")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Lapis")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/lapis")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Quartz")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/quartz")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Redstone")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/redstone")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ancient_Debris")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/ancient_debris")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Toxenium")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/toxenium")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ash")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/ash")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Herobrine")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/herobrine")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Frozen")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/frozen_heart")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Green")
							&& (world.getBlockState(BlockPos.containing(x, posY, z))).is(BlockTags.create(ResourceLocation.parse(((namespace + ":" + "ores/green")).toLowerCase(java.util.Locale.ENGLISH))))) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Metal Detector: §0Found ore!§f" + " X: " + x + " Y: " + posY + " Z: " + z)), false);
					}
					posY = posY - 1;
				}
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
			}
		}
	}
}