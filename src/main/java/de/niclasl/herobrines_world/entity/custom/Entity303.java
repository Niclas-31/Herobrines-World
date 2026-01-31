package de.niclasl.herobrines_world.entity.custom;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.*;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.projectile.SmallFireball;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Entity303 extends Monster {

	private final ServerBossEvent bossInfo =
			new ServerBossEvent(this.getDisplayName(),
					ServerBossEvent.BossBarColor.RED,
					ServerBossEvent.BossBarOverlay.NOTCHED_20);

	private int phase = 1;
	private int minionCooldown = 0;
	private int fireballCooldown = 0;

	private final List<Mob> minions = new ArrayList<>();

	public Entity303(EntityType<Entity303> type, Level level) {
		super(type, level);
		xpReward = 500;
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
		return new FlyingPathNavigation(this, level);
	}

	@Override
	public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
	}

	@Override
	protected void customServerAiStep(@NotNull ServerLevel level) {
		super.customServerAiStep(level);

		float hpPercent = this.getHealth() / this.getMaxHealth();

		if (phase == 1 && hpPercent <= 0.66F) {
			phase = 2;
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(55);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.4);
			Objects.requireNonNull(this.getAttribute(Attributes.FLYING_SPEED)).setBaseValue(0.5);
		}

		if (phase == 2 && hpPercent <= 0.33F) {
			phase = 3;

			this.setHealth(this.getMaxHealth());
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(75);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.6);

			level.explode(this, getX(), getY(), getZ(),
					3F, Level.ExplosionInteraction.NONE);
		}

		if (minionCooldown <= 0 && phase == 2 && minions.isEmpty()) {
			spawnVanillaMinions(2);
			minionCooldown = 200;
		} else if (minionCooldown <= 0 && phase == 3 && minions.isEmpty()) {
			spawnVanillaMinions(4);
			minionCooldown = 100;
		}

		handleAbilities(level);

		if (fireballCooldown > 0) fireballCooldown--;
		if (minionCooldown > 0) minionCooldown--;

		bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	private void handleAbilities(ServerLevel level) {
		LivingEntity target = this.getTarget();
		if (target == null) return;

		if (phase == 3 && fireballCooldown <= 0) {
			shootFireballs(level, target);
			fireballCooldown = 100;
		}
	}

	private void shootFireballs(ServerLevel level, LivingEntity target) {
		for (int i = 0; i < 3; i++) {

			double startX = this.getX();
			double startY = this.getEyeY() - 0.3;
			double startZ = this.getZ();

			Vec3 direction = new Vec3(
					target.getX() - startX + randomOffset(),
					target.getEyeY() - startY,
					target.getZ() - startZ + randomOffset()
			).normalize().scale(0.9);

			SmallFireball fireball = new SmallFireball(
					level,
					startX,
					startY,
					startZ,
					direction
			);

			level.addFreshEntity(fireball);
		}
	}

	private double randomOffset() {
		return (this.random.nextDouble() - 0.5) * 4.0;
	}

	private void spawnVanillaMinions(int count) {
		if (!(level() instanceof ServerLevel level)) return;

		for (int i = 0; i < count; i++) {
			EntityType<?> type = random.nextBoolean()
					? EntityType.WITHER_SKELETON
					: EntityType.PIGLIN;

			Mob mob = (Mob) type.create(level, EntitySpawnReason.MOB_SUMMONED);
			if (mob == null) continue;

			mob.setPos(
					getX() + random.nextInt(6) - 3,
					getY(),
					getZ() + random.nextInt(6) - 3
			);

			minions.add(mob);
			level.addFreshEntity(mob);
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true));
		this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
		this.targetSelector.addGoal(4,
				new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(5,
				new HurtByTargetGoal(this));
	}

	@Override
	public boolean hurtServer(@NotNull ServerLevel level, DamageSource source, float amount) {

		if (source.is(DamageTypes.FALL)
				|| source.is(DamageTypes.DROWN)
				|| source.is(DamageTypes.CACTUS)
				|| source.is(DamageTypes.WITHER)
				|| source.is(DamageTypes.EXPLOSION)) {
			return false;
		}

		return super.hurtServer(level, source, amount);
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return false;
	}

	@Override
	public void startSeenByPlayer(@NotNull ServerPlayer player) {
		bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(@NotNull ServerPlayer player) {
		bossInfo.removePlayer(player);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 1024)
				.add(Attributes.ARMOR, 10)
				.add(Attributes.ATTACK_DAMAGE, 40)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.FLYING_SPEED, 0.3)
				.add(Attributes.FOLLOW_RANGE, 40);
	}
}
