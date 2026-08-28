package de.niclasl.herobrines_world.common.util.clock;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.clock.WorldClock;

public class ModWorldClocks {
    public static final ResourceKey<WorldClock> HEROBRINES_REALM = key("herobrines_realm");
    public static final ResourceKey<WorldClock> UNDERWORLD = key("underworld");

    public static void bootstrap(BootstrapContext<WorldClock> context) {
        context.register(HEROBRINES_REALM, new WorldClock());
        context.register(UNDERWORLD, new WorldClock());
    }

    private static ResourceKey<WorldClock> key(String id) {
        return ResourceKey.create(Registries.WORLD_CLOCK, Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, id));
    }
}