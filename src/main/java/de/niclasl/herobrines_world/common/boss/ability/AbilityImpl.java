package de.niclasl.herobrines_world.common.boss.ability;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.boss.Ability;
import de.niclasl.herobrines_world_api.boss.BossAbility;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.resources.Identifier;

public class AbilityImpl {

    public static final BossAbility SUMMON = new SummonAbility();
    public static final BossAbility TELEPORT = new TeleportAbility();
    public static final BossAbility FIREBALL = new FireballAbility();
    public static final BossAbility HEALING = new HealingAbility();

    public static void register() {
        registerAbility(id("summon"), SUMMON);
        registerAbility(id("teleport"), TELEPORT);
        registerAbility(id("fireball"), FIREBALL);
        registerAbility(id("health"), HEALING);
    }

    public static void registerAbility(Identifier id, Ability ability) {
        for (Ability existing : HWRegistries.ABILITIES.values()) {
            if (existing.id().equals(id)) {
                throw new IllegalStateException("Ability with id " + id + " already exists");
            }
        }

        HWRegistries.ABILITIES.register(id, ability);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path);
    }
}