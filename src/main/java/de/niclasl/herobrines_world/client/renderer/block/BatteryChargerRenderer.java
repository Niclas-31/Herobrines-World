package de.niclasl.herobrines_world.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import de.niclasl.herobrines_world.common.registries.block.custom.BatteryChargerBlock;
import de.niclasl.herobrines_world.common.registries.block.entity.BatteryChargerBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class BatteryChargerRenderer implements BlockEntityRenderer<BatteryChargerBlockEntity, BatteryChargerRenderState> {
    private final ItemModelResolver itemModelResolver;

    public BatteryChargerRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NotNull BatteryChargerRenderState createRenderState() {
        return new BatteryChargerRenderState();
    }

    @Override
    public void extractRenderState(@NotNull BatteryChargerBlockEntity blockEntity, @NotNull BatteryChargerRenderState renderState, float partialTick,
                                   @NotNull Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.lightPosition = blockEntity.getBlockPos();
        renderState.blockEntityLevel = blockEntity.getLevel();
        renderState.facing = blockEntity.getBlockState().getValue(BatteryChargerBlock.FACING);

        itemModelResolver.updateForTopItem(renderState.itemStackRenderState1,
                blockEntity.items.getFirst(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        itemModelResolver.updateForTopItem(renderState.itemStackRenderState2,
                blockEntity.items.getLast(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
    }

    @Override
    public void submit(@NotNull BatteryChargerRenderState renderState, @NonNull PoseStack poseStack,
                       @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState cameraRenderState) {
        float posX1 = 1f;
        float posZ1 = 1f;

        float y = 0.82f;

        float posX2 = 1f;
        float posZ2 = 1f;

        switch (renderState.facing) {
            case NORTH -> {
                posX1 = 0.375f;
                posZ1 = 0.9f;

                posX2 = 0.625f;
                posZ2 = 0.9f;
            }
            case SOUTH -> {
                posX1 = 0.625f;
                posZ1 = 0.1f;

                posX2 = 0.375f;
                posZ2 = 0.1f;
            }
            case EAST  -> {
                posX1 = 0.1f;
                posZ1 = 0.375f;

                posX2 = 0.1f;
                posZ2 = 0.625f;
            }
            case WEST  -> {
                posX1 = 0.9f;
                posZ1 = 0.625f;

                posX2 = 0.9f;
                posZ2 = 0.375f;
            }
        }

        poseStack.pushPose();

        poseStack.translate(posX2, y, posZ2);
        poseStack.scale(1f, 0.75f, 1f);

        renderState.itemStackRenderState1.submit(
                poseStack,
                submitNodeCollector,
                getLightLevel(renderState.blockEntityLevel, renderState.lightPosition),
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();

        poseStack.pushPose();

        poseStack.translate(posX1, y, posZ1);
        poseStack.scale(1f, 0.75f, 1f);

        renderState.itemStackRenderState2.submit(
                poseStack,
                submitNodeCollector,
                getLightLevel(renderState.blockEntityLevel, renderState.lightPosition),
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}