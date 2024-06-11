package main;

import Audio.Audio;
import graphics.GameRenderer;
import highscore.HighScoreSaveSystem;
import logic.AI.Ball;
import logic.game.Difficulty;
import logic.game.Score;
import logic.player.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static int WIDTH;
    public static int HEIGHT;
    private Thread thread;

    private boolean gameRunning;
    private boolean gameStarted;
    private boolean spaceVisible; // Indicates if the "Press SPACE to start" text is visible

    private final Paddle playerPaddle;
    private final Paddle aiPaddle;
    private final Ball ball;
    private HighScoreSaveSystem hs;

    private final Score score;
    private int highscore;

    private final Audio paddleHitSound;
    private final JButton difficultyButton;
    private final Difficulty difficulty;
    private final GameRenderer renderer; // GameRenderer instance to handle rendering

    public GamePanel(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();

        playerPaddle = new Paddle(50, HEIGHT / 2 - 50, 20, 100, 4);
        aiPaddle = new Paddle(WIDTH - 70, HEIGHT / 2 - 50, 20, 100, 4);
        ball = new Ball(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 3, 3);

        addKeyListener(this);
        playerPaddle.setSpeed(8);

        difficulty = new Difficulty();

        // Initialize difficulty button
        difficultyButton = new JButton("Difficulty: Easy");
        difficultyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Increase difficulty level and adjust ball speed
                increaseDifficulty();
            }
        });

        add(difficultyButton);

        score = new Score();
        highscore = HighScoreSaveSystem.loadHighscore();

        // Initialize game state
        gameRunning = false;
        gameStarted = false;

        paddleHitSound = new Audio("/Audio/resources/paddle_hit.wav");

        // Initialize the game renderer
        renderer = new GameRenderer();
        renderer.initialize();

        Thread flashThread = new Thread(new FlashRunnable());
        flashThread.start();
    }

    // Method to increase the difficulty level
    private void increaseDifficulty() {
        difficulty.increaseDifficulty(); // Increase difficulty level
        adjustBallSpeed(); // Adjust ball speed based on difficulty
        adjustPaddleSpeed(); // Adjust paddle speed based on difficulty
        difficultyButton.setText("Difficulty: " + difficulty.getDifficultyLabel());
        requestFocusInWindow(); // Request focus for the game panel
    }

    // Method to adjust ball speed based on difficulty
    private void adjustBallSpeed() {
        ball.setSpeedX(ball.getSpeedX() + difficulty.getBallSpeedIncrement());
        ball.setSpeedY(ball.getSpeedY() + difficulty.getBallSpeedIncrement());
    }

    // Method to adjust paddle speed based on difficulty
    private void adjustPaddleSpeed() {
        // Adjust paddle speeds based on difficulty level
        float newSpeed = playerPaddle.getBaseSpeed() + difficulty.getPaddleSpeedIncrement();
        playerPaddle.setSpeed(newSpeed);
        aiPaddle.setSpeed(newSpeed);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (gameStarted) {
                update();
                repaint();
            }

            try {
                Thread.sleep(16); // Adjust the sleep time as needed for desired frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to update game state
    private void update() {
        playerPaddle.move();
        aiPaddle.moveTowards(ball);

        ball.move();
        ball.checkCollisionWithWall(WIDTH, HEIGHT);

        if (ball.checkCollisionWithPaddle(playerPaddle) || ball.checkCollisionWithPaddle(aiPaddle)) {
            paddleHitSound.play();
        }

        if (ball.getX() <= 0) {
            score.incrementAiScore();
            resetBall();
        } else if (ball.getX() + ball.getSize() >= WIDTH) {
            score.incrementPlayerScore();
            if (score.getPlayerScore() > highscore) {
                highscore = score.getPlayerScore();
                hs.saveHighscore(highscore);
            }
            resetBall();
        }
    }

    // Method to reset the ball position
    private void resetBall() {
        ball.setX(WIDTH / 2 - 10);
        ball.setY(HEIGHT / 2 - 10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Use the renderer to render the game graphics
        renderer.render(g, this, gameRunning, spaceVisible, playerPaddle, aiPaddle, ball, score, highscore);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE && !gameStarted) {
            gameRunning = true;
            gameStarted = true;
            remove(difficultyButton); // Remove the difficulty button from the panel
            repaint();
        }

        // Handle key events for the game if it's running
        if (gameRunning) {
            if (keyCode == KeyEvent.VK_UP) {
                playerPaddle.setMoveUp(true);
            } else if (keyCode == KeyEvent.VK_DOWN) {
                playerPaddle.setMoveDown(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Handle key events for the game if it's running
        if (gameRunning) {
            if (keyCode == KeyEvent.VK_UP) {
                playerPaddle.setMoveUp(false);
            } else if (keyCode == KeyEvent.VK_DOWN) {
                playerPaddle.setMoveDown(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    // Runnable for handling the flashing effect
    private class FlashRunnable implements Runnable {
        @Override
        public void run() {
            while (!gameStarted) {
                try {
                    Thread.sleep(500); // Flash interval: 500 milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                spaceVisible = !spaceVisible; // Toggle visibility
                repaint(); // Redraw the panel to update the visibility
            }
        }
    }
}
