package logic.player;
import logic.AI.Ball;
import main.GamePanel;

import java.awt.*;

public class Paddle {

    private int x;
    private int y;
    private int width;
    private int height;
    private float speed;
    private boolean moveUp;
    private boolean moveDown;

    public GamePanel gamePanel;

    public Paddle(int x, int y, int width, int height, float speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.moveUp = false;
        this.moveDown = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void move() {
        if (moveUp && y > 0) {
            y -= speed;
        }
        if (moveDown && y + height < gamePanel.HEIGHT) {
            y += speed;
        }
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveTowards(Ball ball) {
        // Predict the future position of the ball
        int predictedBallY = predictBallY(ball);

        // Move the paddle towards the predicted position
        if (y < predictedBallY - height / 2 && y + height < GamePanel.HEIGHT) {
            y += speed;
        } else if (y > predictedBallY - height / 2 && y > 0) {
            y -= speed;
        }
    }

    private int predictBallY(Ball ball) {
        // Calculate the time it takes for the ball to reach the logic.AI.AI paddle's x-coordinate
        float timeToReach = (ball.getX() - x) / ball.getSpeedX();

        // Calculate the predicted y-coordinate of the ball at that time
        int predictedBallY = ball.getY() + (int) (timeToReach * ball.getSpeedY());

        // Adjust for the ball bouncing off the walls
        int panelHeight = GamePanel.HEIGHT;
        int ballSize = ball.getSize();
        int ballMaxY = panelHeight - ballSize;

        while (predictedBallY < 0 || predictedBallY > ballMaxY) {
            if (predictedBallY < 0) {
                // logic.AI.Ball hit the top wall, reflect it
                predictedBallY = -predictedBallY;
            } else {
                // logic.AI.Ball hit the bottom wall, reflect it
                predictedBallY = 2 * ballMaxY - predictedBallY;
            }
        }

        return predictedBallY;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setY(int newY) {
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getBaseSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
}
