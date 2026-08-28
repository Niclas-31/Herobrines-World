package de.niclasl.herobrines_world.common.util.clock;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.timeline.Timeline;

public class ModTimelines {

    public static void bootstrap(BootstrapContext<Timeline> context) {
    }

    private static ResourceKey<Timeline> key(String id) {
        return ResourceKey.create(Registries.TIMELINE,
                Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, id));
    }
}