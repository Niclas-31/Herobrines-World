package de.niclasl.herobrines_world.client.renderer.block;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class LogicGateBlockEntityRenderState extends BlockEntityRenderState {
    public Direction facing;
    public float rotation;
    public String saved;
}