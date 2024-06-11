package logic.game;

public class Difficulty {
    // Properties
    private int level; // Current difficulty level
    private static final float PADDLE_SPEED_INCREMENT = 0.5f; // Increment for paddle speed with each difficulty level

    // Constructor
    public Difficulty() {
        this.level = 1; // Default level
    }

    // Method to increase the difficulty level
    public void increaseDifficulty() {
        level++; // Increment the difficulty level
    }

    // Getter method for the difficulty level
    public int getLevel() {
        return level;
    }

    // Method to get the label corresponding to the difficulty level
    public String getDifficultyLabel() {
        switch (level) {
            case 1:
                return "Easy";
            case 2:
                return "Medium";
            case 3:
                return "Hard";
            default:
                return "Unknown"; // Default case for unknown difficulty levels
        }
    }

    // Method to get the increment for paddle speed based on the difficulty level
    public float getPaddleSpeedIncrement() {
        return level * PADDLE_SPEED_INCREMENT; // Paddle speed increases linearly with difficulty level
    }

    // Method to get the increment for ball speed based on the difficulty level (not implemented)
    public int getBallSpeedIncrement() {
        return level; // Placeholder method for future implementation
    }
}
