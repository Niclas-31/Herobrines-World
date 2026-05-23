package de.niclasl.herobrines_world.math;

public class SoulMath {

    public static final int SOFT_CAP = 100;
    public static final int HARD_CAP = 150;

    public static int getXPForLevel(int level) {

        if (level <= 16) return 2 * level + 7;
        if (level <= 31) return 5 * level - 38;

        int base = 9 * level - 158;

        if (level >= SOFT_CAP) {
            int extra = level - SOFT_CAP;
            base += extra * extra * 5;
        }

        return base;
    }

    public static int getTotalForLevel(int level) {

        int capped = Math.min(level, HARD_CAP);

        int xp = 0;

        for (int i = 0; i < capped; i++) {
            xp += getXPForLevel(i);
        }

        return xp;
    }

    public static int getLevelFromXP(int xp) {

        int level = 0;

        while (level < HARD_CAP &&
                xp >= getTotalForLevel(level + 1)) {
            level++;
        }

        return level;
    }

    public static float getProgress(int xp) {

        int level = getLevelFromXP(xp);

        if (level >= HARD_CAP) return 1f;

        int current = getTotalForLevel(level);
        int next = getTotalForLevel(level + 1);

        if (next == current) return 0f;

        return (float)(xp - current) / (float)(next - current);
    }

    public static boolean canPrestige(int xp) {
        return getLevelFromXP(xp) >= HARD_CAP;
    }

    public static boolean isSoftCap(int level) {
        return level >= SOFT_CAP;
    }

    public static boolean isHardCap(int level) {
        return level >= HARD_CAP;
    }
}