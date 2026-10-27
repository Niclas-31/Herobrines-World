package de.niclasl.herobrines_world.common.network.transfer.resolver;

import de.niclasl.herobrines_world.common.network.transfer.wrapper.CombinedChestWrapper;
import de.niclasl.herobrines_world.common.network.transfer.wrapper.EmptyInventoryWrapper;
import de.niclasl.herobrines_world_api.transfer.resolver.Resolver;
import de.niclasl.herobrines_world_api.transfer.wrapper.InventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.List;

public class CombinedChestResolver extends Resolver<InventoryWrapper> {

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

    if (!(state.getBlock() instanceof ChestBlock)) {
      return EmptyInventoryWrapper.INSTANCE;
    }

    ChestType chestType = state.getValue(ChestBlock.TYPE);

    if (chestType == ChestType.SINGLE) {
      return EmptyInventoryWrapper.INSTANCE;
    }

    Direction facing = state.getValue(ChestBlock.FACING);

    Direction otherDirection = chestType == ChestType.LEFT
            ? facing.getCounterClockWise()
            : facing.getClockWise();

    BlockPos otherPos = pos.relative(otherDirection);
    BlockEntity otherBe = level.getBlockEntity(otherPos);

    if (!(otherBe instanceof BaseContainerBlockEntity otherContainer)) {
      return EmptyInventoryWrapper.INSTANCE;
    }

    BlockState otherState = level.getBlockState(otherPos);

    if (!(otherState.getBlock() instanceof ChestBlock)
            || otherState.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
      return EmptyInventoryWrapper.INSTANCE;
    }

    return new CombinedChestWrapper(List.of(baseContainer, otherContainer));
  }
}