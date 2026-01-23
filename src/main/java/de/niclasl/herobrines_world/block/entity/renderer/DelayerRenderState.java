package de.niclasl.herobrines_world.block.entity.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class DelayerRenderState extends BlockEntityRenderState {
    public BlockPos lightPosition;
    public Level blockEntityLevel;
    public Direction facing;
    public float rotation;
    public String saved;
}