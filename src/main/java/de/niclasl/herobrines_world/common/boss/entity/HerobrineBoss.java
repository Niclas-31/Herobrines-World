package de.niclasl.herobrines_world.common.boss.entity;

import de.niclasl.herobrines_world.common.boss.BossFightManager;
import de.niclasl.herobrines_world.common.boss.BossManager;
import de.niclasl.herobrines_world.common.boss.ability.AbilityImpl;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.RelicData;
import de.niclasl.herobrines_world.common.registries.entities.ModEntities;
import de.niclasl.herobrines_world.common.registries.items.ModItems;
import de.niclasl.herobrines_world.common.structure.boss.HWStructureTypes;
import de.niclasl.herobrines_world.common.util.variables.MapVariables;
import de.niclasl.herobrines_world_api.boss.*;
import de.niclasl.herobrines_world_api.event.BossDeathEvent;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class HerobrineBoss extends Boss {

	private final ServerBossEvent bossBar =
			new ServerBossEvent(
					UUID.randomUUID(),
					this.getDisplayName(),
					ServerBossEvent.BossBarColor.RED,
					ServerBossEvent.BossBarOverlay.NOTCHED_12
			);

	private BossContext bossContext;
    private static final BossManager manager = BossManager.get();

	private BossPhase phase = BossPhase.PHASE_1;
	private final Random random = new Random();

    private final List<Mob> minions = new ArrayList<>();

    private static final Map<UUID, Set<LivingEntity>> ownerTargets = new HashMap<>();
	private UUID ownerUUID;
	private boolean minionMode = true;
	private boolean hideBossBar;
	private boolean ownedBoss = false;

	public HerobrineBoss(EntityType<? extends Monster> type, Level level) {
		super(type, level);

		this.bossContext = new BossContext(level, this, BossFightState.NOT_STARTED);
		this.moveControl = new FlyingMoveControl<>(this, 10, false);

		this.xpReward = 500;
		this.setPersistenceRequired();
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
			protected boolean canAttack(@Nullable LivingEntity target,
										@NotNull TargetingConditions conditions) {
				if (isOwner(target)) return false;

				return super.canAttack(target, conditions);
			}
		});

		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(
				this,
				Player.class,
				true,
				(player, _) -> !isOwner(player)
		));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 600)
				.add(Attributes.ATTACK_DAMAGE, 6)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.FOLLOW_RANGE, 40)
				.add(Attributes.FLYING_SPEED, 0.3);
	}

	@Override
	public void tick() {
		super.tick();

		BossFightState state = bossContext.getState();

		if (state == BossFightState.STARTING) {
			this.setInvulnerable(true);
			this.setNoAi(true);

			if (this.getHealth() < this.getMaxHealth()) {
				this.setHealth(Math.min(
						this.getHealth() + 1.0F,
						this.getMaxHealth()
				));
			}

			if (this.getHealth() >= this.getMaxHealth()) {
				if (level() instanceof ServerLevel level) {
					BossFightManager manager = BossFightManager.get(level);
					manager.startFight(HWStructureTypes.HEROBRINE_TEMPLE.id());
				}

				bossContext.setState(BossFightState.ACTIVE);

				this.setInvulnerable(false);
				this.setNoAi(false);
			}
        }
	}

	@Override
	protected void addAdditionalSaveData(@NotNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		if (ownerUUID != null) output.putString("Owner", ownerUUID.toString());
		output.putBoolean("MinionMode", minionMode);
		output.putBoolean("hideBossBar", hideBossBar);
		output.putBoolean("OwnedBoss", ownedBoss);

		output.store("phase", BossPhase.CODEC, phase);
		output.store("fight_state", BossFightState.CODEC, bossContext.getState());

		output.putInt("abilityCooldown", bossContext.getAbilityCooldown());
		output.putInt("minionCooldown", bossContext.getMinionCooldown());
		output.putInt("teleportCooldown", bossContext.getTeleportCooldown());
		output.putInt("healCooldown", bossContext.getHealCooldown());
		output.putInt("healChargeTicks", bossContext.getHealChargeTicks());
		output.putBoolean("healing", bossContext.isHealing());
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

		phase = input.read("phase", BossPhase.CODEC).orElse(BossPhase.PHASE_1);

		BossFightState state = input.read("fight_state", BossFightState.CODEC).orElse(BossFightState.NOT_STARTED);

		bossContext.setState(state);

		bossContext.setAbilityCooldown(input.getIntOr("abilityCooldown", 0));
		bossContext.setMinionCooldown(input.getIntOr("minionCooldown", 0));
		bossContext.setTeleportCooldown(input.getIntOr("teleportCooldown", 0));
		bossContext.setHealCooldown(input.getIntOr("healCooldown", 0));
		bossContext.setHealChargeTicks(input.getIntOr("healChargeTicks", 0));
		bossContext.setHealing(input.getBooleanOr("healing", false));
	}

	@Override
	protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
		return SoundEvents.PLAYER_HURT;
	}

	@Override
	protected @NotNull SoundEvent getDeathSound() {
		return SoundEvents.PLAYER_DEATH;
	}

	@Override
	protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
		return new FlyingPathNavigation(this, level);
	}

	@Override
	public boolean canAttack(@NotNull LivingEntity target) {
		if (ownerUUID != null && ownerUUID.equals(target.getUUID())) {
			return false;
		}
		return super.canAttack(target);
	}

	@Override
	public BossType getBossType() {
		return BossType.HEROBRINE_BOSS;
	}

	@Override
	public void setMinions(Mob mob) {
		minions.add(mob);
	}

	@Override
	public void setBossContext(BossContext bossContext) {
		this.bossContext = bossContext;
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

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());

		minions.removeIf(Mob::isDeadOrDying);

		if (ownerUUID != null) {
			cleanTargets(ownerUUID);
		}
	}

	@Override
	protected void customServerAiStep(@NotNull ServerLevel level) {
		super.customServerAiStep(level);

		if (bossContext == null) {
			return;
		}

		bossContext.tick();

		updateAbilities();
		updateTarget();
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

		if (bossContext == null) {
			return super.hurtServer(level, source, amount);
		}

		if (bossContext.getState() != BossFightState.ACTIVE) {
			return false;
		}

		if (getHealth() - amount <= 0) {
			if (phase != BossPhase.PHASE_4) {
				nextPhase();
				return false;
			}

			bossContext.setState(BossFightState.DEFEATED);
			manager.getFightManager(level).endFight();

			return super.hurtServer(level, source, amount);
		}

		return super.hurtServer(level, source, amount);
	}

	private void nextPhase() {
		switch (phase) {
			case PHASE_1 -> phase = BossPhase.PHASE_2;
			case PHASE_2 -> phase = BossPhase.PHASE_3;
			case PHASE_3 -> phase = BossPhase.PHASE_4;
			case PHASE_4 -> {
                return;
            }
		}

		setHealth(getMaxHealth());

		applyPhaseAttributes();

		bossContext.setAbilityCooldown(0);
		bossContext.setMinionCooldown(0);
	}

    private void applyPhaseAttributes() {
        switch (phase) {
            case PHASE_1 -> setPhaseAttributes(6.0, 0.3, 0.3);
            case PHASE_2 -> setPhaseAttributes(10.0, 0.4, 0.5);
            case PHASE_3 -> setPhaseAttributes(14.0, 0.5, 0.6);
            case PHASE_4 -> setPhaseAttributes(18.0, 0.6, 0.7);
        }
    }

    private void setPhaseAttributes(double attackDamage, double movementSpeed, double flyingSpeed) {
        Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE))
                .setBaseValue(attackDamage);

        Objects.requireNonNull(getAttribute(Attributes.MOVEMENT_SPEED))
                .setBaseValue(movementSpeed);

        Objects.requireNonNull(getAttribute(Attributes.FLYING_SPEED))
                .setBaseValue(flyingSpeed);
    }

	private void updateAbilities() {
		if (phase == BossPhase.PHASE_2
				&& minionMode
				&& minions.isEmpty()
		) {
			List<EntityType<?>> entities = List.of(EntityTypes.ZOMBIE, EntityTypes.SKELETON);

			AbilityImpl.SUMMON.execute(bossContext, 2, entities);
		}

		if (phase == BossPhase.PHASE_3) {
			AbilityImpl.FIREBALL.execute(bossContext, 3);
		}

		if (phase == BossPhase.PHASE_4) {
			AbilityImpl.TELEPORT.execute(bossContext, 10);
		}

		if (phase == BossPhase.PHASE_2
						|| phase == BossPhase.PHASE_3
						|| phase == BossPhase.PHASE_4) {

			AbilityImpl.HEALING.execute(bossContext, 10);
		}
	}

	private void updateTarget() {
		if (ownerUUID == null) {
			return;
		}

		Set<LivingEntity> targets = getTargets(ownerUUID);

		if (!targets.isEmpty()) {
			setTarget(targets.iterator().next());
		}
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected int getBaseExperienceReward(@NotNull ServerLevel level) {
		if (ownerUUID != null) {
			cleanTargets(ownerUUID);
		}

		if (ownedBoss) {
			return 0;
		}
		return super.getBaseExperienceReward(level);
	}

	@Override
	protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source, boolean recentlyHit) {
		if (ownedBoss) return;
		this.spawnAtLocation(level, new ItemStack(Items.ENDER_EYE, 32 + random.nextInt(32)));
		super.dropCustomDeathLoot(level, source, recentlyHit);
	}

	@Override
	public void die(@NotNull DamageSource source) {
		super.die(source);

		if (ownedBoss) {
			return;
		}

		if (!(level() instanceof ServerLevel level)) {
			return;
		}

		MapVariables map = level.getServer().getDataStorage().computeIfAbsent(MapVariables.TYPE);

		if (source.getEntity() instanceof ServerPlayer player && !map.isHerobrineDead) {
			ItemStack relicStack = new ItemStack(ModItems.HEROBRINE_RELIC.get());

			relicStack.set(ModDataComponents.RELIC_DATA, new RelicData(
					player.getUUID(),
					player.getName().getString(),
					1200,
					this.getStringUUID()
			));
			player.addItem(relicStack);

			map.isHerobrineDead = true;
			map.markSyncDirty();
			player.sendSystemMessage(Component.translatable("entity.herobrines_world.herobrine_boss.defeated"));
		}

		NeoForge.EVENT_BUS.post(new BossDeathEvent(level, blockPosition(), this.getBossType(), this));
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

	public void setTamedOwner(ServerPlayer player) {
		if (player != null) ownerUUID = player.getUUID();
	}

	public void setMinionMode(boolean b) {
		this.minionMode = b;
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

	public static void addTarget(UUID ownerUUID, LivingEntity target) {
		if (ownerUUID == null || target == null) return;
		ownerTargets.computeIfAbsent(ownerUUID, _ -> new HashSet<>()).add(target);
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
		NeoForge.EVENT_BUS.addListener(HerobrineBoss::onLivingHurt);
		NeoForge.EVENT_BUS.addListener(HerobrineBoss::removeOwnerAndTarget);
	}

	private static void onLivingHurt(LivingDamageEvent.Pre event) {
		if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

		UUID owner = player.getUUID();

        LivingEntity target = event.getEntity();

		if (target instanceof ServerPlayer targetPlayer) {
			if (targetPlayer.getUUID().equals(owner)) {
				return;
			}
		}

		if (!target.getType().equals(ModEntities.HEROBRINE_BOSS.get())) {
			addTarget(owner, target);
		}
	}

	public static void removeOwnerAndTarget(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer player) {
			UUID owner = player.getUUID();
			ownerTargets.remove(owner);
		}
	}
}