package de.niclasl.herobrines_world.common.registries.items.custom;

import de.niclasl.herobrines_world.common.network.message.OpenWaypointScreenPacket;
import de.niclasl.herobrines_world.common.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.common.registries.components.SavedWaypoint;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WaypointCompass extends Item {

    public WaypointCompass(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(Level level, Player player, @NonNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {

            List<SavedWaypoint> waypoints = getWaypoints(stack);

            if (player.isShiftKeyDown()) {

                BlockPos pos = player.blockPosition();

                SavedWaypoint waypoint = new SavedWaypoint(
                        UUID.randomUUID(),
                        "Waypoint " + (waypoints.size() + 1),
                        pos,
                        level.dimension()
                );

                waypoints.add(waypoint);

                saveWaypoints(stack, waypoints);

                player.displayClientMessage(
                        Component.translatable("item.herobrines_world.waypoint_compass.waypoint_saved")
                                .withStyle(ChatFormatting.GREEN),
                        true
                );

                return InteractionResult.SUCCESS;
            }

            PacketDistributor.sendToPlayer(
                    (ServerPlayer) player,
                    new OpenWaypointScreenPacket(stack));
        }

        return InteractionResult.SUCCESS;
    }

    private List<SavedWaypoint> getWaypoints(ItemStack stack) {

        List<SavedWaypoint> list = stack.get(ModDataComponents.WAYPOINTS);

        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    private void saveWaypoints(ItemStack stack, List<SavedWaypoint> waypoints) {
        stack.set(ModDataComponents.WAYPOINTS, List.copyOf(waypoints));
    }
}