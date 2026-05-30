package de.niclasl.herobrines_world.common.registries.item.custom;

import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.RuneData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

@Deprecated(since = "1.7.2", forRemoval = true)
public class RuneStone extends Item {

	public RuneStone(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level world,
										  @NotNull Player player,
										  @NotNull InteractionHand hand) {

		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		}

		var stack = player.getItemInHand(hand);

		var tag = stack.getOrDefault(ModDataComponents.RUNE_DATA, new RuneData(0, 0, 0, Level.OVERWORLD, false, 0, 0));

		boolean hasSaved = tag.saved();

		if (player.isShiftKeyDown()) {

			if (hasSaved) {
				player.displayClientMessage(
						Component.translatable("item.herobrines_world.rune_stone.already_saved"),
						true
				);
				return InteractionResult.SUCCESS;
			}

			stack.set(ModDataComponents.RUNE_DATA, new RuneData(
					player.getX(),
					player.getY(),
					player.getZ(),
					player.level().dimension(),
					true,
					player.getYRot(),
					player.getXRot()
			));

			System.out.println(player.level().dimension());

			player.displayClientMessage(
					Component.translatable("item.herobrines_world.rune_stone.position_saved"),
					true
			);

			return InteractionResult.SUCCESS;
		}

		if (!hasSaved) {
			player.displayClientMessage(
					Component.translatable("item.herobrines_world.rune_stone.no_position_saved"),
					true
			);
			return InteractionResult.SUCCESS;
		}

		ResourceKey<Level> savedDimension = tag.dimension();

		double x = tag.x();
		double y = tag.y();
		double z = tag.z();
		float yaw = tag.yaw();
		float pitch = tag.pitch();

		if (player instanceof ServerPlayer serverPlayer) {
			teleportToDimension(serverPlayer, savedDimension, x, y, z, yaw, pitch);
		}

		return InteractionResult.SUCCESS;
	}

	public static void teleportToDimension(ServerPlayer player, ResourceKey<Level> targetDim, double x, double y, double z, float yaw, float pitch) {
		MinecraftServer server = player.level().getServer();

        ServerLevel targetLevel = server.getLevel(targetDim);
		if (targetLevel == null) return;

		player.teleportTo(
				targetLevel,
				x,
				y,
				z,
				EnumSet.noneOf(Relative.class),
				yaw,
				pitch,
				true
		);
    }
}