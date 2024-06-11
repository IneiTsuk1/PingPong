import java.awt.*;

public class Paddle {

    private int x;
    private int y;
    private int width;
    private int height;
    private float speed;
    private boolean moveUp;
    private boolean moveDown;

    public Paddle(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.moveUp = false;
        this.moveDown = false;
    }

    public float getBaseSpeed() {
        return speed;
    }

    public void move() {
        if (moveUp && y > 0) {
            y -= speed;
        }
        if (moveDown && y + height < GamePanel.HEIGHT) {
            y += speed;
        }
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void setY(int newY) {
        y = newY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setSpeed(float speed) { // Setter method for speed
        this.speed = speed;
    }
}
