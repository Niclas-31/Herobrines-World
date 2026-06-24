package de.niclasl.herobrines_world.common.registries;

import de.niclasl.herobrines_world.common.network.transfer.resolver.RemoteInventoryResolver;
import de.niclasl.herobrines_world_api.api.transfer.TransferAPI;

public class ModResolverRegistries {

    public static void register() {
        TransferAPI.registerResolver(new RemoteInventoryResolver());
    }
}