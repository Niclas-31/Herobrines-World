package de.niclasl.herobrines_world.common.network.access.mode;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import net.minecraft.resources.Identifier;

public class PrivateMode implements AccessMode {

    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "private");
    }
}