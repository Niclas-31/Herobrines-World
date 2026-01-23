package de.niclasl.herobrines_world.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.init.ModGameRules;

@EventBusSubscriber
public class LootBoxSpawning {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event.getEntity().level(), event.getEntity());
	}

	private static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if ((world instanceof ServerLevel _serverLevelGR0 && _serverLevelGR0.getGameRules().getBoolean(ModGameRules.CAN_LOOT_BOX_SPAWN))) {
			if (ModVariables.MapVariables.get(world).LootBoxTimer > 0) {
				ModVariables.MapVariables.get(world).LootBoxTimer = ModVariables.MapVariables.get(world).LootBoxTimer - 1;
				ModVariables.MapVariables.get(world).markSyncDirty();
			}
			if (ModVariables.MapVariables.get(world).LootBoxTimer == 0) {
				if (world instanceof ServerLevel _serverworld) {
					StructureTemplate template = _serverworld.getStructureManager().getOrCreate(ResourceLocation.fromNamespaceAndPath("herobrines_world", "loot_box/loot_box"));
                    template.placeInWorld(_serverworld,
                            BlockPos.containing(entity.getData(ModVariables.PLAYER_VARIABLES).X, entity.getData(ModVariables.PLAYER_VARIABLES).Y, entity.getData(ModVariables.PLAYER_VARIABLES).Z),
                            BlockPos.containing(entity.getData(ModVariables.PLAYER_VARIABLES).X, entity.getData(ModVariables.PLAYER_VARIABLES).Y, entity.getData(ModVariables.PLAYER_VARIABLES).Z),
                            new StructurePlaceSettings().setRotation(Rotation.getRandom(_serverworld.random)).setMirror(Mirror.values()[_serverworld.random.nextInt(2)]).setIgnoreEntities(false), _serverworld.random, 3);
                }
			}
			if (ModVariables.MapVariables.get(world).LootBoxTimer <= (world instanceof ServerLevel _serverLevelGR2 ? _serverLevelGR2.getGameRules().getInt(ModGameRules.SPAWN_LOOT_BOX_TIMER) : 0)) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("In §2" + new java.text.DecimalFormat("###,###,###").format(ModVariables.MapVariables.get(world).LootBoxTimer) + " §fTicks is coming a new loot box!")), true);
			}
		}
	}
}