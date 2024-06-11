public class Score {
    private int playerScore;
    private int aiScore;

    public Score() {
        playerScore = 0;
        aiScore = 0;
    }

    public void incrementPlayerScore() {
        playerScore++;
    }

    public void incrementAiScore() {
        aiScore++;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getAiScore() {
        return aiScore;
    }
}
