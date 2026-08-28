package de.niclasl.herobrines_world.client.renderer.entity;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class RedCrystalRenderState extends EntityRenderState {
    public boolean showsBottom = true;
    public @Nullable Vec3 beamOffset;
}