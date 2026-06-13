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
        registerAccessMode(id("public"), PUBLIC);
        registerAccessMode(id("private"), PRIVATE);
        registerAccessMode(id("trusted"), TRUSTED);
        registerAccessMode(id("owner_only"), OWNER_ONLY);
    }

    public static void registerAccessMode(Identifier id, AccessMode mode) {
        for (AccessMode existing : HWRegistries.ACCESS_MODES.values()) {

            if (existing.priority() == mode.priority()) {
                throw new IllegalStateException(
                        "Duplicate AccessMode priority "
                                + mode.priority()
                                + " between "
                                + existing.id()
                                + " and "
                                + mode.id()
                );
            }
        }

        HWRegistries.ACCESS_MODES.put(id, mode);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path);
    }
}