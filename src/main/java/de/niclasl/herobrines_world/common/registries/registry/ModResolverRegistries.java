package de.niclasl.herobrines_world.common.registries.registry;

import de.niclasl.herobrines_world.common.network.transfer.resolver.RemoteInventoryResolver;
import de.niclasl.herobrines_world_api.transfer.TransferAPI;

public class ModResolverRegistries {

    public static void register() {
        TransferAPI.registerResolver(new RemoteInventoryResolver());
    }
}