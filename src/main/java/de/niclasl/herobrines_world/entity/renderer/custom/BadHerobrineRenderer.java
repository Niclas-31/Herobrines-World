package de.niclasl.herobrines_world.entity.renderer.custom;

import de.niclasl.herobrines_world.entity.custom.BadHerobrine;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class BadHerobrineRenderer extends HumanoidMobRenderer<BadHerobrine, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    public BadHerobrineRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, new HumanoidModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public @NotNull HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HumanoidRenderState state) {
        return Identifier.parse("herobrines_world:textures/entity/bad_herobrine.png");
    }
}