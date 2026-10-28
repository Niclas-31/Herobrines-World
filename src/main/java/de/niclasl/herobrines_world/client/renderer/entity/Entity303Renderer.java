package de.niclasl.herobrines_world.client.renderer.entity;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.boss.entity.Entity303;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class Entity303Renderer extends HumanoidMobRenderer<Entity303, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private static final Identifier ENTITY_303_LOCATION = Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "textures/entity/entity_303.png");

    public Entity303Renderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public @NotNull HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HumanoidRenderState state) {
        return ENTITY_303_LOCATION;
    }
}