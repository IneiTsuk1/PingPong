package highscore;

import java.io.*;

public class HighScoreSaveSystem {
    private static final String HIGHSCORE_FILE_PATH = "src/highscore/highscore.txt";

    public static void saveHighscore(int highscore) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGHSCORE_FILE_PATH, true))) {
            writer.println(highscore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int loadHighscore() {
        File file = new File(HIGHSCORE_FILE_PATH);
        if (!file.exists()) {
            // Create the file if it doesn't exist
            createHighscoreFile();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxScore = Integer.MIN_VALUE;
            while ((line = reader.readLine()) != null) {
                int score = Integer.parseInt(line);
                if (score > maxScore) {
                    maxScore = score;
                }
            }
            return maxScore;
        } catch (IOException | NumberFormatException e) {
            // Handle file not found or invalid data
            e.printStackTrace();
            return 0; // Return default highscore if loading fails
        }
    }

    private static void createHighscoreFile() {
        try {
            File file = new File(HIGHSCORE_FILE_PATH);
            if (file.createNewFile()) {
                System.out.println("Highscore file created: " + file.getAbsolutePath());
            } else {
                System.out.println("Highscore file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
