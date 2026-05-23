package de.niclasl.herobrines_world.registries.command;

import com.mojang.brigadier.context.CommandContext;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.network.manager.PlayerProgressManager;
import de.niclasl.herobrines_world.network.message.SyncLeaderboardPacket;
import de.niclasl.herobrines_world.network.message.entry.LeaderboardEntry;
import de.niclasl.herobrines_world.network.message.type.LeaderboardType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@EventBusSubscriber
public class LeaderboardCommand {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("leaderboard")
                        .then(Commands.literal("souls")
                                .executes(ctx ->
                                        openLeaderboard(ctx, LeaderboardType.SOULS)
                                )
                        )
                        .then(Commands.literal("progression")
                                .executes(ctx ->
                                        openLeaderboard(ctx, LeaderboardType.PROGRESS)
                                )
                        )
        );
    }

    private static ModVariables.PlayerVariables vars(ServerPlayer player) {
        return player.getData(ModVariables.PLAYER_VARIABLES);
    }

    private static int openLeaderboard(CommandContext<CommandSourceStack> ctx, LeaderboardType type) {

        CommandSourceStack source = ctx.getSource();

        if (!(source.getEntity() instanceof ServerPlayer player)) {
            return 0;
        }

        List<ServerPlayer> players =
                source.getServer()
                        .getPlayerList()
                        .getPlayers();

        List<LeaderboardEntry> entries = new ArrayList<>();

        for (ServerPlayer p : players) {
            ServerLevel level = p.level();

            int value = switch (type) {
                case SOULS -> vars(p).Souls;
                case PROGRESS -> PlayerProgressManager.get(level).getProgress(player.getUUID());
            };

            entries.add(new LeaderboardEntry(p.getGameProfile().name(), value));
        }

        entries.sort(Comparator.comparingInt(LeaderboardEntry::value).reversed());

        PacketDistributor.sendToPlayer(player, new SyncLeaderboardPacket(type, entries));
        return 1;
    }
}