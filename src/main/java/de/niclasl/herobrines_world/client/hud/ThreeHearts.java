package de.niclasl.herobrines_world.client.hud;

import de.niclasl.herobrines_world.common.network.ModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.util.Objects;

@EventBusSubscriber(Dist.CLIENT)
public class ThreeHearts {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {

		Player entity = Minecraft.getInstance().player;
		if (entity == null) return;

		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();

		ModVariables.PlayerVariables vars = entity.getData(ModVariables.PLAYER_VARIABLES);

		boolean gamemode = getEntityGameType(entity) == GameType.SURVIVAL
				|| getEntityGameType(entity) == GameType.ADVENTURE;

		boolean enabled = vars.ThreeHearts;

		if (entity.level().getLevelData().isHardcore()) {
			enabled = false;
		}

		boolean heart1 = vars.Hearts >= 1;
		boolean heart2 = vars.Hearts >= 2;
		boolean heart3 = vars.Hearts >= 3;

		if (enabled && gamemode) {

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED,
					Identifier.parse("minecraft:textures/gui/sprites/hud/heart/container.png"),
					w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED,
					Identifier.parse("minecraft:textures/gui/sprites/hud/heart/container.png"),
					w / 2, h - 47, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED,
					Identifier.parse("minecraft:textures/gui/sprites/hud/heart/container.png"),
					w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);

			// Hearts
			if (heart3) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED,
						Identifier.parse("minecraft:textures/gui/sprites/hud/heart/frozen_full.png"),
						w / 2 - 5, h - 55, 0, 0, 9, 9, 9, 9);
			}

			if (heart1) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED,
						Identifier.parse("minecraft:textures/gui/sprites/hud/heart/frozen_full.png"),
						w / 2, h - 47, 0, 0, 9, 9, 9, 9);
			}

			if (heart2) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED,
						Identifier.parse("minecraft:textures/gui/sprites/hud/heart/frozen_full.png"),
						w / 2 - 10, h - 47, 0, 0, 9, 9, 9, 9);
			}
		}
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo info = Objects.requireNonNull(
					Minecraft.getInstance().getConnection()
			).getPlayerInfo(player.getGameProfile().id());

			if (info != null) {
				return info.getGameMode();
			}
		}
		return GameType.SURVIVAL;
	}
}