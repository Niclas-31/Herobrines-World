package de.niclasl.herobrines_world.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.niclasl.herobrines_world.block.custom.Delayer;
import de.niclasl.herobrines_world.block.entity.custom.WirelessReceiverBlockEntity;
import de.niclasl.herobrines_world.network.data.VisibleNetwork;
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

import java.util.Comparator;

public class WirelessReceiverBlockEntityRenderer implements BlockEntityRenderer<WirelessReceiverBlockEntity, WirelessReceiverBlockEntityRenderState> {

    private final Font font;
    public WirelessReceiverBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.font();
    }

    @Override
    public @NotNull WirelessReceiverBlockEntityRenderState createRenderState() {
        return new WirelessReceiverBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NotNull WirelessReceiverBlockEntity entity, @NotNull WirelessReceiverBlockEntityRenderState state,
                                   float partialTick, @NotNull Vec3 cameraPosition,
                                   @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTick, cameraPosition, breakProgress);

        state.facing = entity.getBlockState().getValue(Delayer.FACING);
        state.rotation = switch (state.facing) {
            case NORTH -> 0f;
            case WEST -> 90f;
            case EAST -> -90f;
            default -> 180f;
        };
        state.sender = entity.getVisibleNetworks().stream()
                .map(VisibleNetwork::getSenderData)
                .min(Comparator.comparingInt(a -> a.pos().distManhattan(entity.getBlockPos())))
                .orElse(null);

        state.saved = state.sender != null ? state.sender.getName() : "None";
    }

    @Override
    public void submit(@NotNull WirelessReceiverBlockEntityRenderState state, @NotNull PoseStack poseStack,
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
