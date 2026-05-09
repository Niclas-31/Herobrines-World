package de.niclasl.herobrines_world.math;

public class SoulMath {

    public static int getXPForLevel(int level) {
        if (level <= 16) return 2 * level + 7;
        if (level <= 31) return 5 * level - 38;
        return 9 * level - 158;
    }

    public static int getTotalForLevel(int level) {
        int xp = 0;

        for (int i = 0; i < level; i++) {
            xp += getXPForLevel(i);
        }

        return xp;
    }

    public static int getLevelFromXP(int xp) {
        int level = 0;

        while (xp >= getTotalForLevel(level + 1)) {
            level++;
        }

        return level;
    }

    public static float getProgress(int xp) {
        int level = getLevelFromXP(xp);

        int current = getTotalForLevel(level);
        int next = getTotalForLevel(level + 1);

        if (next == current) return 0f;

        return (float)(xp - current) / (float)(next - current);
    }

    public static int addXP(int currentXP, int amount) {
        return Math.max(0, currentXP + amount);
    }
}