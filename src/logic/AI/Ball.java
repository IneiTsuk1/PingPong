package logic.AI;

import logic.player.Paddle;
import java.awt.*;

public class Ball {
    // Properties
    private int x; // X-coordinate of the ball
    private int y; // Y-coordinate of the ball
    private int size; // Size of the ball
    private int speedX; // Horizontal speed of the ball
    private int speedY; // Vertical speed of the ball
    private int previousX; // Previous X-coordinate of the ball
    private int previousY; // Previous Y-coordinate of the ball

    // Constructor
    public Ball(int x, int y, int size, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = speedX;
        this.speedY = speedY;
        this.previousX = x;
        this.previousY = y;
    }

    // Method to move the ball
    public void move() {
        previousX = x; // Record the previous X-coordinate
        previousY = y; // Record the previous Y-coordinate
        x += speedX; // Move the ball horizontally
        y += speedY; // Move the ball vertically
    }

    // Getter and setter methods for various properties of the ball
    public void setX(int newX) {
        x = newX;
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

    public int getSize() {
        return size;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    // Method to check collision with a paddle
    public boolean checkCollisionWithPaddle(Paddle paddle) {
        if (x < paddle.getX() + paddle.getWidth() && x + size > paddle.getX() &&
                y < paddle.getY() + paddle.getHeight() && y + size > paddle.getY()) {
            // Correct position and reverse direction
            if (previousX + size <= paddle.getX() || previousX >= paddle.getX() + paddle.getWidth()) {
                speedX *= -1; // Horizontal collision
            } else if (previousY + size <= paddle.getY() || previousY >= paddle.getY() + paddle.getHeight()) {
                speedY *= -1; // Vertical collision
            }
            return true; // Collision occurred
        }
        return false; // No collision
    }

    // Method to check collision with the game window's walls
    public void checkCollisionWithWall(int screenWidth, int screenHeight) {
        if (y <= 0 || y + size >= screenHeight) {
            speedY *= -1; // Reverse vertical direction upon collision with top or bottom wall
        }
    }

    // Method to draw the ball
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size); // Fill a circle representing the ball
    }
}
