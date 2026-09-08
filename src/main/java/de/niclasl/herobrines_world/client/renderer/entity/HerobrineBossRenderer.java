package de.niclasl.herobrines_world.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.client.ModModelLayers;
import de.niclasl.herobrines_world.client.renderer.entity.model.HerobrineBossModel;
import de.niclasl.herobrines_world.common.boss.entity.HerobrineBoss;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class HerobrineBossRenderer extends HumanoidMobRenderer<HerobrineBoss, HerobrineBossRenderState, HerobrineBossModel> {
    private static final Identifier HEROBRINE_BOSS_LOCATION = Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "textures/entity/herobrine_boss.png");

    public HerobrineBossRenderer(EntityRendererProvider.Context context) {
        super(context, new HerobrineBossModel(context.bakeLayer(ModModelLayers.HEROBRINE_BOSS)), 0.5F);
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull HerobrineBossRenderState state) {
        return HEROBRINE_BOSS_LOCATION;
    }

    @Override
    public void submit(@NonNull HerobrineBossRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public @NotNull HerobrineBossRenderState createRenderState() {
        return new HerobrineBossRenderState();
    }

    @Override
    public void extractRenderState(@NonNull HerobrineBoss entity, @NonNull HerobrineBossRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.sprintAnimationState.copyFrom(entity.sprintAnimationState);
        reusedState.flyAnimationState.copyFrom(entity.flyAnimationState);
        reusedState.attackAnimationState.copyFrom(entity.attackAnimationState);
    }
}