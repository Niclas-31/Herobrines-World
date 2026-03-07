package de.niclasl.herobrines_world.entity.renderer.custom;

import de.niclasl.herobrines_world.entity.custom.Entity303;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class Entity303Renderer extends HumanoidMobRenderer<Entity303, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    public Entity303Renderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, new HumanoidModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public @NotNull HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HumanoidRenderState state) {
        return ResourceLocation.parse("herobrines_world:textures/entity/entity_303.png");
    }
}