package de.niclasl.herobrines_world.common.boss.ability;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.boss.BossFightManager;
import de.niclasl.herobrines_world.common.boss.BossManager;
import de.niclasl.herobrines_world_api.boss.Boss;
import de.niclasl.herobrines_world_api.boss.BossAbility;
import de.niclasl.herobrines_world_api.boss.BossContext;
import de.niclasl.herobrines_world_api.structure.BossArena;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TeleportAbility extends BossAbility {

    private static final int COOLDOWN = 100;

    public TeleportAbility() {
        super("teleport");
    }

    @Override
    public void execute(BossContext context, int amount) {
        Boss boss = context.getBoss();
        LivingEntity target = boss.getTarget();

        if (target == null) {
            return;
        }

        if (context.getTeleportCooldown() > 0) {
            return;
        }

        Level level = context.getLevel();

        BossManager manager = BossManager.get();
        BossFightManager fightManager = manager.getFightManager();
        BossArena arena = fightManager.getArena();

        for (int i = 0; i < 20; i++) {
            double angle = level.getRandom().nextDouble() * Math.PI * 2.0;
            double distance = level.getRandom().nextDouble() * amount;

            double x = target.getX() + Math.cos(angle) * distance;
            double z = target.getZ() + Math.sin(angle) * distance;
            double y = target.getY();

            BlockPos teleportPos = BlockPos.containing(x, y, z);

            if (!arena.contains(teleportPos)) {
                continue;
            }

            boss.teleportTo(x, y, z);
            return;
        }

        double angle = level.getRandom().nextDouble() * Math.PI * 2.0;
        double distance = level.getRandom().nextDouble() * amount;

        double x = target.getX() + Math.cos(angle) * distance;
        double z = target.getZ() + Math.sin(angle) * distance;
        double y = target.getY();

        boss.teleportTo(x, y, z);

        context.setTeleportCooldown(COOLDOWN);
    }

    @Override
    public @NotNull Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "teleport");
    }
}