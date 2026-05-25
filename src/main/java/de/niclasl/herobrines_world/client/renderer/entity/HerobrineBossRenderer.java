package de.niclasl.herobrines_world.client.renderer.entity;

import de.niclasl.herobrines_world.common.registries.entity.custom.HerobrineBoss;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class HerobrineBossRenderer extends HumanoidMobRenderer<HerobrineBoss, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    public HerobrineBossRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, new HumanoidModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public @NotNull HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HumanoidRenderState state) {
        return Identifier.parse("herobrines_world:textures/entity/herobrine_boss.png");
    }
}