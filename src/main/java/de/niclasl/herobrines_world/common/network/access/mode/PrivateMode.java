package de.niclasl.herobrines_world.common.network.access.mode;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.annotation.Experimental;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@Experimental
public class PrivateMode implements AccessMode {

    @Override
    public @NonNull Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "private");
    }

    @Override
    public int priority() {
        return 1;
    }
}