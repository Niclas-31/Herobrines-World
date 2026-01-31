package de.niclasl.herobrines_world.entity.custom;

import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.procedures.OwnerTargetTracker;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class HerobrineBoss extends Monster {

	private final ServerBossEvent bossBar =
			new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.NOTCHED_12);

	private int phase = 1;
	private int abilityCooldown = 0;
	private int minionCooldown = 0;
	private final Random random = new Random();

	private UUID ownerUUID;
	private boolean minionMode = true;
	private final List<Mob> minions = new ArrayList<>();
	private boolean hideBossBar;
	private boolean ownedBoss = false;

	public HerobrineBoss(EntityType<? extends Monster> type, Level level) {
		super(type, level);
		this.xpReward = 500;
		this.setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
		this.setNoGravity(true);
	}

	@Override
	protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
		return new FlyingPathNavigation(this, level);
	}

	@Override
	public boolean canAttack(@NotNull LivingEntity target) {
		ModVariables.PlayerVariables mapVars = target.getData(ModVariables.PLAYER_VARIABLES);
		UUID owner = mapVars.getRelicOwner();
		if (owner != null && target.getUUID() == owner) {
			return false;
		}
		return super.canAttack(target);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8D) {
			@Override
			protected Vec3 getPosition() {
				return HerobrineBoss.this.position().add(
						random.nextInt(16) - 8,
						random.nextInt(8) - 4,
						random.nextInt(16) - 8
				);
			}
		});
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new FloatGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this){
			@Override
			protected boolean canAttack(@Nullable LivingEntity target, @NotNull TargetingConditions conditions) {
				if (isMinionMode() && isOwner(target)) return false;
				return super.canAttack(target, conditions);
			}
		});

		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(
				this,
				Player.class,
				true,
                (player, level) -> !isMinionMode() || !isOwner(player)
        ));
	}

	@Override
	public void startSeenByPlayer(@NotNull ServerPlayer player) {
		if (hideBossBar) return;
		super.startSeenByPlayer(player);
		bossBar.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(@NotNull ServerPlayer player) {
		if (hideBossBar) return;
		super.stopSeenByPlayer(player);
		bossBar.removePlayer(player);
	}

	private void spawnVanillaMinions(int count) {
		if (!(level() instanceof ServerLevel level)) return;

		for (int i = 0; i < count; i++) {
			EntityType<?> type = random.nextBoolean()
					? EntityType.ZOMBIE
					: EntityType.SKELETON;

			Mob mob = (Mob) type.create(level, EntitySpawnReason.MOB_SUMMONED);
			if (mob == null) continue;

			mob.setPos(
					getX() + random.nextInt(6) - 3,
					getY(),
					getZ() + random.nextInt(6) - 3
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

			minions.add(mob);
			level.addFreshEntity(mob);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);

		minions.removeIf(Mob::isDeadOrDying);

		if (ownerUUID != null) {
			OwnerTargetTracker.cleanTargets(ownerUUID);
		}
	}

	private void handleFireballAttack() {
		if (abilityCooldown > 0) return;
		LivingEntity target = this.getTarget();
		if (target == null) return;

		for (int i = 0; i < 3; i++) {
			double dx = target.getX() - this.getX() + (random.nextDouble() - 0.5) * 2;
			double dy = target.getEyeY() - this.getEyeY() + (random.nextDouble() - 0.5) * 2;
			double dz = target.getZ() - this.getZ() + (random.nextDouble() - 0.5) * 2;
			SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, this.level());
			fireball.setPos(this.getX(), this.getEyeY(), this.getZ());
			fireball.setDeltaMovement(dx * 0.2, dy * 0.2, dz * 0.2);
			this.level().addFreshEntity(fireball);
		}

		abilityCooldown = 60;
	}

	@Override
	public void setNoGravity(boolean noGravity) {
		super.setNoGravity(true);
	}

	@Override
	protected void customServerAiStep(@NotNull ServerLevel level) {
		super.customServerAiStep(level);

		float hpPercent = this.getHealth() / this.getMaxHealth();

		if (phase == 1 && hpPercent <= 0.66F) {
			phase = 2;
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(30);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.4);
			Objects.requireNonNull(this.getAttribute(Attributes.FLYING_SPEED)).setBaseValue(0.5);
		}

		if (phase == 2 && hpPercent <= 0.33F) {
			phase = 3;
			this.setHealth(this.getMaxHealth());
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(60);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.6);

			level.explode(this, getX(), getY(), getZ(),
					3F, Level.ExplosionInteraction.NONE);
		}

		if (minionCooldown <= 0 && minionMode && phase == 2 && minions.isEmpty()) {
			spawnVanillaMinions(2);
			minionCooldown = 200;
		} else if (minionCooldown <= 0 && minionMode && phase == 3 && minions.isEmpty()) {
			spawnVanillaMinions(4);
			minionCooldown = 100;
		}

		if (phase == 3) handleFireballAttack();

		if (abilityCooldown > 0) abilityCooldown--;
		if (minionCooldown > 0) minionCooldown--;

		bossBar.setProgress(hpPercent);

		if (ownerUUID != null) {
			Set<LivingEntity> targets = OwnerTargetTracker.getTargets(ownerUUID);
			if (!targets.isEmpty()) {
				this.setTarget(targets.iterator().next());
			}
		}
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected int getBaseExperienceReward(@NotNull ServerLevel level) {
		if (ownerUUID != null) {
			OwnerTargetTracker.cleanTargets(ownerUUID);
		}

		if (ownedBoss) {
			return 0;
		}
		return super.getBaseExperienceReward(level);
	}

	@Override
	protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source, boolean recentlyHit) {
		if (ownedBoss) return;
		this.spawnAtLocation(level, new ItemStack(Items.ENDER_EYE, 16 + random.nextInt(49)));
		super.dropCustomDeathLoot(level, source, recentlyHit);
	}

	@Override
	public void die(@NotNull DamageSource source) {
		super.die(source);

		if (ownedBoss) {
			return;
		}

		if (source.getEntity() instanceof ServerPlayer player) {
			ItemStack relicStack = new ItemStack(ModItems.HEROBRINE_RELIC.get());
			player.addItem(relicStack);

			ModVariables.PlayerVariables vars = player.getData(ModVariables.PLAYER_VARIABLES);
			vars.herobrineRelicOwner = player.getUUID().toString();
		}
	}

	@Override
	public boolean isAlliedTo(@Nullable Team team) {
		if (team == null) return super.isAlliedTo((Team) null);

		for (Player player : this.level().players()) {
			if (player.getTeam() == team && player.getUUID().equals(ownerUUID)) {
				return true;
			}
		}

		return super.isAlliedTo(team);
	}

	@Override
	protected void addAdditionalSaveData(@NotNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		if (ownerUUID != null) output.putString("Owner", ownerUUID.toString());
		output.putBoolean("MinionMode", minionMode);
		output.putBoolean("hideBossBar", hideBossBar);
		output.putBoolean("OwnedBoss", ownedBoss);
	}

	@Override
	protected void readAdditionalSaveData(@NotNull ValueInput input) {
		super.readAdditionalSaveData(input);

		String ownerStr = input.getStringOr("Owner", "");
		if (!ownerStr.isEmpty()) {
			try {
				ownerUUID = UUID.fromString(ownerStr);
			} catch (IllegalArgumentException e) {
				ownerUUID = null;
			}
		}

		minionMode = input.getBooleanOr("MinionMode", true);
		hideBossBar = input.getBooleanOr("hideBossBar", false);
		ownedBoss = input.getBooleanOr("OwnedBoss", false);
	}

	public void setTamedOwner(ServerPlayer player) {
		if (player != null) ownerUUID = player.getUUID();
	}

	public void setMinionMode(boolean b) {
		this.minionMode = b;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 600)
				.add(Attributes.ATTACK_DAMAGE, 40)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.FOLLOW_RANGE, 40)
				.add(Attributes.FLYING_SPEED, 0.3);
	}

	public static void onOwnerDeath(ServerPlayer player) {
		UUID owner = player.getData(ModVariables.PLAYER_VARIABLES).getRelicOwner();
		if (owner != null) {
			OwnerTargetTracker.removeOwnerAndTargets(owner);
		}
	}

	@Override
	public boolean hurtServer(@NotNull ServerLevel level, @NotNull DamageSource source, float amount) {
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
	protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
		return SoundEvents.PLAYER_HURT;
	}

	@Override
	protected @NotNull SoundEvent getDeathSound() {
		return SoundEvents.PLAYER_DEATH;
	}

	private boolean isOwner(Entity entity) {
		return entity instanceof Player player
				&& ownerUUID != null
				&& ownerUUID.equals(player.getUUID());
	}

	public void setHideBossBar(boolean hide) {
		this.hideBossBar = hide;
	}

	public boolean isMinionMode() {
		return this.minionMode;
	}
}
