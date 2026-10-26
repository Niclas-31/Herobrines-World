package de.niclasl.herobrines_world.common.network.transfer.resolver;

import de.niclasl.herobrines_world.common.network.transfer.wrapper.EmptyInventoryWrapper;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.StorageControllerInventoryWrapper;
import de.niclasl.herobrines_world.common.registries.blocks.entities.StorageControllerBlockEntity;
import de.niclasl.herobrines_world_api.transfer.resolver.Resolver;
import de.niclasl.herobrines_world_api.transfer.wrapper.InventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InventoryResolver extends Resolver<InventoryWrapper> {

    @Override
    public InventoryWrapper resolve(ServerLevel level, BlockPos pos) {

        if (level == null) return EmptyInventoryWrapper.INSTANCE;

        BlockEntity be = level.getBlockEntity(pos);

        if (be == null) return EmptyInventoryWrapper.INSTANCE;

        if (be instanceof StorageControllerBlockEntity controller) {
            return new StorageControllerInventoryWrapper(controller);
        }

        return EmptyInventoryWrapper.INSTANCE;
    }
}