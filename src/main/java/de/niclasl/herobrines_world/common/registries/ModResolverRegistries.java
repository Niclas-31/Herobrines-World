package de.niclasl.herobrines_world.common.registries;

import de.niclasl.herobrines_world.common.network.transfer.resolver.BlockResolver;
import de.niclasl.herobrines_world.common.network.transfer.resolver.EntityResolver;
import de.niclasl.herobrines_world.common.network.transfer.resolver.InventoryResolver;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import de.niclasl.herobrines_world_api.transfer.wrapper.BlockWrapper;
import de.niclasl.herobrines_world_api.transfer.wrapper.EntityWrapper;
import de.niclasl.herobrines_world_api.transfer.wrapper.InventoryWrapper;

public class ModResolverRegistries {

    public static void register() {
        HWRegistries.RESOLVERS.register(InventoryWrapper.class, new InventoryResolver());
        HWRegistries.RESOLVERS.register(BlockWrapper.class, new BlockResolver());
        HWRegistries.RESOLVERS.register(EntityWrapper.class, new EntityResolver());
    }
}