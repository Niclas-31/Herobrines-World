package de.niclasl.herobrines_world.common.event;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.boss.BossFightManager;
import de.niclasl.herobrines_world_api.structure.BossArena;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class BossEvents {

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        ServerLevel level = player.level();
        BossFightManager bossManager = BossFightManager.get(level);

        BlockPos pos = event.getPos();

        boolean insideArena = bossManager.getArenas()
                .values()
                .stream()
                .anyMatch(arena -> arena.contains(pos));

        if (insideArena) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ServerLevel level = player.level();
        BossFightManager bossManager = BossFightManager.get(level);

        BlockPos pos = event.getPos();

        boolean insideArena = bossManager.getArenas()
                .values()
                .stream()
                .anyMatch(arena -> arena.contains(pos));

        if (insideArena) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ServerLevel level = player.level();
        BossFightManager bossManager = BossFightManager.get(level);

        if (!bossManager.isActive()) {
            return;
        }

        BossArena arena = bossManager.getActiveArena();

        if (arena == null) {
            return;
        }

        BlockPos pos = player.blockPosition();

        if (!arena.contains(pos)) {
            player.teleportTo(1023.5, arena.origin().getY() + 1.5, 1005.5);
        }
    }
}