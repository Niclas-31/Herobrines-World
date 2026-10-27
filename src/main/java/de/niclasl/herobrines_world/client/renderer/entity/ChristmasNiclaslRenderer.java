package de.niclasl.herobrines_world.client.renderer.entity;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.entities.custom.ChristmasNiclasl;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class ChristmasNiclaslRenderer extends HumanoidMobRenderer<ChristmasNiclasl, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private static final Identifier CHRISTMAS_NICLASL_LOCATION = Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "textures/entity/christmas_niclasl.png");

    public ChristmasNiclaslRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public @NotNull HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HumanoidRenderState state) {
        return CHRISTMAS_NICLASL_LOCATION;
    }
}