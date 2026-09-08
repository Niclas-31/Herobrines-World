package de.niclasl.herobrines_world.client.renderer.entity.model;

import de.niclasl.herobrines_world.client.renderer.entity.HerobrineBossRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class HerobrineBossModel extends HumanoidModel<HerobrineBossRenderState> {

    private final KeyframeAnimation flyAnimation;
    private final KeyframeAnimation sprintAnimation;
    private final KeyframeAnimation attackAnimation;

    public HerobrineBossModel(ModelPart root) {
        super(root);

        this.flyAnimation = HerobrineBossAnimation.fly.bake(root);
        this.sprintAnimation = HerobrineBossAnimation.sprint.bake(root);
        this.attackAnimation = HerobrineBossAnimation.attack.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();

        PartDefinition leftArm = root.addOrReplaceChild(
                "left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F)
        );
        PartDefinition rightArm = root.getChild("right_arm");
        leftArm.addOrReplaceChild(
                "left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.ZERO
        );
        rightArm.addOrReplaceChild(
                "right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.ZERO
        );

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NonNull HerobrineBossRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(state.yRot, state.xRot);

        this.sprintAnimation.apply(state.sprintAnimationState, state.ageInTicks);
        this.flyAnimation.apply(state.flyAnimationState, state.ageInTicks);
        this.attackAnimation.apply(state.attackAnimationState, state.ageInTicks, 3.0F);

        super.setupAnim(state);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }
}