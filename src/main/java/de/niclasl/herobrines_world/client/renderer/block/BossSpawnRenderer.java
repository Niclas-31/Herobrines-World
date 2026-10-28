package de.niclasl.herobrines_world.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.niclasl.herobrines_world.common.registries.blocks.entities.BossSpawnBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BossSpawnRenderer implements BlockEntityRenderer<BossSpawnBlockEntity, BossSpawnRenderState> {
    private final ItemModelResolver itemModelResolver;

    public BossSpawnRenderer(BlockEntityRendererProvider.Context context) {
        itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NonNull BossSpawnRenderState createRenderState() {
        return new BossSpawnRenderState();
    }

    @Override
    public void extractRenderState(@NonNull BossSpawnBlockEntity blockEntity, @NonNull BossSpawnRenderState renderState, float partialTicks,
                                   @NonNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTicks, cameraPosition, breakProgress);

        renderState.lightPosition = blockEntity.getBlockPos();
        renderState.blockEntityLevel = blockEntity.getLevel();
        renderState.rotation = blockEntity.getRenderingRotation();

        itemModelResolver.updateForTopItem(renderState.itemStackRenderState,
                blockEntity.items.getFirst(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
    }

    @Override
    public void submit(@NotNull BossSpawnRenderState renderState, @NotNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.translate(0.5f, 1.15f, 0.5f);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));

        renderState.itemStackRenderState.submit(poseStack, submitNodeCollector, getLightLevel(renderState.blockEntityLevel,
                renderState.lightPosition), OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightCoordsUtil.pack(bLight, sLight);
    }
}