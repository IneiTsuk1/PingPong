package main;

import javax.swing.*;

// This class represents the main window of the game
public class Window extends JFrame {

    // Constructor for the Window class
    public Window() {
        // Set the title of the window
        setTitle("PING PONG");

        // Set the default close operation to exit when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent resizing of the window
        setResizable(false);

        // Set the content pane of the window to an instance of GamePanel with dimensions 1280x720
        setContentPane(new GamePanel(1280, 720));

        // Pack the components within the JFrame to ensure they are at their preferred sizes
        pack();

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Make the window visible
        setVisible(true);
    }
}