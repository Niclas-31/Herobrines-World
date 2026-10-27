package de.niclasl.herobrines_world.common.network.transfer.resolver;

import de.niclasl.herobrines_world.common.network.transfer.wrapper.EmptyInventoryWrapper;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.SingleInventoryWrapper;
import de.niclasl.herobrines_world_api.transfer.resolver.Resolver;
import de.niclasl.herobrines_world_api.transfer.wrapper.InventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class SingleInventoryResolver extends Resolver<InventoryWrapper> {

  @Override
  public InventoryWrapper resolve(ServerLevel level, BlockPos pos) {
    if (level == null) {
      return EmptyInventoryWrapper.INSTANCE;
    }

    BlockEntity be = level.getBlockEntity(pos);

    if (!(be instanceof BaseContainerBlockEntity baseContainer)) {
      return EmptyInventoryWrapper.INSTANCE;
    }

    BlockState state = level.getBlockState(pos);

    if (state.getBlock() instanceof ChestBlock
            && state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
      return EmptyInventoryWrapper.INSTANCE;
    }

    return new SingleInventoryWrapper(baseContainer);
  }
}