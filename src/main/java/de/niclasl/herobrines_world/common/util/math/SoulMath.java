package de.niclasl.herobrines_world.common.util.math;

public class SoulMath {

    public static final int SOFT_CAP = 100;
    public static final int HARD_CAP = 150;

    public static int getXPForLevel(int level) {

        if (level <= 15) return 2 * level + 7;
        if (level <= 30) return 5 * level - 38;

        int base = 9 * level - 158;

        if (level >= SOFT_CAP) {
            int extra = level - SOFT_CAP;
            base += extra * extra * 5;
        }

        return base;
    }

    public static float getSoulBonus(int prestige) {

        if (prestige >= 50) return 3.0f;
        if (prestige >= 25) return 2.5f;
        if (prestige >= 15) return 2.0f;
        if (prestige >= 10) return 1.75f;
        if (prestige >= 5) return 1.5f;
        if (prestige >= 3) return 1.25f;
        if (prestige >= 1) return 1.1f;

        return 1.0f;
    }

    public static int getLevelFromSouls(int souls) {

        if (souls <= 0) {
            return 0;
        }

        int level = 0;
        int total = 0;

        while (level < HARD_CAP) {
            int required = getXPForLevel(level);

            if (total + required > souls) {
                break;
            }

            total += required;
            level++;
        }

        return level;
    }
}