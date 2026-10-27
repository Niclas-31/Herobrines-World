package de.niclasl.herobrines_world.client.renderer.entity;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.boss.entity.HerobrineBoss;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class HerobrineBossRenderer extends HumanoidMobRenderer<HerobrineBoss, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private static final Identifier HEROBRINE_BOSS_LOCATION = Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "textures/entity/herobrine_boss.png");

    public HerobrineBossRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
    }

    @Override
    public @NotNull HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull HumanoidRenderState state) {
        return HEROBRINE_BOSS_LOCATION;
    }
}