package de.niclasl.herobrines_world.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.niclasl.herobrines_world.block.custom.Delayer;
import de.niclasl.herobrines_world.block.entity.custom.DelayerEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DelayerRenderer implements BlockEntityRenderer<DelayerEntity, DelayerRenderState> {

    private final Font font;
    
    public DelayerRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.font();
    }

    @Override
    public @NotNull DelayerRenderState createRenderState() {
        return new DelayerRenderState();
    }

    @Override
    public void extractRenderState(@NotNull DelayerEntity entity, @NotNull DelayerRenderState state, float partialTick,
                                   @NotNull Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTick, cameraPosition, breakProgress);

        state.facing = entity.getBlockState().getValue(Delayer.FACING);
        state.rotation = switch (state.facing) {
            case NORTH -> 0f;
            case WEST -> 90f;
            case EAST -> -90f;
            default -> 180f;
        };
        state.saved = entity.getFormattedTime();
    }

    @Override
    public void submit(@NotNull DelayerRenderState state, @NotNull PoseStack poseStack,
                       @NotNull SubmitNodeCollector collector, @NotNull CameraRenderState cameraState) {
        poseStack.pushPose();

        poseStack.translate(0.5, 0.125, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.rotation));
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        poseStack.scale(0.01f, 0.01f, 0.01f);

        FormattedCharSequence formattedcharsequence = Component.literal(state.saved).getVisualOrderText();

        float f = (float) -this.font.width(formattedcharsequence) / 2;

        collector.submitText(
                poseStack, f, 0f, formattedcharsequence, false, Font.DisplayMode.POLYGON_OFFSET, LightTexture.FULL_BRIGHT, DyeColor.YELLOW.getTextColor(), 0, 0
        );

        poseStack.popPose();
    }
}

