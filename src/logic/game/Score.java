package logic.game;

public class Score {
    // Properties
    private int playerScore; // Player's score
    private int aiScore; // AI's score

    // Constructor
    public Score() {
        playerScore = 0; // Initialize player's score to 0
        aiScore = 0; // Initialize AI's score to 0
    }

    // Method to increment player's score
    public void incrementPlayerScore() {
        playerScore++; // Increment player's score
    }

    // Method to increment AI's score
    public void incrementAiScore() {
        aiScore++; // Increment AI's score
    }

    // Getter method for player's score
    public int getPlayerScore() {
        return playerScore; // Return player's score
    }

    // Getter method for AI's score
    public int getAiScore() {
        return aiScore; // Return AI's score
    }
}
