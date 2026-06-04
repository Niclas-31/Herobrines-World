package de.niclasl.herobrines_world.client.renderer.block;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class BatteryChargerRenderState extends BlockEntityRenderState {
    public BlockPos lightPosition;
    public Level blockEntityLevel;
    public Direction facing;

    final ItemStackRenderState itemStackRenderState1 = new ItemStackRenderState();
    final ItemStackRenderState itemStackRenderState2 = new ItemStackRenderState();
}