package de.niclasl.herobrines_world.common.registries.items.custom;

import de.niclasl.herobrines_world.Config;
import de.niclasl.herobrines_world.common.network.ModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FrozenHeart extends Item {
	public FrozenHeart(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

		if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;

		if (!isThreeHeartsEnabled(serverPlayer)) {
			if (!level.isClientSide()) {
				player.displayClientMessage(
						Component.translatable("herobrines_world.configuration.three_hearts.disabled"),
						true
				);
			}
			return InteractionResult.SUCCESS;
		}

		if (vars.Hearts >= 3) {
			if (!level.isClientSide()) {
				player.displayClientMessage(
						Component.translatable("item.herobrines_world.frozen_heart.not_more_hearts"),
						true
				);
			}
			return InteractionResult.SUCCESS;
		}

		player.getItemInHand(hand).shrink(1);

		vars.Hearts = Math.min(3, vars.Hearts + 1);

		if (player instanceof ServerPlayer sp) {
			vars.markSyncDirty(sp);
		}

		return InteractionResult.SUCCESS;
	}

	private static boolean isThreeHeartsEnabled(ServerPlayer player) {
		ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);

		if (player.level().getLevelData().isHardcore()) return false;

        if (player.level().getServer().isDedicatedServer()) {
			return vars.ThreeHearts;
		}

		return Config.THREE_HEARTS.getAsBoolean() && vars.ThreeHearts;
	}
}