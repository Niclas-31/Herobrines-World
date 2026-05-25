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

        itemModelResolver.updateForTopItem(renderState.itemStackRenderState,
                blockEntity.items.getFirst(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
    }

    @Override
    public void submit(@NotNull BatteryChargerRenderState renderState, PoseStack poseStack,
                       @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        float x = 1f;
        float y = 0.31f;
        float z = 1f;

        if (renderState.blockEntityLevel != null && renderState.lightPosition != null) {
            BatteryChargerBlockEntity be = (BatteryChargerBlockEntity) renderState.blockEntityLevel.getBlockEntity(renderState.lightPosition);
            if (be != null) {
                switch (be.getBlockState().getValue(BatteryChargerBlock.FACING)) {
                    case NORTH -> { x = 0.5f; z = 0.31f; }
                    case SOUTH -> { x = 0.5f; z = 0.69f; }
                    case EAST  -> { x = 0.69f; z = 0.5f; }
                    case WEST  -> { x = 0.31f; z = 0.5f; }
                }
            }
        }

        poseStack.translate(x, y, z);

        renderState.itemStackRenderState.submit(
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