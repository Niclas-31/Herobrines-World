package de.niclasl.herobrines_world.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

import org.jetbrains.annotations.NotNull;

public class RuneStone extends Item {

	public RuneStone(Properties properties) {
		super(properties.rarity(Rarity.RARE).stacksTo(1));
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level world,
										  @NotNull Player player,
										  @NotNull InteractionHand hand) {

		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		}

		var stack = player.getItemInHand(hand);

		var tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

		boolean hasSaved = tag.getBooleanOr("hasSavedPosition", false);
		
		if (player.isShiftKeyDown()) {

			if (hasSaved) {
				player.displayClientMessage(
						Component.literal("§4A position has already been saved on this item!"),
						true
				);
				return InteractionResult.SUCCESS;
			}

			CustomData.update(DataComponents.CUSTOM_DATA, stack, data -> {
				data.putDouble("savedX", player.getX());
				data.putDouble("savedY", player.getY());
				data.putDouble("savedZ", player.getZ());
				data.putString("savedDimension",
						player.level().dimension().location().toString());
				data.putBoolean("hasSavedPosition", true);
			});

			player.displayClientMessage(
					Component.literal("§2Position was successfully saved in the item!"),
					true
			);

			return InteractionResult.SUCCESS;
		}

		if (!hasSaved) {
			player.displayClientMessage(
					Component.literal("§4No position has been saved yet."),
					true
			);
			return InteractionResult.SUCCESS;
		}

		String savedDimension = tag.getStringOr("savedDimension", "");
		String currentDimension =
				player.level().dimension().location().toString();

		if (!currentDimension.equals(savedDimension)) {
			player.displayClientMessage(
					Component.literal("§4You cannot teleport to another dimension!"),
					true
			);
			return InteractionResult.SUCCESS;
		}

		double x = tag.getDoubleOr("savedX", 0);
		double y = tag.getDoubleOr("savedY", 0);
		double z = tag.getDoubleOr("savedZ", 0);

		if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.connection.teleport(
					x, y, z,
					player.getYRot(),
					player.getXRot()
			);
		}

		return InteractionResult.SUCCESS;
	}
}
