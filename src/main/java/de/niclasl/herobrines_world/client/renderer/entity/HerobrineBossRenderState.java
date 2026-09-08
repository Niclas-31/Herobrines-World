package de.niclasl.herobrines_world.client.renderer.entity;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.AnimationState;

public class HerobrineBossRenderState extends HumanoidRenderState {
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState sprintAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
}