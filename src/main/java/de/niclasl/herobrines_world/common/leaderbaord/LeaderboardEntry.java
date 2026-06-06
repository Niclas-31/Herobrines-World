package de.niclasl.herobrines_world.common.leaderbaord;

import java.util.UUID;

public record LeaderboardEntry(UUID uuid, String name, int value) {
}