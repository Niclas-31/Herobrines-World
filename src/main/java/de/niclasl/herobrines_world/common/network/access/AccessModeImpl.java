package de.niclasl.herobrines_world.common.network.access;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.access.mode.OwnerOnlyMode;
import de.niclasl.herobrines_world.common.network.access.mode.PrivateMode;
import de.niclasl.herobrines_world.common.network.access.mode.PublicMode;
import de.niclasl.herobrines_world.common.network.access.mode.TrustedMode;
import de.niclasl.herobrines_world_api.annotation.Experimental;
import de.niclasl.herobrines_world_api.api.access.AccessMode;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.resources.Identifier;

@Experimental
public class AccessModeImpl {

    public static final AccessMode PUBLIC = new PublicMode();
    public static final AccessMode PRIVATE = new PrivateMode();
    public static final AccessMode TRUSTED = new TrustedMode();
    public static final AccessMode OWNER_ONLY = new OwnerOnlyMode();

    public static void register() {
        HWRegistries.ACCESS_MODES.put(id("public"), PUBLIC);
        HWRegistries.ACCESS_MODES.put(id("private"), PRIVATE);
        HWRegistries.ACCESS_MODES.put(id("trusted"), TRUSTED);
        HWRegistries.ACCESS_MODES.put(id("owner_only"), OWNER_ONLY);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path);
    }
}