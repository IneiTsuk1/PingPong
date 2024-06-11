import java.awt.*;

public class Ball {

    private int x;
    private int y;
    private int size;
    private int speedX;
    private int speedY;
    private int previousX;
    private int previousY;

    public Ball(int x, int y, int size, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = speedX;
        this.speedY = speedY;
        this.previousX = x;
        this.previousY = y;
    }

    public void move() {
        previousX = x;
        previousY = y;
        x += speedX;
        y += speedY;
    }

    public void checkCollisionWithPaddle(Paddle paddle) {
        if (x + size >= paddle.getX() && x <= paddle.getX() + paddle.getWidth() &&
                y + size >= paddle.getY() && y <= paddle.getY() + paddle.getHeight()) {
            speedX *= -1;
        }
    }

    public void checkCollisionWithWall(int screenWidth, int screenHeight) {
        if (y <= 0 || y + size >= screenHeight) {
            speedY *= -1;
        }
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size);
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

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }
}
