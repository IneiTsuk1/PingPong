package logic.game;

public class Difficulty {
    private int level;
    private static final float PADDLE_SPEED_INCREMENT = 0.5f;

    public Difficulty() {
        this.level = 1; //default level
    }

    public void increaseDifficulty() {
        level++;
    }

    public int getLevel() {
        return level;
    }

    public String getDifficultyLabel() {
        switch (level) {
            case 1:
                return "Easy";
            case 2:
                return "Medium";
            case 3:
                return "Hard";
            default:
                return "Unkown";
        }
    }

    public float getPaddleSpeedIncrement() {
        return level * PADDLE_SPEED_INCREMENT;
    }

    public int getBallSpeedIncrement() {
        return level;
    }
}
