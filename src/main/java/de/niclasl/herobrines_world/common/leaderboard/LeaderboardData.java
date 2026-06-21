package de.niclasl.herobrines_world.common.leaderboard;

public class LeaderboardData {

    private int souls;
    private int level;

    public LeaderboardData(int souls, int level) {
        this.souls = souls;
        this.level = level;
    }

    public int getSouls() {
        return souls;
    }

    public void setSouls(int souls) {
        this.souls = souls;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}