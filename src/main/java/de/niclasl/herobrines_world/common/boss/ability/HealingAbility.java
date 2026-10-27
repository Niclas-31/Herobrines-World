package de.niclasl.herobrines_world.common.boss.ability;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.boss.BossAbility;
import de.niclasl.herobrines_world_api.boss.BossContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HealingAbility extends BossAbility {

  private static final int CHARGE_TIME = 60;
  private static final int COOLDOWN = 600;

  public HealingAbility() {
    super("healing");
  }

  @Override
  public void execute(BossContext context, int amount) {
    var boss = context.getBoss();

    if (boss == null || !boss.isAlive()) {
      context.setHealing(false);
      context.setHealChargeTicks(0);
      return;
    }

    if (context.isHealing()) {
      int remaining = context.getHealChargeTicks() - 1;
      context.setHealChargeTicks(remaining);

      if (remaining <= 0) {
        float healAmount = boss.getMaxHealth() * (amount / 100.0F);

        boss.heal(healAmount);

        context.setHealing(false);
        context.setHealChargeTicks(0);
        context.setHealCooldown(COOLDOWN);
      }

      return;
    }

    if (context.getHealCooldown() > 0) {
      return;
    }

    if (boss.getHealth() >= boss.getMaxHealth()) {
      return;
    }

    context.setHealing(true);
    context.setHealChargeTicks(CHARGE_TIME);
  }

  @Override
  public void execute(BossContext context, int amount, List<EntityType<?>> entities) {
    super.execute(context, amount, entities);
  }

  @Override
  public @NotNull Identifier id() {
    return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "healing");
  }
}