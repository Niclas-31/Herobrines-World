package de.niclasl.herobrines_world.procedures;

import de.niclasl.herobrines_world.entity.ModEntities;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.*;

public class OwnerTargetTracker {

    private static final Map<UUID, Set<LivingEntity>> ownerTargets = new HashMap<>();

    public static void addTarget(UUID ownerUUID, LivingEntity target) {
        if (ownerUUID == null || target == null) return;
        ownerTargets.computeIfAbsent(ownerUUID, k -> new HashSet<>()).add(target);
    }

    public static Set<LivingEntity> getTargets(UUID ownerUUID) {
        Set<LivingEntity> targets = ownerTargets.get(ownerUUID);
        if (targets == null) return Collections.emptySet();
        targets.removeIf(e -> e == null || !e.isAlive());
        return targets;
    }

    public static void cleanTargets(UUID ownerUUID) {
        Set<LivingEntity> targets = ownerTargets.get(ownerUUID);
        if (targets != null) {
            targets.removeIf(e -> e == null || !e.isAlive());
            if (targets.isEmpty()) {
                ownerTargets.remove(ownerUUID);
            }
        }
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(OwnerTargetTracker::onLivingHurt);
    }

    private static void onLivingHurt(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        UUID owner = player.getData(ModVariables.PLAYER_VARIABLES).getRelicOwner();
        if (owner == null) return;

        LivingEntity target = event.getEntity();
        if (!target.getType().equals(ModEntities.HEROBRINE_BOSS.get())) {
            addTarget(owner, target);
        }
    }

    public static void removeOwnerAndTargets(UUID owner) {
        if (owner == null) return;
        ownerTargets.remove(owner);
    }
}