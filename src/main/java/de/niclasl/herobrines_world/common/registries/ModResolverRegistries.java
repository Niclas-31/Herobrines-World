package de.niclasl.herobrines_world.common.registries;

import de.niclasl.herobrines_world.common.network.transfer.resolver.CombinedChestResolver;
import de.niclasl.herobrines_world.common.network.transfer.resolver.InventoryResolver;
import de.niclasl.herobrines_world.common.network.transfer.resolver.SingleInventoryResolver;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import de.niclasl.herobrines_world_api.transfer.wrapper.InventoryWrapper;

public class ModResolverRegistries {

    public static void register() {
        HWRegistries.RESOLVERS.register(InventoryWrapper.class, new InventoryResolver());
        HWRegistries.RESOLVERS.register(InventoryWrapper.class, new SingleInventoryResolver());
        HWRegistries.RESOLVERS.register(InventoryWrapper.class, new CombinedChestResolver());
    }
}