package de.niclasl.herobrines_world.common.boss.ability;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.boss.Boss;
import de.niclasl.herobrines_world_api.boss.BossAbility;
import de.niclasl.herobrines_world_api.boss.BossContext;
import de.niclasl.herobrines_world_api.boss.BossFightState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SummonAbility extends BossAbility {

    private static final int COOLDOWN = 200;

    public SummonAbility() {
        super("summon");
    }

    @Override
    public void execute(BossContext context, int amount, List<EntityType<?>> entities) {
        Level level = context.getLevel();
        Boss boss = context.getBoss();
        RandomSource random = level.getRandom();

        if (context.getState() != BossFightState.ACTIVE) {
            return;
        }

        if (context.getMinionCooldown() > 0) {
            return;
        }

        for (EntityType<?> type : entities) {
            for (int i = 0; i < amount; i++) {
                Mob mob = (Mob) type.create(level, EntitySpawnReason.MOB_SUMMONED);
                if (mob == null) return;

                mob.setPos(
                        boss.getX() + random.nextInt(6) - 3,
                        boss.getY(),
                        boss.getZ() + random.nextInt(6) - 3
                );

                mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                mob.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
                mob.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
                mob.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
                mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));

                mob.setDropChance(EquipmentSlot.HEAD, 0.0F);
                mob.setDropChance(EquipmentSlot.CHEST, 0.0F);
                mob.setDropChance(EquipmentSlot.LEGS, 0.0F);
                mob.setDropChance(EquipmentSlot.FEET, 0.0F);
                mob.setDropChance(EquipmentSlot.MAINHAND, 0.0F);

                boss.setMinions(mob);
                level.addFreshEntity(mob);

                context.setMinionCooldown(COOLDOWN);
            }
        }
    }

    @Override
    public @NotNull Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "summon_ability");
    }
}