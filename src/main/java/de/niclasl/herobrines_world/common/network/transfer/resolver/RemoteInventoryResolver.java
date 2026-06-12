package de.niclasl.herobrines_world.common.network.transfer.resolver;

import de.niclasl.herobrines_world.common.network.transfer.wrapper.StorageControllerInventoryWrapper;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.EmptyInventoryWrapper;
import de.niclasl.herobrines_world_api.api.transfer.resolver.InventoryResolver;
import de.niclasl.herobrines_world_api.api.transfer.wrapper.InventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RemoteInventoryResolver implements InventoryResolver {

    @Override
    public InventoryWrapper resolve(ServerLevel level, BlockPos pos, ResourceKey<Level> dim) {

        ServerLevel targetLevel = level.getServer().getLevel(dim);

        if (targetLevel == null) {
            return EmptyInventoryWrapper.INSTANCE;
        }

        BlockEntity be = targetLevel.getBlockEntity(pos);

        if (be == null) {
            return EmptyInventoryWrapper.INSTANCE;
        }

        return new StorageControllerInventoryWrapper(be);
    }
}