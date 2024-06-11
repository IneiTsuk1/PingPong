package main;

public class GameLauncher {
    // Constructor
    public GameLauncher() {
        try {
            new Window(); // Create a new instance of the Window class to launch the game
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur during game launch
        }
    }

    // Main method
    public static void main(String[] args) {
        new GameLauncher(); // Create a new instance of GameLauncher to start the game
    }
}
