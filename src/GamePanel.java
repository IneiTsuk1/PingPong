import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

    private Paddle playerPaddle;
    private Paddle aiPaddle;
    private Ball ball;
    private HighScoreSaveSystem hs;

    private Score score;
    private int highscore;
    private Clip paddleHitSound;

    private JButton difficultyButton;
    private int difficultyLevel;

    public GamePanel(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();

        playerPaddle = new Paddle(50, HEIGHT / 2 - 50, 20, 100, 4);
        aiPaddle = new Paddle(WIDTH - 70, HEIGHT / 2 - 50, 20, 100, 4);
        ball = new Ball(WIDTH / 2 - 10, HEIGHT / 2  - 10, 20, 3, 3);

        addKeyListener(this);
        playerPaddle.setSpeed(8);

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
        difficultyLevel = 1; // Default difficulty level

        score = new Score();

        highscore = HighScoreSaveSystem.loadHighscore();

        // Initialize game state
        gameRunning = false;
        gameStarted = false;

        // Load the sound for paddle hit
        try {
            paddleHitSound = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream("paddle_hit.wav"));
            paddleHitSound.open(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread flashThread = new Thread(new FlashRunnable());
        flashThread.start();
    }

    private void increaseDifficulty() {
        difficultyLevel++; // Increase difficulty level
        adjustBallSpeed();// Adjust ball speed based on difficulty
        adjustPaddleSpeed();// Adjust paddle speed based on difficulty
        requestFocusInWindow(); // Request focus for the game panelrequestFocusInWindow();// Request focus for the game panel
    }

    private void adjustBallSpeed() {
        // Adjust ball speed based on difficulty level
        int newSpeedX = ball.getSpeedX() + difficultyLevel;
        int newSpeedY = ball.getSpeedY() + difficultyLevel;
        ball.setSpeedX(newSpeedX);
        ball.setSpeedY(newSpeedY);
        // Update button text to reflect current difficulty
        difficultyButton.setText("Difficulty: " + getDifficultyLabel());
    }

    private void adjustPaddleSpeed() {
        // Adjust paddle speeds based on difficulty level
        float paddleSpeedIncrement = 0.7f; // Adjust this value as needed
        float newPaddleSpeed = playerPaddle.getBaseSpeed() + (difficultyLevel * paddleSpeedIncrement);
        playerPaddle.setSpeed(newPaddleSpeed);
        aiPaddle.setSpeed(newPaddleSpeed);
    }

    private String getDifficultyLabel() {
        switch (difficultyLevel) {
            case 1:
                return "Easy";
            case 2:
                return "Medium";
            case 3:
                return "Hard";
            // Add more cases as needed
            default:
                return "";
        }
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

    private void update() {
        // Move the player paddle based on input
        // (Implement key event handling in the keyPressed and keyReleased methods)
        playerPaddle.move();

        // Move the AI paddle based on the ball's position
        int ballCenterY = ball.getY() + ball.getSize() / 2;
        int aiPaddleCenterY = aiPaddle.getY() + aiPaddle.getHeight() / 2;
        if (ballCenterY < aiPaddleCenterY) {
            aiPaddle.moveUp();
        } else if (ballCenterY > aiPaddleCenterY) {
            aiPaddle.moveDown();
        }

        // Move the ball
        ball.move();

        // Check for collisions with paddles
        ball.checkCollisionWithPaddle(playerPaddle);
        ball.checkCollisionWithPaddle(aiPaddle);

        // Check for collisions with walls
        ball.checkCollisionWithWall(WIDTH, HEIGHT);

        // Update the score if the ball goes out of bounds
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

        // Game logic goes here
        ball.move();
        ball.checkCollisionWithPaddle(playerPaddle);
        ball.checkCollisionWithPaddle(aiPaddle);
        ball.checkCollisionWithWall(WIDTH, HEIGHT);

        // Check for collision with player paddle and play sound
        if (ball.getX() <= playerPaddle.getX() + playerPaddle.getWidth() &&
                ball.getY() + ball.getSize() >= playerPaddle.getY() &&
                ball.getY() <= playerPaddle.getY() + playerPaddle.getHeight()) {
            playPaddleHitSound();
        }

        // Check for collision with AI paddle and play sound
        if (ball.getX() + ball.getSize() >= aiPaddle.getX() &&
                ball.getY() + ball.getSize() >= aiPaddle.getY() &&
                ball.getY() <= aiPaddle.getY() + aiPaddle.getHeight()) {
            playPaddleHitSound();
        }
    }

    private void playPaddleHitSound() {
        if (paddleHitSound != null) {
            paddleHitSound.stop(); // Stop any previous playback
            paddleHitSound.setFramePosition(0); // Reset sound to the beginning
            paddleHitSound.start(); // Play the sound
        }
    }

    private void resetBall() {
        ball.setX(WIDTH / 2 - 10);
        ball.setY(HEIGHT / 2 - 10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        // Draw game objects if the game is running
        if (gameRunning) {
            playerPaddle.draw(g);
            aiPaddle.draw(g);
            ball.draw(g);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Player: " + score.getPlayerScore(), 20, 30);
            g.drawString("AI: " + score.getAiScore(), WIDTH - 100, 30);
            g.drawString("Highscore: " + highscore, WIDTH / 2 - 70, 30);
        } else {
            // Draw start menu if the game is not running
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Ping Pong Game", WIDTH / 2 - 160, HEIGHT / 2 - 50);

            // Draw "Press SPACE to start" text if it's visible
            if (spaceVisible) {
                g.setFont(new Font("Arial", Font.PLAIN, 24));
                g.drawString("Press SPACE to start", WIDTH / 2 - 130, HEIGHT / 2 + 20);
            }
        }
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
