package logic.player;

import logic.AI.Ball;
import main.GamePanel;
import java.awt.*;

public class Paddle {
    // Properties
    private int x; // X-coordinate of the paddle
    private int y; // Y-coordinate of the paddle
    private int width; // Width of the paddle
    private int height; // Height of the paddle
    private float speed; // Speed of the paddle
    private boolean moveUp; // Flag to indicate whether the paddle should move up
    private boolean moveDown; // Flag to indicate whether the paddle should move down

    public GamePanel gamePanel; // Reference to the game panel

    // Constructor
    public Paddle(int x, int y, int width, int height, float speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.moveUp = false;
        this.moveDown = false;
    }

    // Getter methods for paddle width and height
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Method to move the paddle based on the current movement flags
    public void move() {
        if (moveUp && y > 0) {
            y -= speed; // Move the paddle up if moveUp flag is true and paddle is not at the top edge
        }
        if (moveDown && y + height < gamePanel.HEIGHT) {
            y += speed; // Move the paddle down if moveDown flag is true and paddle is not at the bottom edge
        }
    }

    // Method to move the paddle up
    public void moveUp() {
        y -= speed; // Move the paddle up
    }

    // Method to move the paddle down
    public void moveDown() {
        y += speed; // Move the paddle down
    }

    // Method to move the paddle towards the predicted position of the ball
    public void moveTowards(Ball ball) {
        // Predict the future position of the ball
        int predictedBallY = predictBallY(ball);

        // Move the paddle towards the predicted position
        if (y < predictedBallY - height / 2 && y + height < GamePanel.HEIGHT) {
            y += speed; // Move the paddle down if ball is below the middle of the paddle
        } else if (y > predictedBallY - height / 2 && y > 0) {
            y -= speed; // Move the paddle up if ball is above the middle of the paddle
        }
    }

    // Method to predict the Y-coordinate of the ball when it reaches the paddle's X-coordinate
    private int predictBallY(Ball ball) {
        // Calculate the time it takes for the ball to reach the paddle's X-coordinate
        float timeToReach = (ball.getX() - x) / ball.getSpeedX();

        // Calculate the predicted Y-coordinate of the ball at that time
        int predictedBallY = ball.getY() + (int) (timeToReach * ball.getSpeedY());

        // Adjust for the ball bouncing off the walls
        int panelHeight = GamePanel.HEIGHT;
        int ballSize = ball.getSize();
        int ballMaxY = panelHeight - ballSize;

        while (predictedBallY < 0 || predictedBallY > ballMaxY) {
            if (predictedBallY < 0) {
                predictedBallY = -predictedBallY; // Reflect the ball if it hits the top wall
            } else {
                predictedBallY = 2 * ballMaxY - predictedBallY; // Reflect the ball if it hits the bottom wall
            }
        }

        return predictedBallY;
    }

    // Setter methods for movement flags
    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    // Setter method for Y-coordinate
    public void setY(int newY) {
        y = newY;
    }

    // Getter methods for X and Y coordinates
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Getter method for base speed
    public float getBaseSpeed() {
        return speed;
    }

    // Setter method for speed
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // Method to draw the paddle
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height); // Fill a rectangle representing the paddle
    }
}
