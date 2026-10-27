package de.niclasl.herobrines_world.common.boss.ability;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.boss.BossAbility;
import de.niclasl.herobrines_world_api.boss.BossContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FireballAbility extends BossAbility {

    private static final int COOLDOWN = 100;

    public FireballAbility() {
        super("fireball");
    }

    @Override
    public void execute(BossContext context, int amount) {
        Level level = context.getLevel();
        Monster boss = context.getBoss();
        LivingEntity target = boss.getTarget();
        if (target == null) return;

        if (context.getAbilityCooldown() > 0) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            double dx = target.getX() - boss.getX() + (level.getRandom().nextDouble() - 0.5) * 2;
            double dy = target.getEyeY() - boss.getEyeY() + (level.getRandom().nextDouble() - 0.5) * 2;
            double dz = target.getZ() - boss.getZ() + (level.getRandom().nextDouble() - 0.5) * 2;
            SmallFireball fireball = new SmallFireball(EntityTypes.SMALL_FIREBALL, level);
            fireball.setPos(boss.getX(), boss.getEyeY(), boss.getZ());
            fireball.setDeltaMovement(dx * 0.2, dy * 0.2, dz * 0.2);
            level.addFreshEntity(fireball);

            context.setAbilityCooldown(COOLDOWN);
        }
    }

    @Override
    public @NotNull Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "fireball");
    }
}