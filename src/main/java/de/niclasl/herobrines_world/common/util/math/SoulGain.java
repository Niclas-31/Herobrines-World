package de.niclasl.herobrines_world.common.util.math;

public class SoulGain {

    public static int getSoulGain(int base, int playerLevel) {

        float multiplier;

        if (playerLevel < SoulMath.SOFT_CAP) {
            multiplier = 1.0f;

        } else if (playerLevel < SoulMath.HARD_CAP) {
            multiplier = 0.7f;

        } else {
            multiplier = 0.3f;
        }

        return Math.max(1, Math.round(base * multiplier));
    }
}