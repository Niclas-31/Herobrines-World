package de.niclasl.herobrines_world.common.registries.items.custom;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.util.database.PlayerData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class FrozenHeart extends Item {
	public FrozenHeart(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

		if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        try {
            PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

			if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;

			if (!isThreeHeartsEnabled(serverPlayer)) {
				if (!level.isClientSide()) {
					serverPlayer.sendSystemMessage(Component.translatable("herobrines_world.configuration.three_hearts.disabled"), true);
				}
				return InteractionResult.SUCCESS;
			}

			if (data.hearts >= 3) {
				if (!level.isClientSide()) {
					serverPlayer.sendSystemMessage(Component.translatable("item.herobrines_world.frozen_heart.not_more_hearts"), true);
				}
				return InteractionResult.SUCCESS;
			}

			player.getItemInHand(hand).shrink(1);

			data.hearts = Math.min(3, data.hearts + 1);

			HerobrinesWorld.DATABASE.savePlayerData(data);
        } catch (SQLException e) {
			HerobrinesWorld.LOGGER.error(
					"Failed to update hearts state for {}",
					player.getUUID(),
					e
			);
        }

		return InteractionResult.SUCCESS;
	}

	private static boolean isThreeHeartsEnabled(ServerPlayer player) throws SQLException {
		PlayerData data = HerobrinesWorld.DATABASE.getPlayerData(player.getUUID());

		if (player.level().getLevelData().isHardcore()) return false;

		return data.threeHearts;
	}
}