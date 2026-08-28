package de.niclasl.herobrines_world.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.client.ModModelLayers;
import de.niclasl.herobrines_world.client.renderer.entity.model.RedCrystalModel;
import de.niclasl.herobrines_world.common.registries.entities.custom.RedCrystal;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class RedCrystalRenderer extends EntityRenderer<RedCrystal, RedCrystalRenderState> {
    private static final Identifier RED_CRYSTAL_LOCATION = Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "textures/entity/red_crystal/red_crystal.png");
    private final RedCrystalModel model;

    public RedCrystalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.model = new RedCrystalModel(context.bakeLayer(ModModelLayers.RED_CRYSTAL));
    }

    @Override
    public void submit(@NonNull RedCrystalRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.translate(0.0F, -0.5F, 0.0F);
        submitNodeCollector.submitModel(
                this.model, state, poseStack, RED_CRYSTAL_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
        );
        poseStack.popPose();
        Vec3 beamOffset = state.beamOffset;
        if (beamOffset != null) {
            float crystalY = getY(state.ageInTicks);
            float deltaX = (float)beamOffset.x;
            float deltaY = (float)beamOffset.y;
            float deltaZ = (float)beamOffset.z;
            poseStack.translate(beamOffset);
            EnderDragonRenderer.submitCrystalBeams(-deltaX, -deltaY + crystalY, -deltaZ, state.ageInTicks, poseStack, submitNodeCollector, state.lightCoords);
        }

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    public static float getY(float timeInTicks) {
        float hh = Mth.sin(timeInTicks * 0.2F) / 2.0F + 0.5F;
        hh = (hh * hh + hh) * 0.4F;
        return hh - 1.4F;
    }

    @Override
    public @NonNull RedCrystalRenderState createRenderState() {
        return new RedCrystalRenderState();
    }

    @Override
    public void extractRenderState(@NonNull RedCrystal entity, @NonNull RedCrystalRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.ageInTicks = entity.time + partialTicks;
        state.showsBottom = entity.showsBottom();
        BlockPos beamTarget = entity.getBeamTarget();
        if (beamTarget != null) {
            state.beamOffset = Vec3.atCenterOf(beamTarget).subtract(entity.getPosition(partialTicks));
        } else {
            state.beamOffset = null;
        }
    }

    @Override
    public boolean shouldRender(@NonNull RedCrystal entity, @NonNull Frustum culler, double camX, double camY, double camZ) {
        return super.shouldRender(entity, culler, camX, camY, camZ) || entity.getBeamTarget() != null;
    }
}