package logic.AI;

import logic.player.Paddle;
import main.GamePanel;

public class AI {
    // Properties
    private Paddle paddle; // The AI's paddle
    private Ball ball; // The ball object

    // Constructor
    public AI(Paddle paddle, Ball ball) {
        this.paddle = paddle;
        this.ball = ball;
    }

    // Method to update the AI's paddle position
    public void update(Ball ball) {
        // Calculate the predicted Y-coordinate of the ball when it reaches the AI paddle's X-coordinate
        double predictedY = predictBallPosition();

        // Adjust the AI paddle's position based on the predicted Y-coordinate of the ball
        if (predictedY < paddle.getY() + paddle.getHeight() / 2) {
            paddle.moveUp(); // Move the paddle up
        } else {
            paddle.moveDown(); // Move the paddle down
        }

        // Ensure the paddle stays within the game window bounds
        if (paddle.getY() < 0) {
            paddle.setY(0); // Clamp paddle position to top of the window
        } else if (paddle.getY() + paddle.getHeight() > GamePanel.HEIGHT) {
            paddle.setY(GamePanel.HEIGHT - paddle.getHeight()); // Clamp paddle position to bottom of the window
        }
    }

    // Method to predict the Y-coordinate of the ball when it reaches the AI paddle's X-coordinate
    private double predictBallPosition() {
        double slope = (double)(ball.getY() - ball.getPreviousY()) / (ball.getX() - ball.getPreviousX());
        return slope * (paddle.getX() - ball.getX()) + ball.getY();
    }
}
