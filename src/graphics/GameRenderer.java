package graphics;

import logic.AI.Ball;
import logic.player.Paddle;
import logic.game.Score;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameRenderer {
    // Constructor
    public GameRenderer() {
        // Initialize any required resources or settings
    }

    // Method to initialize the renderer
    public void initialize() {
        // Perform any initialization tasks
    }

    // Method to render the game graphics
    public void render(Graphics g, JPanel panel, boolean gameRunning, boolean spaceVisible,
                       Paddle playerPaddle, Paddle aiPaddle, Ball ball, Score score, int highscore) {
        // Clear the panel
        clearPanel(g, panel);

        // Draw game objects based on the game state
        if (gameRunning) {
            // Draw game objects if the game is running
            drawGameObjects(g, playerPaddle, aiPaddle, ball, score, highscore);
        } else {
            // Draw start menu if the game is not running
            drawStartMenu(g, spaceVisible, panel.getWidth(), panel.getHeight());
        }

        // Repaint the panel
        panel.repaint();
    }

    // Method to clear the panel
    private void clearPanel(Graphics g, JPanel panel) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
    }

    // Method to draw game objects
    private void drawGameObjects(Graphics g, Paddle playerPaddle, Paddle aiPaddle,
                                 Ball ball, Score score, int highscore) {
        // Draw player paddle
        playerPaddle.draw(g);

        // Draw logic.AI.AI paddle
        aiPaddle.draw(g);

        // Draw ball
        ball.draw(g);

        // Draw scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Player: " + score.getPlayerScore(), 20, 30);
        g.drawString("AI: " + score.getAiScore(), GamePanel.WIDTH - 100, 30);
        g.drawString("Highscore: " + highscore, GamePanel.WIDTH / 2 - 70, 30);
    }

    // Method to draw the start menu
    private void drawStartMenu(Graphics g, boolean spaceVisible, int panelWidth, int panelHeight) {
        // Draw title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Ping Pong Game", panelWidth / 2 - 160, panelHeight / 2 - 50);

        // Draw "Press SPACE to start" text if it's visible
        if (spaceVisible) {
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press SPACE to start", panelWidth / 2 - 130, panelHeight / 2 + 20);
        }
    }
}
