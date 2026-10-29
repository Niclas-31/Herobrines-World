package de.niclasl.herobrines_world.common.boss.entity;

import de.niclasl.herobrines_world.common.boss.BossFightManager;
import de.niclasl.herobrines_world.common.boss.BossManager;
import de.niclasl.herobrines_world.common.boss.ability.AbilityImpl;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class Entity303 extends Boss {

	private final ServerBossEvent bossBar =
			new ServerBossEvent(
					UUID.randomUUID(),
					this.getDisplayName(),
					ServerBossEvent.BossBarColor.RED,
					ServerBossEvent.BossBarOverlay.NOTCHED_20);

	private BossContext bossContext;
    private static final BossManager manager = BossManager.get();

	private BossPhase phase = BossPhase.PHASE_1;
	private final Random random = new Random();

	private final List<Mob> minions = new ArrayList<>();

	public Entity303(EntityType<Entity303> type, Level level) {
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
				return Entity303.this.position().add(
                        random.nextInt(16) -8,
                        random.nextInt(8) - 4,
                        random.nextInt(16) - 8
                );
			}
		});
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1024)
                .add(Attributes.ARMOR, 10)
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
                    manager.setState(BossFightState.ACTIVE);
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

        phase = input.read("phase", BossPhase.CODEC).orElse(BossPhase.PHASE_1);

        BossFightState state = input.read("fight_state", BossFightState.CODEC).orElse(BossFightState.NOT_STARTED);

        bossContext.setState(state);
        manager.getFightManager().setState(state);

        bossContext.setAbilityCooldown(input.getIntOr("abilityCooldown", 0));
        bossContext.setMinionCooldown(input.getIntOr("minionCooldown", 0));
        bossContext.setTeleportCooldown(input.getIntOr("teleportCooldown", 0));
        bossContext.setHealCooldown(input.getIntOr("healCooldown", 0));
        bossContext.setHealChargeTicks(input.getIntOr("healChargeTicks", 0));
        bossContext.setHealing(input.getBooleanOr("healing", false));
    }

    @Override
    protected @NonNull SoundEvent getHurtSound(@NonNull DamageSource source) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    protected @NonNull SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    @Override
	protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
		return new FlyingPathNavigation(this, level);
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
        super.startSeenByPlayer(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossBar.removePlayer(player);
    }

	@Override
	public void aiStep() {
		super.aiStep();
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());

        minions.removeIf(Mob::isDeadOrDying);
	}

	@Override
	protected void customServerAiStep(@NotNull ServerLevel level) {
		super.customServerAiStep(level);

        if (bossContext == null) {
            return;
        }

        bossContext.tick();

        updateAbilities();
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
            manager.getFightManager().setState(BossFightState.DEFEATED);

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
        if (phase == BossPhase.PHASE_2 && minions.isEmpty()) {
            List<EntityType<?>> entities = List.of(EntityTypes.WITHER_SKELETON, EntityTypes.PIGLIN);

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

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void die(@NotNull DamageSource source) {
        super.die(source);

        if (!(level() instanceof ServerLevel level)) {
            return;
        }

        MapVariables map = level.getServer().getDataStorage().computeIfAbsent(MapVariables.TYPE);

        if (source.getEntity() instanceof ServerPlayer player && !map.isEntity303Dead) {
            map.isEntity303Dead = true;
            map.markSyncDirty();
            player.sendSystemMessage(Component.translatable("entity.herobrines_world.herobrine_boss.defeated"));
        }

        NeoForge.EVENT_BUS.post(new BossDeathEvent(level, blockPosition(), this.getBossType(), this));
    }
}