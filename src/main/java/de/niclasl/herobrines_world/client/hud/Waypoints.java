package de.niclasl.herobrines_world.client.hud;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SavedWaypoint;
import de.niclasl.herobrines_world.common.registries.items.ModItems;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import org.joml.Vector3fc;

import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = HerobrinesWorld.MODID, value = Dist.CLIENT)
public class Waypoints {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void render(RenderGuiLayerEvent.Post event) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null) return;

        ItemStack stack = mc.player.getMainHandItem();

        if (!stack.is(ModItems.WAYPOINT_COMPASS.get())) return;

        List<SavedWaypoint> waypoints =
                stack.get(ModDataComponents.WAYPOINTS.get());

        UUID selected = stack.get(ModDataComponents.SELECTED_WAYPOINT.get());

        if (waypoints == null || selected == null) return;

        SavedWaypoint wp = waypoints.stream()
                .filter(waypoint -> waypoint.id().equals(selected))
                .findFirst()
                .orElse(null);

        if (wp == null) return;

        if (wp.dimension() != mc.level.dimension()) return;

        renderWaypoint(event.getGuiGraphics(), mc, wp);
    }

    private static void renderWaypoint(GuiGraphics gui, Minecraft mc, SavedWaypoint wp) {

        Camera cam = mc.gameRenderer.getMainCamera();

        Vec3 camPos = cam.position();

        Vec3 to = new Vec3(
                wp.pos().getX() + 0.5,
                wp.pos().getY() + 0.5,
                wp.pos().getZ() + 0.5
        ).subtract(camPos);

        Vector3fc forward = cam.forwardVector();
        Vector3fc up = cam.upVector();
        Vector3fc left = cam.leftVector();

        double x = to.x * left.x() + to.y * left.y() + to.z * left.z();
        double y = to.x * up.x() + to.y * up.y() + to.z * up.z();
        double z = to.x * forward.x() + to.y * forward.y() + to.z * forward.z();

        if (z <= 0.01) return;

        int screenW = mc.getWindow().getGuiScaledWidth();
        int screenH = mc.getWindow().getGuiScaledHeight();

        int centerX = screenW / 2;
        int centerY = screenH / 2;

        int screenX = (int)(centerX + (x / z) * 200);
        int screenY = (int)(centerY - (y / z) * 200);

        assert mc.player != null;
        double dist = mc.player.distanceToSqr(Vec3.atCenterOf(wp.pos()));

        String text = wp.name() + " " + (int)Math.sqrt(dist) + "m";

        gui.fill(screenX - 3, screenY - 3, screenX + 3, screenY + 3, 0xFFFFFFFF);

        gui.drawString(mc.font, text, screenX - mc.font.width(text) / 2, screenY + 6, 0xFFFFFFFF);
    }
}